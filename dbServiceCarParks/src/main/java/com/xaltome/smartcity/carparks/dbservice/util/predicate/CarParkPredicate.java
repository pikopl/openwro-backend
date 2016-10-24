/**
 * 
 */
package com.xaltome.smartcity.carparks.dbservice.util.predicate;


import java.util.Map;

import org.apache.commons.collections4.Predicate;

/**
 * @author kopajczy
 *
 */
public class CarParkPredicate implements Predicate<Map<String, Object>>{
	
	private String carParkName;
	
	public CarParkPredicate(final String carParkName) {
		this.carParkName = carParkName;
	}

	@Override
	public boolean evaluate(final Map<String, Object> record) {
		return carParkName.equals((String) record.get("Nazwa"));
	}

}
