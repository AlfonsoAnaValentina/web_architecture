package com.tp.arqui.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tp.arqui.dto.ProvinceDTO;
import com.tp.arqui.model.ProvinceModel;
import com.tp.arqui.model.repository.ProvinceRepository;
import com.tp.arqui.service.IProvinceService;

@Service
public class ProvinceServiceImpl implements IProvinceService{
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private ProvinceRepository provinceRepository;

	@Override
	public Page<ProvinceDTO> getAllProvinces(Pageable pageable) {
		Page<ProvinceModel> reportList = provinceRepository.findAll(pageable);
		List<ProvinceDTO> aux = new ArrayList<ProvinceDTO>();
		for (ProvinceModel prodModel : reportList.getContent()) {
			aux.add(modelMapper.map(prodModel, ProvinceDTO.class));
		}
		Page<ProvinceDTO> response = new PageImpl<ProvinceDTO>(aux, pageable, reportList.getTotalElements());
		return response;
	}

	@Override
	public Page<ProvinceDTO> getAllCountryProvinces(Integer countryId, Pageable pageable) {
		Page<ProvinceModel> reportList = provinceRepository.findByCountryId(countryId, pageable);
		List<ProvinceDTO> aux = new ArrayList<ProvinceDTO>();
		for (ProvinceModel prodModel : reportList.getContent()) {
			aux.add(modelMapper.map(prodModel, ProvinceDTO.class));
		}
		Page<ProvinceDTO> response = new PageImpl<ProvinceDTO>(aux, pageable, reportList.getTotalElements());
		return response;
	}
}
