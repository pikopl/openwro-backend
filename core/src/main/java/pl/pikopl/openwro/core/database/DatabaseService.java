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
	
	public void fillCarkParkData(final List<Map<String, Object>> data){
		System.out.println("ENTER:> DatabaseFiller.fillCarkParkData");
		fillCarParkTable();
		fillCarkParkLoadTable(data);
		System.out.println("EXIT:> DatabaseFiller.fillCarkParkData");
	}
	
	protected void fillCarkParkLoadTable(final List<Map<String, Object>> data){
		System.out.println("ENTER:> DatabaseFiller.fillCarkParkLoadTable");
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
				System.out.println("ANY:> DatabaseFiller.fillCarkParkLoadTable" + e);
			}	
			System.out.println("ANY:> DatabaseFiller.fillCarkParkLoadTable:successfully save record with timestamp " + record.get("Czas_Rejestracji"));
		}
		System.out.println("EXIT:> DatabaseFiller.fillCarkParkLoadTable");
	}
	
	protected Timestamp parseTimestamp(final String timestampString) throws ParseException {
		System.out.println("ENTER:> DatabaseFiller.parseTimestamp");
		//TODO: move string to property file
		//Locale.FRENCH solves problem with noon date inserting to the DB as 00.
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS",  Locale.FRENCH);
	    Date parsedDate = null;
		try {
			parsedDate = dateFormat.parse(timestampString);
		} catch (ParseException e) {
			System.out.println("ERROR:> DatabaseFiller.parseTimestamp:" + e);
			throw e;
		}
	    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
		System.out.println("EXIT:> DatabaseFiller.parseTimestamp:result " + timestamp);
		return timestamp;
	}
	
	
	
	/**
	 * Fills CarPark table with hardcoded values by now
	 */
	protected void fillCarParkTable(){
		System.out.println("ENTER:> DatabaseFiller.fillCarParkTable");
		
		if (carParkRepo.findByName("Renoma") == null) {
			CarPark carPark1 = new CarPark();
			carPark1.setName("Renoma");
			carPark1.setCapacity(630L);
			carParkRepo.save(carPark1);
		}

		if (carParkRepo.findByName("ul. œw. Antoniego") == null) {
			CarPark carPark2 = new CarPark();
			carPark2.setName("ul. œw. Antoniego");
			carPark2.setCapacity(140L);
			carParkRepo.save(carPark2);
		}

		if (carParkRepo.findByName("Nowy Targ") == null) {
			CarPark carPark3 = new CarPark();
			carPark3.setName("Nowy Targ");
			carPark3.setCapacity(334L);
			carParkRepo.save(carPark3);
		}

		System.out.println("EXIT:> DatabaseFiller.fillCarParkTable");
	}
}
