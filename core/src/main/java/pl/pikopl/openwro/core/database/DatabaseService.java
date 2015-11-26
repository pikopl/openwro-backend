/**
 * 
 */
package pl.pikopl.openwro.core.database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.sql.Timestamp;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.pikopl.openwro.dbservice.model.CarPark;
import pl.pikopl.openwro.dbservice.model.CarParkLoad;
import pl.pikopl.openwro.dbservice.repository.CarParkLoadRepository;
import pl.pikopl.openwro.dbservice.repository.CarParkRepository;

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
		fillCarParkTable();
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
				carParkLoad.setFreePlaceAmount(Long.parseLong((String) record.get("Liczba_Wolnych_Miejsc")));
				carParkLoad.setCarInAmount(Long.parseLong((String) record.get("Liczba_Poj_Wjezdzajacych")));
				carParkLoad.setCarOutAmount(Long.parseLong((String) record.get("Liczba_Poj_Wyjezdzajacych")));
				carParkLoad.setTimestamp(parseTimestamp((String) record.get("Czas_Rejestracji")));
				CarPark carPark = carParkRepo.findByName((String) record.get("Nazwa")); //TODO: locale
				if (carPark == null) { //workaround for lacking locale and parking not match
					carPark = new CarPark();
					carPark.setCarParkid(2L);
					carPark.setName("ul. œw. Antoniego");
					carPark.setCapacity(140L);
				}
				carParkLoad.setCarPark(carPark);
				carParkLoadRepo.save(carParkLoad);
			} catch (Exception e) {
				LOGGER.errorf("Exception in fillCarkParkLoadTable for data (%s, %s)", record.get("Czas_Rejestracji"), record.get("Nazwa"), e);
				continue;
			}
			LOGGER.debugf("fillCarkParkLoadTable successfully save record (%s, %s)", record.get("Czas_Rejestracji"), record.get("Nazwa"));
		}
		LOGGER.info("Leaving fillCarkParkLoadTable");
	}
	
	protected Timestamp parseTimestamp(final String timestampString) throws ParseException {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Entering parseTimestamp: %s", timestampString);
		}
		//TODO: move string to property file
		//Locale.FRENCH solves problem with noon date inserting to the DB as 00.
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS",  Locale.FRENCH);
	    Date parsedDate = null;
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
	
	
	
	/**
	 * Fills CarPark table with hardcoded values by now
	 */
	protected void fillCarParkTable(){
		LOGGER.info("Entering fillCarParkTable");
		if (carParkRepo.findByName("Renoma") == null) {
			CarPark carPark1 = new CarPark();
			carPark1.setName("Renoma");
			carPark1.setCapacity(630L);
			carParkRepo.save(carPark1);
			LOGGER.info("fillCarParkTable: inserted Renoma cark park");
		}

		if (carParkRepo.findByName("ul. œw. Antoniego") == null) {
			CarPark carPark2 = new CarPark();
			carPark2.setName("ul. œw. Antoniego");
			carPark2.setCapacity(140L);
			carParkRepo.save(carPark2);
			LOGGER.info("fillCarParkTable: inserted ul. œw. Antoniego cark park");
		}

		if (carParkRepo.findByName("Nowy Targ") == null) {
			CarPark carPark3 = new CarPark();
			carPark3.setName("Nowy Targ");
			carPark3.setCapacity(334L);
			carParkRepo.save(carPark3);
			LOGGER.info("fillCarParkTable: inserted Nowy Targ cark park");
		}
		LOGGER.info("Leaving fillCarParkTable");
	}
}
