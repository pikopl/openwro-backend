/**
 * 
 */
package pl.pikopl.openwro.weather.dbservice.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import pl.pikopl.openwro.weather.dbservice.model.WeatherData;
import pl.pikopl.openwro.weather.dbservice.model.WeatherStation;

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
}
