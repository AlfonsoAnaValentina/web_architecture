package com.tp.arqui.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tp.arqui.model.ProvinceModel;

@Repository
public interface ProvinceRepository extends PagingAndSortingRepository<ProvinceModel, Integer> {

	Page<ProvinceModel> findByCountryId(Integer countryId, Pageable pageable);

}