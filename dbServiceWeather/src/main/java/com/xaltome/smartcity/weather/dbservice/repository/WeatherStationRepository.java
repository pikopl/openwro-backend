/**
 * 
 */
package com.xaltome.smartcity.weather.dbservice.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xaltome.smartcity.weather.dbservice.model.WeatherStation;


/**
 * @author kopajczy
 *
 */
public interface WeatherStationRepository extends PagingAndSortingRepository<WeatherStation, Long>{
	@Transactional
	WeatherStation findByName(String name);
	
	@Transactional
	Page<WeatherStation> findAll(Pageable pageable);
}
