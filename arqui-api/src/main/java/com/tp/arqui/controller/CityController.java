package com.tp.arqui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tp.arqui.dto.CityDTO;
import com.tp.arqui.dto.ProvinceDTO;
import com.tp.arqui.service.ICityService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/cities")
public class CityController {
	private static final int DEFAULT_PAGE_SIZE = 20;
	@Autowired
	private ICityService cityService;

	@GetMapping
	public Page<CityDTO> getAllCities(@PageableDefault(size = DEFAULT_PAGE_SIZE) Pageable pageable) {
		Page<CityDTO> response = cityService.getAllCities(pageable);
		return response;
	}
	
	@GetMapping("/province")
	public Page<CityDTO> getAllProvinceCities(@RequestParam Integer provinceId,@PageableDefault(size = DEFAULT_PAGE_SIZE) Pageable pageable) {
		Page<CityDTO> response = cityService.getAllProvinceCities(provinceId, pageable);
		return response;
	}
}
