/**
 * 
 */
package pl.pikopl.openwro.weather.dbservice;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.pikopl.openwro.weather.dbservice.model.ShowerType;
import pl.pikopl.openwro.weather.dbservice.model.WeatherData;
import pl.pikopl.openwro.weather.dbservice.model.WeatherStation;
import pl.pikopl.openwro.weather.dbservice.repository.WeatherDataRepository;
import pl.pikopl.openwro.weather.dbservice.repository.WeatherStationRepository;

/**
 * @author kopajczy
 *
 */
@Service
public class WeatherDatabaseService {
	@Autowired
	private WeatherDataRepository weatherDataRepo;
	
	@Autowired
	private WeatherStationRepository weatherStationRepo;
	
	protected static final Logger LOGGER = Logger.getLogger(WeatherDatabaseService.class);
	
	public void fillWeatherData(final List<Map<String, Object>> data){
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Entering fillWeatherData: %s", data);
		} else {
			LOGGER.info("Entering fillWeatherData");
		}
		fillWeatherDataTable(data);
		LOGGER.info("Leaving fillWeatherData");
	}
	
	protected void fillWeatherDataTable(final List<Map<String, Object>> data){
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Entering fillWeatherDataTable: %s", data);
		} else {
			LOGGER.info("Entering fillWeatherDataTable");
		}
		for (Map<String, Object> record : data) {
			
			try {
				WeatherData weatherData = new WeatherData();
				//TODO: move strings to property file
				weatherData.setTimestamp(parseTimestamp((String) record.get("Czas_Rejestracji")));
				weatherData.setWindSpeed(Double.parseDouble((String) record.get("Wiatr_V")));
				weatherData.setWindDirection(Double.parseDouble((String) record.get("Wiatr_Kierunek")));
				weatherData.setHumidity(Double.parseDouble((String) record.get("Wilgotnosc")));
				weatherData.setAirTemperature(Double.parseDouble((String) record.get("T_Powietrza")));
				weatherData.setGroundTemperature(Double.parseDouble((String) record.get("T_Grunt")));
				weatherData.setShowerType(ShowerType.valueOf((Integer) record.get("Opad_Typ")));
				WeatherStation weatherStation = weatherStationRepo.findByName((String) record.get("Lokalizacja_Opis"));
				if (weatherStation == null) { // add dynamically new weather station
					weatherStation = addNewWeatherStation((String) record.get("Lokalizacja_Opis"));
				}
				weatherData.setWeatherStation(weatherStation);
				weatherDataRepo.save(weatherData);
			} catch (Exception e) {
				LOGGER.errorf("Exception in fillWeatherDataTable for data (%s, %s)", record.get("Czas_Rejestracji"), record.get("Lokalizacja_Opis"), e);
				continue;
			}
			LOGGER.debugf("fillWeatherDataTable successfully save record (%s, %s)", record.get("Czas_Rejestracji"), record.get("Lokalizacja_Opis"));
		}
		LOGGER.info("Leaving fillWeatherDataTable");
	}
	
	protected WeatherStation addNewWeatherStation(final String name){
		LOGGER.infof("Entering addNewWeatherStation with name: %s", name);
		final WeatherStation weatherStation = new WeatherStation();
		weatherStation.setName(name);
		LOGGER.infof("Exiting/saving addNewWeatherStation");
		return weatherStationRepo.save(weatherStation);
	}
	
	protected Timestamp parseTimestamp(final String timestampString) throws ParseException {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Entering parseTimestamp: %s", timestampString);
		}
		//TODO: move string to property file
		//Locale.FRENCH solves problem with noon date inserting to the DB as 00.
	    SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy HH:mm:ss a"/*,  Locale.FRENCH*/);
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
}
