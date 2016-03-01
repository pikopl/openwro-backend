package pl.pikopl.openwro.carparks.rest.controller;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.pikopl.openwro.dbservice.model.CarParkLoad;
import pl.pikopl.openwro.dbservice.model.CarPark;
import pl.pikopl.openwro.dbservice.repository.CarParkLoadRepository;
import pl.pikopl.openwro.dbservice.repository.CarParkRepository;

/**
 * Rest controller for CarParkLoad model. Provides REST API.
 */
@RestController
public class CarParkLoadController {

	@Autowired
	private CarParkLoadRepository carParkLoadRep;
	
	@Autowired
	private CarParkRepository carParkRep;
	
	
	protected static final Logger LOGGER = Logger.getLogger(CarParkLoadController.class);
	/**
	 * Requests a list of all data from car park load
	 * 
	 * Request example:
	 * GET http://localhost:8080/carparks/carParkLoads/
	 * 
	 * @return
	 */
	@RequestMapping(value = "/carParkLoads", method = RequestMethod.GET)
	public Iterable<CarParkLoad> getAllCarParkLoads() {
		LOGGER.info("Entering getAllCarParkLoads()");
		final Iterable<CarParkLoad> allCarkParkLoads = carParkLoadRep.findAll();
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Leaving getAllCarParkLoads(): %s", allCarkParkLoads);
		} else {
			LOGGER.info("Leaving getAllCarParkLoads()");
		}
		return allCarkParkLoads;
	}
	
	/**
	 * Requests a count of all data from car park load
	 * 
	 * Request example:
	 * GET http://localhost:8080/carparks/carParkLoadsCount/
	 * 
	 * @return
	 */
	@RequestMapping(value = "/carParkLoadsCount", method = RequestMethod.GET)
	public Long getAllCarParkLoadsCount() {
		LOGGER.info("Entering getAllCarParkLoadsCount()");
		final long allCarkParkLoadsCount = carParkLoadRep.count();
		LOGGER.infof("Leaving getAllCarParkLoadsCount(): %s", allCarkParkLoadsCount);
		return allCarkParkLoadsCount;
	}
	
	/**
	 * Requests a list of all data from given car park
	 * 
	 * Request example:
	 * GET http://localhost:8080/carparks/carParkLoads/renoma
	 * 
	 * @return
	 */
	@RequestMapping(value = "/carParkLoads/{name}", method = RequestMethod.GET)
	public Iterable<CarParkLoad> getCarParkLoad(@PathVariable final String name) {
		LOGGER.infof("Entering getCarParkLoad(%s)", name);
		final CarPark carPark = carParkRep.findByName(name);
		final Iterable<CarParkLoad> carkParkLoad = carParkLoadRep.findByCarPark(carPark);
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Leaving getCarParkLoad(): %s", carkParkLoad);
		} else {
			LOGGER.info("Leaving getCarParkLoad()");
		}
		return carkParkLoad;
	}
}
