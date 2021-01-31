package com.tp.arqui.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tp.arqui.dto.CityDTO;
import com.tp.arqui.dto.CountryDTO;
import com.tp.arqui.dto.ProvinceDTO;

public interface ICityService {
	Page<CityDTO> getAllCities(Pageable pageable);
	
	Page<CityDTO> getAllProvinceCities(Integer provinceId, Pageable pageable);
}
