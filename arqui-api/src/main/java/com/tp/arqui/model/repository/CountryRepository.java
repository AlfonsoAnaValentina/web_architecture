package com.tp.arqui.model.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tp.arqui.model.CountryModel;

@Repository
public interface CountryRepository extends PagingAndSortingRepository<CountryModel, Integer> {

}
