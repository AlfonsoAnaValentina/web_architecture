package com.tp.arqui.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tp.arqui.dto.CountryDTO;

public interface ICountryService {
	Page<CountryDTO> getAllCountries(Pageable pageable);
}
