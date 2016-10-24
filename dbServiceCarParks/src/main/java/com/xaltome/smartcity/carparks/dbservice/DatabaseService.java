/**
 * 
 */
package com.xaltome.smartcity.carparks.dbservice;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;







import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.xaltome.smartcity.carparks.dbservice.model.CarPark;
import com.xaltome.smartcity.carparks.dbservice.model.CarParkLoad;
import com.xaltome.smartcity.carparks.dbservice.repository.CarParkLoadRepository;
import com.xaltome.smartcity.carparks.dbservice.repository.CarParkRepository;
import com.xaltome.smartcity.carparks.dbservice.util.predicate.CarParkPredicate;

/**
 * @author kopajczy
 *
 */
@Service
public class DatabaseService {
	
	@Autowired
	private CarParkLoadRepository carParkLoadRepo;
	
	@Autowired
	private CarParkRepository carParkRepo;
	
	protected static final Logger LOGGER = Logger.getLogger(DatabaseService.class);
	
	public void fillCarkParkData(final List<Map<String, Object>> data){
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Entering fillCarkParkData: %s", data);
		} else {
			LOGGER.info("Entering fillCarkParkData");
		}
		fillCarkParkLoadTable(data);
		LOGGER.info("Leaving fillCarkParkData");
	}
	
	protected void fillCarkParkLoadTable(final List<Map<String, Object>> data){
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Entering fillCarkParkLoadTable: %s", data);
		}
		for (Map<String, Object> record : data) {
			try {
				addNewCarParkLoadRecord(record);
			} catch (Exception e) {
				LOGGER.errorf("Exception in fillCarkParkLoadTable for data (%s, %s): %s", record.get("Czas_Rejestracji"), record.get("Nazwa"), e);
				continue;
			}
			LOGGER.debugf("fillCarkParkLoadTable successfully save record (%s, %s)", record.get("Czas_Rejestracji"), record.get("Nazwa"));
		}
		LOGGER.debug("Leaving fillCarkParkLoadTable");
	}
	
	protected void addNewCarParkLoadRecord(final Map<String, Object> record) throws ParseException{
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Entering addNewCarParkLoadRecord: %s", record);
		}
		CarParkLoad carParkLoad = new CarParkLoad();
		//TODO: move strings to property file
		carParkLoad.setFreePlaceAmount(parseLong((String) record.get("Liczba_Wolnych_Miejsc")));
		carParkLoad.setCarInAmount(parseLong((String) record.get("Liczba_Poj_Wjezdzajacych")));
		carParkLoad.setCarOutAmount(parseLong((String) record.get("Liczba_Poj_Wyjezdzajacych")));
		carParkLoad.setTimestamp(parseTimestamp((String) record.get("Czas_Rejestracji")));
		CarPark carPark = carParkRepo.findByName((String) record.get("Nazwa"));
		if (carPark == null) { // add dynamically new car park
			carPark = addNewCarPark((String) record.get("Nazwa"));
		}
		carParkLoad.setCarPark(carPark);
		carParkLoadRepo.save(carParkLoad);
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("Leaving addNewCarParkLoadRecord()");
		}
	}
	
	protected CarPark addNewCarPark(final String name){
		LOGGER.infof("Entering addNewCarPark with name: %s", name);
		final CarPark carPark = new CarPark();
		carPark.setName(name);
		carPark.setCapacity(-1L);
		LOGGER.infof("Exiting/saving addNewCarPark");
		return carParkRepo.save(carPark);
	}
	
	protected Timestamp parseTimestamp(final String timestampString) throws ParseException {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Entering parseTimestamp: %s", timestampString);
		}
		//TODO: move string to property file
		//Locale.FRENCH solves problem with noon date inserting to the DB as 00.
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS",  Locale.FRENCH);
	    Date parsedDate = null;
	    //TODO: check if timestampString != null
		try { //TODO: get rid of exception, handle in fillCarkParkLoadTable
			dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Warsaw"));
			parsedDate = dateFormat.parse(timestampString);
		} catch (ParseException e) {
			LOGGER.error("Exception in parseTimestamp", e);
			throw e;
		}
	    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Leaving parseTimestamp: %s", timestamp);
		}
		return timestamp;
	}
	
	protected Long parseLong(final String longString){
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Entering parseLong: %s", longString);
		}
		Long number = null;
		if (longString != null && !longString.equals("")) {
			number = Long.parseLong(longString);
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Leaving parseLong: %s", number);
		}
		return number;
	}
	
	public void detectChanges(final List<Map<String, Object>> data) throws ParseException{
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Entering detectChanges(): %s", data);
		} else {
			LOGGER.info("Entering detectChanges()");
		}
		Iterable<CarPark> carParkList = carParkRepo.findAll();
		for (CarPark carPark : carParkList) {
			final String carParkName = carPark.getName();
			final Predicate<Map<String, Object>> carParkPredicate = new CarParkPredicate(carParkName);
			Collection<Map<String, Object>> selectedCarParkData = CollectionUtils.select(data, carParkPredicate);
			//selectedCarParkData should be in DESC order, to avoid linear list iteration and date comparison
			Collections.sort((List<Map<String, Object>>) selectedCarParkData, new TimestampComparator());
			if (LOGGER.isTraceEnabled()) {
				LOGGER.tracef("detectChanges() selectedCarParkData: %s", selectedCarParkData);
			}
			
			final CarParkLoad latestEntry = carParkLoadRepo.getLatestEntry(carParkName, new PageRequest(0, 1)).get(0);
			final Timestamp timestampFromDB = latestEntry.getTimestamp();
			
			List<Map<String, Object>> changeList = new LinkedList<Map<String,Object>>();
			for (Map<String, Object> record : selectedCarParkData) {
				Timestamp timestampFromCSV = parseTimestamp((String) record.get("Czas_Rejestracji"));
				if (timestampFromCSV.after(timestampFromDB)) {
					if (LOGGER.isTraceEnabled()) {
						LOGGER.tracef("detectChanges() new entry in csv file detected: %s", record);
					}
					changeList.add(record);
				}
			}
			//Reverse the list to keep chronological order of record insertion
			Collections.reverse(changeList);
			if (LOGGER.isTraceEnabled()) {
				LOGGER.tracef("detectChanges() changeList to be insterted into DB: %s", changeList);
			}
			fillCarkParkLoadTable(changeList);
		}
		LOGGER.info("Leaving detectChanges()");
	}
	
	private class TimestampComparator implements Comparator<Map<String, Object>>{

		public int compare(Map<String, Object> o1, Map<String, Object> o2) {
			Timestamp timestamp1;
			Timestamp timestamp2;
			try {
				timestamp1 = parseTimestamp((String) o1.get("Czas_Rejestracji"));
				timestamp2 = parseTimestamp((String) o2.get("Czas_Rejestracji"));
			} catch (ParseException e) {
				LOGGER.error("Exception in TimestampComparator", e);
				throw new RuntimeException(e);
			}
			return timestamp2.compareTo(timestamp1);
		}
		
	}
}
