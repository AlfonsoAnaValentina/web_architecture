package com.tp.arqui.service;

import java.util.List;

import javax.validation.Valid;

import com.tp.arqui.dto.ReceiverDTO;
import com.tp.arqui.model.ReceiverModel;

public interface IReceiverService {

	ReceiverDTO setNewReceiver(ReceiverDTO newReceiver);

	ReceiverDTO setMultiplesReceivers(List<ReceiverDTO> newReceivers);

	List<ReceiverDTO> getReceivers(Integer idMail, Integer type);
	
	List<ReceiverDTO> getReceivedMails(Integer idMail);

}
