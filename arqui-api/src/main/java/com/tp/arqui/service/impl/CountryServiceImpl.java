package com.tp.arqui.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tp.arqui.dto.CountryDTO;
import com.tp.arqui.model.CountryModel;
import com.tp.arqui.model.repository.CountryRepository;
import com.tp.arqui.service.ICountryService;

@Service
public class CountryServiceImpl implements ICountryService{
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private CountryRepository countryRepository;

	@Override
	public Page<CountryDTO> getAllCountries(Pageable pageable) {
		Page<CountryModel> reportList = countryRepository.findAll(pageable);
		List<CountryDTO> aux = new ArrayList<CountryDTO>();
		for (CountryModel prodModel : reportList.getContent()) {
			aux.add(modelMapper.map(prodModel, CountryDTO.class));
		}
		Page<CountryDTO> response = new PageImpl<CountryDTO>(aux, pageable, reportList.getTotalElements());
		return response;
	}
}
