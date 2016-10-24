package com.xaltome.smartcity.weather.rest.controller;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xaltome.smartcity.weather.dbservice.model.WeatherData;
import com.xaltome.smartcity.weather.dbservice.model.WeatherStation;
import com.xaltome.smartcity.weather.dbservice.repository.WeatherDataRepository;
import com.xaltome.smartcity.weather.dbservice.repository.WeatherStationRepository;

@RestController
public class WeatherDataController {
	@Autowired
	private WeatherDataRepository weatherDataRepo;
	
	@Autowired
	private WeatherStationRepository weatherStationRepo;
	
	private static final String DEFAULT_PAGE = "0";
	
	private static final String DEFAULT_SIZE = "2147483647"; // Integer.MAX_VALUE
	
	private static final String DEFAULT_SORT_ITEM = "timestamp";
	
	private static final String DEFAULT_SORT_ORDER = "desc"; // shows the newest
	
	
	protected static final Logger LOGGER = Logger.getLogger(WeatherDataController.class);
	
	/**
	 * Requests a list of all data from weather data table
	 * 
	 * Request example:
	 * GET http://localhost:8080/weather/weatherData/
	 * 
	 * @return
	 */
	@RequestMapping(value = "/weatherData", method = RequestMethod.GET)
	public Page<WeatherData> getAllWeatherData(@RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_SIZE) int size, @RequestParam(defaultValue = DEFAULT_SORT_ITEM) String sort, @RequestParam(defaultValue = DEFAULT_SORT_ORDER) String sortOrder) {
		LOGGER.info("Entering getAllWeatherData()");
		final Page<WeatherData> allWeatherData = weatherDataRepo.findAll(createPageRequest(page, size, sort, sortOrder));
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Leaving getAllWeatherData(): %s", allWeatherData);
		} else {
			LOGGER.info("Leaving getAllWeatherData()");
		}
		return allWeatherData;
	}
	
	/**
	 * Requests a count of all data from weather data table
	 * 
	 * Request example:
	 * GET http://localhost:8080/weather/weatherDataCount/
	 * 
	 * @return
	 */
	@RequestMapping(value = "/weatherDataCount", method = RequestMethod.GET)
	public Long getAllWeatherDataCount() {
		LOGGER.info("Entering getAllWeatherDataCount()");
		final long allWeatherDataCount = weatherDataRepo.count();
		LOGGER.infof("Leaving getAllWeatherDataCount(): %s", allWeatherDataCount);
		return allWeatherDataCount;
	}
	
	/**
	 * Requests a list of all data from given weather station
	 * 
	 * Request example:
	 * GET http://localhost:8080/weather/weatherData/ESTAKADA GADOWIANKA
	 * 
	 * @return
	 */
	@RequestMapping(value = "/weatherData/{name}", method = RequestMethod.GET)
	public Page<WeatherData> getWeatherData(@PathVariable final String name, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_SIZE) int size, @RequestParam(defaultValue = DEFAULT_SORT_ITEM) String sort, @RequestParam(defaultValue = DEFAULT_SORT_ORDER) String sortOrder) {
		LOGGER.infof("Entering getWeatherData(%s)", name);
		final WeatherStation weatherStation = weatherStationRepo.findByName(name);
		final Page<WeatherData> weatherData = weatherDataRepo.findByWeatherStation(weatherStation, createPageRequest(page, size, sort, sortOrder));
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Leaving getWeatherData(): %s", weatherData);
		} else {
			LOGGER.info("Leaving getWeatherData()");
		}
		return weatherData;
	}
	
    private Pageable createPageRequest(final int page, final int size, String sort, String sortOder) {
		return new PageRequest(page, size, new Sort(Direction.fromString(sortOder), sort)); 
    }
    
	/**
	 * Requests the latest (the closest before the "now") entry from the weatherdata table for given weatherstation
	 * 
	 * Request example:
	 * GET http://localhost:8080/weather/latestEntry/ESTAKADA GADOWIANKA
	 * 
	 * @return
	 */
	@RequestMapping(value = "/latestEntry/{name}", method = RequestMethod.GET)
	public WeatherData getLatestEntry(@PathVariable final String name){
		LOGGER.infof("Entering getLatestEntry(%s)", name);
		WeatherData latestEntry = weatherDataRepo.getLatestEntry(name, createPageRequest(0, 1)).get(0);
		LOGGER.infof("Leaving getLatestEntry(): %s", latestEntry);
		return latestEntry;
	}
	
    private Pageable createPageRequest(final int page, final int size) {
		return new PageRequest(page, size); 
    }
}
