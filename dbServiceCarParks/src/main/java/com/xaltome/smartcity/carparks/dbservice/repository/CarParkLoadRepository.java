package com.xaltome.smartcity.carparks.dbservice.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.xaltome.smartcity.carparks.dbservice.model.CarPark;
import com.xaltome.smartcity.carparks.dbservice.model.CarParkLoad;

public interface CarParkLoadRepository extends PagingAndSortingRepository<CarParkLoad, Long>{
	
	@Transactional
	Page<CarParkLoad> findByCarPark(CarPark carPark, Pageable pageable);
	
	@Transactional
	Page<CarParkLoad> findAll(Pageable pageable);
	
	CarParkLoad findByCarParkLoadId(long carParkLoadId);
	
	@Query("SELECT cpl FROM CarParkLoad cpl INNER JOIN cpl.carPark cp WHERE cp.name = (:carParkName) ORDER BY cpl.timestamp DESC")
	List<CarParkLoad> getLatestEntry(@Param("carParkName") String carParkName, Pageable pageable);
	
	@Query("SELECT cpl FROM CarParkLoad cpl INNER JOIN cpl.carPark cp WHERE cp.carParkid = (:carParkId) ORDER BY cpl.timestamp DESC")
	List<CarParkLoad> getLatestEntry(@Param("carParkId") Long carParkId, Pageable pageable);
	
	//TODO: find a solution to get latest entries for all parks by one query
	@Query("SELECT cpl FROM CarParkLoad cpl INNER JOIN cpl.carPark cp WHERE cp.carParkid IN (SELECT cp.carParkid FROM cp) ORDER BY cpl.timestamp DESC")
	List<CarParkLoad> getLatestEntries(Pageable pageable);

}
