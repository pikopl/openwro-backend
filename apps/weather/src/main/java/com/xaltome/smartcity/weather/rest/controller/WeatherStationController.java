/**
 * 
 */
package com.xaltome.smartcity.weather.rest.controller;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xaltome.smartcity.weather.dbservice.model.WeatherStation;
import com.xaltome.smartcity.weather.dbservice.repository.WeatherStationRepository;

/**
 * @author kopajczy
 *
 */
@RestController
public class WeatherStationController {
	@Autowired
	private WeatherStationRepository weatherStationRepo;
	
	protected static final Logger LOGGER = Logger.getLogger(WeatherStationController.class);

	/**
	 * Requests a list of all data from weather station
	 * 
	 * Request example:
	 * GET http://localhost:8080/weather/weatherStation/
	 * 
	 * @return
	 */
	@RequestMapping(value = "/weatherStation", method = RequestMethod.GET)
	public Iterable<WeatherStation> getAllWeatherStations() {
		LOGGER.info("Entering getAllWeatherStations()");
		final Iterable<WeatherStation> allWeatherStations = weatherStationRepo.findAll();
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Leaving getAllWeatherStations(): %s", allWeatherStations);
		} else {
			LOGGER.info("Leaving getAllWeatherStations()");
		}
		return allWeatherStations;
	}
	
	/**
	 * Requests a count of all data from weather station
	 * 
	 * Request example:
	 * GET http://localhost:8080/weather/weatherStationCount/
	 * 
	 * @return
	 */
	@RequestMapping(value = "/weatherStationCount", method = RequestMethod.GET)
	public Long getAllWeatherStationCount() {
		LOGGER.info("Entering getAllWeatherStationCount()");
		final Long allWeatherStationCount = weatherStationRepo.count();
		LOGGER.infof("Leaving getAllWeatherStationCount(): %s", allWeatherStationCount);
		return allWeatherStationCount;
	}
	
	/**
	 * Requests a list of all data from given weather station
	 * 
	 * Request example:
	 * GET http://localhost:8080/weather/weatherStation/ESTAKADA GADOWIANKA
	 * 
	 * @return
	 */
	@RequestMapping(value = "/weather/{name}", method = RequestMethod.GET)
	public WeatherStation getWeatherStation(@PathVariable final String name) {
		LOGGER.infof("Entering getWeatherStation(%s)", name);
		final WeatherStation weatherStation = weatherStationRepo.findByName(name);
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Leaving getWeatherStation(): %s", weatherStation);
		} else {
			LOGGER.infof("Leaving getWeatherStation()");
		}
		return weatherStation;
	}
}
