/**
 * 
 */
package pl.pikopl.openwro.carparks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.pikopl.openwro.dbservice.model.CarPark;
import pl.pikopl.openwro.dbservice.repository.CarParkRepository;

/**
 * Rest controller for CarPark model. Provides REST API.
 */
@RestController
public class CarParkController {
	@Autowired
	private CarParkRepository carParkRep;
	
	/**
	 * Requests a list of all data from car park load
	 * 
	 * Request example:
	 * GET http://localhost:8080/carparks/carParks/
	 * 
	 * @return
	 */
	@RequestMapping(value = "/carParks", method = RequestMethod.GET)
	public Iterable<CarPark> getAllCarPark() {
		return carParkRep.findAll();
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
	public CarPark getCarParkLoad(@PathVariable final String name) {
		return carParkRep.findByName(name);
	}
}
