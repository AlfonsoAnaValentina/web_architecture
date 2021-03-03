package com.tp.arqui.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tp.arqui.dto.ReceiverDTO;
import com.tp.arqui.model.ReceiverModel;
import com.tp.arqui.model.UserModel;
import com.tp.arqui.service.IReceiverService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/receiver")
public class ReceiverController {

	@Autowired
	private IReceiverService receiverService;
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	@ResponseBody
	public ReceiverDTO createReceiver(@Valid @RequestBody ReceiverDTO req) {
		return receiverService.setNewReceiver(req);
	}
	
	@PostMapping("/multiple")
	@ResponseBody
	public ReceiverDTO createReceivers(@Valid @RequestBody List<ReceiverDTO> req) {
		return receiverService.setMultiplesReceivers(req);
	}
	
	@GetMapping("/receipient")
	@ResponseBody
	public List<ReceiverDTO> getReceipient(@RequestParam Integer idMail) {
		List<ReceiverDTO> response = receiverService.getReceivers(idMail, 0);
		return response;
	}
	
	@GetMapping("/cc")
	@ResponseBody
	public List<ReceiverDTO> getCC(@RequestParam Integer idMail) {
		List<ReceiverDTO> response = receiverService.getReceivers(idMail, 1);
		return response;
	}
	
	@GetMapping("/cco")
	@ResponseBody
	public List<ReceiverDTO> getCCO(@RequestParam Integer idMail) {
		List<ReceiverDTO> response = receiverService.getReceivers(idMail, 2);
		return response;
	}
}
