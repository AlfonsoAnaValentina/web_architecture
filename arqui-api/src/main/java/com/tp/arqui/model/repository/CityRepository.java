package com.tp.arqui.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tp.arqui.model.CityModel;

@Repository
public interface CityRepository extends PagingAndSortingRepository<CityModel, Integer> {

	Page<CityModel> findByProvinceId(Integer provinceId, Pageable pageable);

}

