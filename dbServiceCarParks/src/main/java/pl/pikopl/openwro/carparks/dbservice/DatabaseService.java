/**
 * 
 */
package pl.pikopl.openwro.carparks.dbservice;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.pikopl.openwro.carparks.dbservice.model.CarPark;
import pl.pikopl.openwro.carparks.dbservice.model.CarParkLoad;
import pl.pikopl.openwro.carparks.dbservice.repository.CarParkLoadRepository;
import pl.pikopl.openwro.carparks.dbservice.repository.CarParkRepository;

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
		} else {
			LOGGER.info("Entering fillCarkParkLoadTable");
		}
		for (Map<String, Object> record : data) {
			
			try {
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
			} catch (Exception e) {
				LOGGER.errorf("Exception in fillCarkParkLoadTable for data (%s, %s): %s", record.get("Czas_Rejestracji"), record.get("Nazwa"), e);
				continue;
			}
			LOGGER.debugf("fillCarkParkLoadTable successfully save record (%s, %s)", record.get("Czas_Rejestracji"), record.get("Nazwa"));
		}
		LOGGER.info("Leaving fillCarkParkLoadTable");
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
		if (longString != null) {
			number = Long.parseLong(longString);
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Leaving parseLong: %s", number);
		}
		return number;
	}
}
