/**
 * 
 */
package pl.pikopl.openwro.dbservice.repository;

import javax.transaction.Transactional;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.pikopl.openwro.dbservice.model.CarPark;

public interface CarParkRepository extends PagingAndSortingRepository<CarPark, Long>{
	
	@Transactional
	CarPark findByName(String name);
}
