package pl.pikopl.openwro.carparks.rest.controller;

import java.util.List;

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

import pl.pikopl.openwro.carparks.dbservice.model.CarPark;
import pl.pikopl.openwro.carparks.dbservice.model.CarParkLoad;
import pl.pikopl.openwro.carparks.dbservice.repository.CarParkLoadRepository;
import pl.pikopl.openwro.carparks.dbservice.repository.CarParkRepository;

/**
 * Rest controller for CarParkLoad model. Provides REST API.
 */
@RestController
public class CarParkLoadController {

	@Autowired
	private CarParkLoadRepository carParkLoadRep;
	
	@Autowired
	private CarParkRepository carParkRep;
	
	private static final String DEFAULT_PAGE = "0";
	
	private static final String DEFAULT_SIZE = "2147483647"; // Integer.MAX_VALUE
	
	private static final String DEFAULT_SORT_ITEM = "timestamp";
	
	private static final String DEFAULT_SORT_ORDER = "desc"; // shows the newest
	
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
	public Page<CarParkLoad> getAllCarParkLoads(@RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_SIZE) int size, @RequestParam(defaultValue = DEFAULT_SORT_ITEM) String sort, @RequestParam(defaultValue = DEFAULT_SORT_ORDER) String sortOrder) {
		LOGGER.info("Entering getAllCarParkLoads()");
		final Page<CarParkLoad> allCarkParkLoads = carParkLoadRep.findAll(createPageRequest(page, size, sort, sortOrder));
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
	public Page<CarParkLoad> getCarParkLoad(@PathVariable final String name, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_SIZE) int size, @RequestParam(defaultValue = DEFAULT_SORT_ITEM) String sort, @RequestParam(defaultValue = DEFAULT_SORT_ORDER) String sortOrder) {
		LOGGER.infof("Entering getCarParkLoad(%s)", name);
		final CarPark carPark = carParkRep.findByName(name);
		final Page<CarParkLoad> carkParkLoad = carParkLoadRep.findByCarPark(carPark, createPageRequest(page, size, sort, sortOrder));
		if (LOGGER.isTraceEnabled()) {
			LOGGER.tracef("Leaving getCarParkLoad(): %s", carkParkLoad);
		} else {
			LOGGER.info("Leaving getCarParkLoad()");
		}
		return carkParkLoad;
	}
	
	/**
	 * Requests the latest (the closest before the "now") entry from the carparkload table for given carpark id
	 * 
	 * Request example:
	 * GET http://localhost:8080/carparks/latestEntry/1
	 * 
	 * @return
	 */
	@RequestMapping(value = "/latestEntry/{idString}", method = RequestMethod.GET)
	public CarParkLoad getLatestEntry(@PathVariable final String idString){
		LOGGER.infof("Entering getLatestEntry(%s)", idString);
		//TODO: add error handling when car park not found
		CarParkLoad latestEntry = carParkLoadRep.getLatestEntry(Long.valueOf(idString), createPageRequest(0, 1)).get(0);
		LOGGER.infof("Leaving getLatestEntry(): %s", latestEntry);
		return latestEntry;
	}
	
    private Pageable createPageRequest(final int page, final int size, String sort, String sortOder) {
		return new PageRequest(page, size, new Sort(Direction.fromString(sortOder), sort)); 
    }
    
    private Pageable createPageRequest(final int page, final int size) {
		return new PageRequest(page, size); 
    }
}
