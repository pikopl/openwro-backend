/**
 * 
 */
package com.xaltome.smartcity.carparks.dbservice.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xaltome.smartcity.carparks.dbservice.model.CarPark;

public interface CarParkRepository extends PagingAndSortingRepository<CarPark, Long>{
	
	@Transactional
	CarPark findByName(String name);
	
	@Transactional
	Page<CarPark> findAll(Pageable pageable);
}
