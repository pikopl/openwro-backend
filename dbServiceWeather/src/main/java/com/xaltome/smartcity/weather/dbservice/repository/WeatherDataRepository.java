/**
 * 
 */
package com.xaltome.smartcity.weather.dbservice.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.xaltome.smartcity.weather.dbservice.model.WeatherData;
import com.xaltome.smartcity.weather.dbservice.model.WeatherStation;

/**
 * @author kopajczy
 *
 */
public interface WeatherDataRepository  extends PagingAndSortingRepository<WeatherData, Long>{
	@Transactional
	Page<WeatherData> findByWeatherStation(WeatherStation weatherStation, Pageable pageable);
	
	@Transactional
	Page<WeatherData> findAll(Pageable pageable);
	
	WeatherData findByWeatherDataId(long weatherDataId);
	
	@Query("SELECT wd FROM WeatherData wd INNER JOIN wd.weatherStation ws WHERE ws.name = (:weatherStationName) ORDER BY wd.timestamp DESC")
	List<WeatherData> getLatestEntry(@Param("weatherStationName") String weatherStationName, Pageable pageable);
}
