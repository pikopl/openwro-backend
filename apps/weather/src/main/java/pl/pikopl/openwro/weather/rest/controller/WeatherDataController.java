package pl.pikopl.openwro.weather.rest.controller;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.pikopl.openwro.weather.dbservice.model.WeatherData;
import pl.pikopl.openwro.weather.dbservice.model.WeatherStation;
import pl.pikopl.openwro.weather.dbservice.repository.WeatherDataRepository;
import pl.pikopl.openwro.weather.dbservice.repository.WeatherStationRepository;

@RestController
public class WeatherDataController {
	@Autowired
	private WeatherDataRepository weatherDataRepo;
	
	@Autowired
	private WeatherStationRepository weatherStationRepo;
	
	
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
	public Iterable<WeatherData> getAllWeatherData() {
		LOGGER.info("Entering getAllWeatherData()");
		final Iterable<WeatherData> allWeatherData = weatherDataRepo.findAll();
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
	public Iterable<WeatherData> getWeatherData(@PathVariable final String name) {
		LOGGER.infof("Entering getWeatherData(%s)", name);
		final WeatherStation weatherStation = weatherStationRepo.findByName(name);
		final Iterable<WeatherData> weatherData = weatherDataRepo.findByWeatherStation(weatherStation);
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Leaving getWeatherData(): %s", weatherData);
		} else {
			LOGGER.info("Leaving getWeatherData()");
		}
		return weatherData;
	}
}
