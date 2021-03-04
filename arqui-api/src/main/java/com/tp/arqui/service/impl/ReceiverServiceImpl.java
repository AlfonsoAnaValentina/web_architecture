package com.tp.arqui.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.tp.arqui.dto.MessageDTO;
import com.tp.arqui.dto.ReceiverDTO;
import com.tp.arqui.model.MessageModel;
import com.tp.arqui.model.ReceiverModel;
import com.tp.arqui.model.repository.ReceiverRepository;
import com.tp.arqui.service.IReceiverService;

@Service
public class ReceiverServiceImpl implements IReceiverService{
	@Autowired
	private ReceiverRepository receiverRepository;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public ReceiverDTO setNewReceiver(ReceiverDTO newReceiver) {
		ReceiverModel request = modelMapper.map(newReceiver, ReceiverModel.class);
		request.setDate(System.currentTimeMillis());
		ReceiverModel newUsr = receiverRepository.save(request);
		return newReceiver;
	}

	@Override
	public ReceiverDTO setMultiplesReceivers(List<ReceiverDTO> newReceivers) {
		for(ReceiverDTO singleReceiver : newReceivers) {
			this.setNewReceiver(singleReceiver);
		}
		return null;
	}

	@Override
	public List<ReceiverDTO> getReceivers(Integer idMail, Integer type) {
		
		List<ReceiverModel> aa = receiverRepository.findByIdMailAndTypeOrderByDate(idMail, type);
		
		List<ReceiverDTO> receptores = new ArrayList<>();
		for (ReceiverModel rm : aa) {
			receptores.add(modelMapper.map(rm, ReceiverDTO.class));
		}
		
		return receptores;
	}

	@Override
	public List<ReceiverDTO> getReceivedMails(Integer idMail) {
		List<ReceiverModel> aa = receiverRepository.findByIdReceiverOrderByDate(idMail);
		
		List<ReceiverDTO> receptores = new ArrayList<>();
		for (ReceiverModel rm : aa) {
			receptores.add(modelMapper.map(rm, ReceiverDTO.class));
		}
		
		return receptores;
	}
	
	

}
