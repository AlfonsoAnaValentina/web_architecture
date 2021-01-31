package com.tp.arqui.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tp.arqui.dto.CityDTO;
import com.tp.arqui.dto.ProvinceDTO;
import com.tp.arqui.model.CityModel;
import com.tp.arqui.model.ProvinceModel;
import com.tp.arqui.model.repository.CityRepository;
import com.tp.arqui.service.ICityService;
import com.tp.arqui.service.IProvinceService;

@Service
public class CityServiceImpl implements ICityService{
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private CityRepository provinceRepository;

	@Override
	public Page<CityDTO> getAllCities(Pageable pageable) {
		Page<CityModel> reportList = provinceRepository.findAll(pageable);
		List<CityDTO> aux = new ArrayList<CityDTO>();
		for (CityModel prodModel : reportList.getContent()) {
			aux.add(modelMapper.map(prodModel, CityDTO.class));
		}
		Page<CityDTO> response = new PageImpl<CityDTO>(aux, pageable, reportList.getTotalElements());
		return response;
	}

	@Override
	public Page<CityDTO> getAllProvinceCities(Integer provinceId, Pageable pageable) {
		Page<CityModel> reportList = provinceRepository.findByProvinceId(provinceId, pageable);
		List<CityDTO> aux = new ArrayList<CityDTO>();
		for (CityModel prodModel : reportList.getContent()) {
			aux.add(modelMapper.map(prodModel, CityDTO.class));
		}
		Page<CityDTO> response = new PageImpl<CityDTO>(aux, pageable, reportList.getTotalElements());
		return response;
	}
}