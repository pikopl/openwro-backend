/**
 * 
 */
package com.xaltome.smartcity.carparks.rest.controller;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xaltome.smartcity.carparks.dbservice.model.CarPark;
import com.xaltome.smartcity.carparks.dbservice.repository.CarParkRepository;

/**
 * Rest controller for CarPark model. Provides REST API.
 */
@RestController
public class CarParkController {
	@Autowired
	private CarParkRepository carParkRep;
	
	protected static final Logger LOGGER = Logger.getLogger(CarParkController.class);
	
	/**
	 * Requests a list of all data from car park
	 * 
	 * Request example:
	 * GET http://localhost:8080/carparks/carParks/
	 * 
	 * @return
	 */
	@RequestMapping(value = "/carParks", method = RequestMethod.GET)
	public Iterable<CarPark> getAllCarPark() {
		LOGGER.info("Entering getAllCarPark()");
		final Iterable<CarPark> allCarkParks = carParkRep.findAll();
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Leaving getAllCarPark(): %s", allCarkParks);
		} else {
			LOGGER.info("Leaving getAllCarPark()");
		}
		return allCarkParks;
	}
	
	/**
	 * Requests a count of all data from car park
	 * 
	 * Request example:
	 * GET http://localhost:8080/carparks/carParksCount/
	 * 
	 * @return
	 */
	@RequestMapping(value = "/carParksCount", method = RequestMethod.GET)
	public Long getAllCarParkCount() {
		LOGGER.info("Entering getAllCarParkCount()");
		final Long allCarkParksCount = carParkRep.count();
		LOGGER.infof("Leaving getAllCarParkCount(): %s", allCarkParksCount);
		return allCarkParksCount;
	}
	
	/**
	 * Requests a list of all data from given car park
	 * 
	 * Request example:
	 * GET http://localhost:8080/carparks/carParks/renoma
	 * 
	 * @return
	 */
	@RequestMapping(value = "/carParks/{name}", method = RequestMethod.GET)
	public CarPark getCarPark(@PathVariable final String name) {
		LOGGER.infof("Entering getCarPark(%s)", name);
		final CarPark carPark = carParkRep.findByName(name);
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Leaving getCarPark(): %s", carPark);
		} else {
			LOGGER.infof("Leaving getCarPark()");
		}
		return carPark;
	}
}
