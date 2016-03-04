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
	List<WeatherData> findByWeatherStation(WeatherStation weatherStation);
	
	@Transactional
	Page<WeatherData> findAll(Pageable pageable);
	
	WeatherData findByWeatherDataId(long weatherDataId);
}
