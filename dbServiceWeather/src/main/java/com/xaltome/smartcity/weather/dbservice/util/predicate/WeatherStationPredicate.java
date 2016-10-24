/**
 * 
 */
package com.xaltome.smartcity.weather.dbservice.util.predicate;

import java.util.Map;

import org.apache.commons.collections4.Predicate;

/**
 * @author kopajczy
 *
 */
public class WeatherStationPredicate implements Predicate<Map<String, Object>> {
	
	private final String weatherStationName;

	public WeatherStationPredicate(final String weatherStationName){
		this.weatherStationName = weatherStationName;
	}
	
	
	public boolean evaluate(Map<String, Object> record) {
		return weatherStationName.equals((String) record.get("Lokalizacja_Opis"));
	}

}
