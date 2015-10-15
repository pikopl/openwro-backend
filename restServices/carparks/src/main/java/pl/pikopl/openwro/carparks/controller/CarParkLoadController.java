package pl.pikopl.openwro.carparks.controller;

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
		return carParkLoadRep.findAll();
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
		CarPark carPark = carParkRep.findByName(name);
		return carParkLoadRep.findByCarPark(carPark);
	}
}
