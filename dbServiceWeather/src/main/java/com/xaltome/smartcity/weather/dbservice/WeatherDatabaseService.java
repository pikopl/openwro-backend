/**
 * 
 */
package com.xaltome.smartcity.weather.dbservice;

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

import com.xaltome.smartcity.weather.dbservice.model.ShowerType;
import com.xaltome.smartcity.weather.dbservice.model.WeatherData;
import com.xaltome.smartcity.weather.dbservice.model.WeatherStation;
import com.xaltome.smartcity.weather.dbservice.repository.WeatherDataRepository;
import com.xaltome.smartcity.weather.dbservice.repository.WeatherStationRepository;
import com.xaltome.smartcity.weather.dbservice.util.predicate.WeatherStationPredicate;

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
			LOGGER.tracef("Entering fillWeatherData(): %s", data);
		} else {
			LOGGER.info("Entering fillWeatherData()");
		}
		fillWeatherDataTable(data);
		LOGGER.info("Leaving fillWeatherData()");
	}
	
	protected void fillWeatherDataTable(final List<Map<String, Object>> data){
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Entering fillWeatherDataTable(): %s", data);
		}
		for (Map<String, Object> record : data) {
			try {
				addNewWeatherData(record);
			} catch (Exception e) {
				LOGGER.errorf("Exception in fillWeatherDataTable for data (%s, %s): %s", record.get("Czas_Rejestracji"), record.get("Lokalizacja_Opis"), e);
				continue;
			}
			LOGGER.debugf("fillWeatherDataTable successfully save record (%s, %s)", record.get("Czas_Rejestracji"), record.get("Lokalizacja_Opis"));
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Leaving fillWeatherDataTable()");
		}
	}
	
	protected WeatherStation addNewWeatherStation(final String name){
		LOGGER.infof("Entering addNewWeatherStation() with name: %s", name);
		final WeatherStation weatherStation = new WeatherStation();
		weatherStation.setName(name);
		LOGGER.infof("Exiting/saving addNewWeatherStation()");
		return weatherStationRepo.save(weatherStation);
	}
	
	protected void addNewWeatherData(final Map<String, Object> record) throws ParseException{
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Entering addNewWeatherData(): %s", record);
		}
		WeatherData weatherData = new WeatherData();
		//TODO: move strings to property file
		weatherData.setTimestamp(parseTimestamp((String) record.get("Czas_Rejestracji")));
		weatherData.setWindSpeed(parseDouble((String) record.get("Wiatr_V")));
		weatherData.setWindDirection(parseDouble((String) record.get("Wiatr_Kierunek")));
		weatherData.setHumidity(parseDouble((String) record.get("Wilgotnosc")));
		weatherData.setAirTemperature(parseDouble((String) record.get("T_Powietrza")));
		weatherData.setGroundTemperature(parseDouble((String) record.get("T_Grunt")));
		weatherData.setShowerType(ShowerType.valueOf(parseInt((String) record.get("Opad_Typ"))));
		WeatherStation weatherStation = weatherStationRepo.findByName((String) record.get("Lokalizacja_Opis"));
		if (weatherStation == null) { // add dynamically new weather station
			weatherStation = addNewWeatherStation((String) record.get("Lokalizacja_Opis"));
		}
		weatherData.setWeatherStation(weatherStation);
		weatherDataRepo.save(weatherData);
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Leaving addNewWeatherData(): %s", record);
		}
	}
	
	protected Timestamp parseTimestamp(final String timestampString) throws ParseException {
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Entering parseTimestamp(): %s", timestampString);
		}
		//TODO: move string to property file
		//Locale.FRENCH solves problem with noon date inserting to the DB as 00.
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS",  Locale.FRENCH);
	    Date parsedDate = null;
	    //TODO: check if timestampString != null
		try { //TODO: get rid of exception, handle in fillCarkParkLoadTable
			//TODO: move time zone to weather station table column
			dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Warsaw"));
			parsedDate = dateFormat.parse(timestampString);
		} catch (ParseException e) {
			LOGGER.error("Exception in parseTimestamp()", e);
			throw e;
		}
	    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Leaving parseTimestamp(): %s", timestamp);
		}
		return timestamp;
	}
	
	protected Double parseDouble(final String doubleString){
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Entering parseDouble(): %s", doubleString);
		}
		Double number = null;
		if (doubleString != null && !doubleString.equals("")) {
			number = Double.parseDouble(doubleString);
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Leaving parseDouble(): %s", number);
		}
		return number;
	}
	
	protected int parseInt(final String intString){
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Entering parseInt(): %s", intString);
		}
		int number = -1;
		if (intString != null && !intString.equals("")) {
			number = Integer.parseInt(intString);
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Leaving parseInt(0: %s", number);
		}
		return number;
	}
	
	public void detectChanges(final List<Map<String, Object>> data) throws ParseException{
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Entering detectChanges(): %s", data);
		} else {
			LOGGER.info("Entering detectChanges()");
		}
		Iterable<WeatherStation> weatherStationList = weatherStationRepo.findAll();
		for (WeatherStation weatherStation : weatherStationList) {
			final String weatherStationName = weatherStation.getName();
			final Predicate<Map<String, Object>> weatherStationPredicate = new WeatherStationPredicate(weatherStationName);
			Collection<Map<String, Object>> selectedWeatherStationData = CollectionUtils.select(data, weatherStationPredicate);
			//selectedWeatherStationData should be in DESC order, to avoid linear list iteration and date comparison
			Collections.sort((List<Map<String, Object>>) selectedWeatherStationData, new TimestampComparator());
			if (LOGGER.isTraceEnabled()) {
				LOGGER.tracef("detectChanges() selectedWeatherStationData: %s", selectedWeatherStationData);
			}
			
			final WeatherData latestEntry = weatherDataRepo.getLatestEntry(weatherStationName, new PageRequest(0, 1)).get(0);
			final Timestamp timestampFromDB = latestEntry.getTimestamp();
			
			List<Map<String, Object>> changeList = new LinkedList<Map<String,Object>>();
			for (Map<String, Object> record : selectedWeatherStationData) {
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
			fillWeatherDataTable(changeList);
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
