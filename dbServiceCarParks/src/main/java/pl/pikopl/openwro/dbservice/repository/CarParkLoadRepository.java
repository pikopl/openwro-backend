package pl.pikopl.openwro.dbservice.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import pl.pikopl.openwro.dbservice.model.CarPark;
import pl.pikopl.openwro.dbservice.model.CarParkLoad;

public interface CarParkLoadRepository extends PagingAndSortingRepository<CarParkLoad, Long>{
	
	@Transactional
	List<CarParkLoad> findByCarPark(CarPark carPark);
	
	@Transactional
	Page<CarParkLoad> findAll(Pageable pageable);
	
	CarParkLoad findByCarParkLoadId(long carParkLoadId);

}
