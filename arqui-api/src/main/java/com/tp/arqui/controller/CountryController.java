package com.tp.arqui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tp.arqui.dto.CountryDTO;
import com.tp.arqui.service.ICountryService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/countries")
public class CountryController {
	private static final int DEFAULT_PAGE_SIZE = 20;
	@Autowired
	private ICountryService countryService;

	@GetMapping
	public Page<CountryDTO> getAllCountries(@PageableDefault(size = DEFAULT_PAGE_SIZE) Pageable pageable) {
		Page<CountryDTO> response = countryService.getAllCountries(pageable);
		return response;
	}
}
