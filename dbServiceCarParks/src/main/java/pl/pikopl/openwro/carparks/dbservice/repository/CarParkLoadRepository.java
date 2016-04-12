package pl.pikopl.openwro.carparks.dbservice.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import pl.pikopl.openwro.carparks.dbservice.model.CarPark;
import pl.pikopl.openwro.carparks.dbservice.model.CarParkLoad;

public interface CarParkLoadRepository extends PagingAndSortingRepository<CarParkLoad, Long>{
	
	@Transactional
	Page<CarParkLoad> findByCarPark(CarPark carPark, Pageable pageable);
	
	@Transactional
	Page<CarParkLoad> findAll(Pageable pageable);
	
	CarParkLoad findByCarParkLoadId(long carParkLoadId);
	
	@Query("SELECT cpl FROM CarParkLoad cpl INNER JOIN cpl.carPark cp WHERE cp.name = (:carParkName) ORDER BY cpl.timestamp DESC")
	List<CarParkLoad> getLatestEntry(@Param("carParkName") String carParkName, Pageable pageable);

}
