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

import com.tp.arqui.dto.ProvinceDTO;
import com.tp.arqui.service.IProvinceService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/provinces")
public class ProvinceController {
	private static final int DEFAULT_PAGE_SIZE = 20;
	@Autowired
	private IProvinceService provinceService;

	@GetMapping
	public Page<ProvinceDTO> getAllProvinces(@PageableDefault(size = DEFAULT_PAGE_SIZE) Pageable pageable) {
		Page<ProvinceDTO> response = provinceService.getAllProvinces(pageable);
		return response;
	}

	@GetMapping("/country")
	public Page<ProvinceDTO> getAllCountryProvinces(@RequestParam Integer countryId,@PageableDefault(size = DEFAULT_PAGE_SIZE) Pageable pageable) {
		Page<ProvinceDTO> response = provinceService.getAllCountryProvinces(countryId, pageable);
		return response;
	}
}
