package com.tp.arqui.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tp.arqui.dto.CountryDTO;
import com.tp.arqui.dto.MessageDTO;
import com.tp.arqui.dto.ProvinceDTO;

public interface IProvinceService {
	Page<ProvinceDTO> getAllProvinces(Pageable pageable);
	
	Page<ProvinceDTO> getAllCountryProvinces(Integer countryId, Pageable pageable);
}
