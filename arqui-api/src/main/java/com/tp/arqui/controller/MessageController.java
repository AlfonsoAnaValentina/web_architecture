package com.tp.arqui.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tp.arqui.dto.FolderDTO;
import com.tp.arqui.dto.LabeledMailDTO;
import com.tp.arqui.dto.MailFolderDTO;
import com.tp.arqui.dto.MessageDTO;
import com.tp.arqui.service.IMessageService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/message")
public class MessageController {

	private static final int DEFAULT_PAGE_SIZE = 10;
	
	@Autowired
	private IMessageService msgService;
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	@ResponseBody
	public MessageDTO createMessage(@Valid @RequestBody MessageDTO req) {
		return msgService.createMessage(req);
	}

	
	@GetMapping
	public Page<MessageDTO> getMessages(@PageableDefault(size = DEFAULT_PAGE_SIZE) Pageable pageable) {
		Page<MessageDTO> response = msgService.getAllMessages(pageable);
		return response;
	}

	@GetMapping("/sent")
	public Page<MessageDTO> getSent(@RequestParam String userMail, @PageableDefault(size = DEFAULT_PAGE_SIZE) Pageable pageable) {
		Page<MessageDTO> response = msgService.getAllSentMessages(userMail, pageable);
		return response;
	}
	
	@GetMapping("/received")
	public Page<MessageDTO> getReceived(@RequestParam String userMail, @PageableDefault(size = DEFAULT_PAGE_SIZE) Pageable pageable) {
		Page<MessageDTO> response = msgService.getAllReceivedMessages(userMail, pageable);
		return response;
	}
	
	@GetMapping("/label")
	public Page<LabeledMailDTO> getLabelMessages(
			@RequestParam String userMail, 
			@RequestParam Integer label, 
			@PageableDefault(size = DEFAULT_PAGE_SIZE) Pageable pageable) {
		Page<LabeledMailDTO> response = msgService.getAllLabeledMessages(userMail, label, pageable);
		return response;
	}
	
	@GetMapping("/labels")
	public List<FolderDTO> getAllLabels(@RequestParam String userMail) {
		List<FolderDTO> response = msgService.getAllLabels(userMail);
		return response;
	}
	
	@PostMapping("/label")
	@ResponseBody
	public FolderDTO createLabel(@Valid @RequestBody FolderDTO req) {
		return msgService.createLabel(req);
	}
	
	@PostMapping("/mail-label")
	@ResponseBody
	public MailFolderDTO addLabeledMail(@Valid @RequestBody MailFolderDTO req) {
		req.setId(null);
		return msgService.addLabeledMail(req);
	}


	@PutMapping("/{id}")
	@ResponseBody
	public MessageDTO updateMessage(@PathVariable Integer id, @Valid @RequestBody MessageDTO req) {
		MessageDTO response = msgService.updateMessage(id, req);
		return response;
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	public MessageDTO getMessage(@PathVariable Integer id) {
		MessageDTO response = msgService.getMessage(id);
		return response;
	}
	
	@DeleteMapping
	@ResponseBody
	public void deleteMessages(@RequestParam  List<Integer> ids) {
		msgService.deleteMessages(ids);
	}
	
	
	@PostMapping("/{id}/upload-image")
	public MessageDTO uplaodImage(@PathVariable Integer id, @RequestParam("imageFile") MultipartFile file) throws FileNotFoundException, IOException {
		return msgService.addImage(file, id);
	}
	
	@GetMapping("/image")
	public @ResponseBody byte[] getImageAsByteArray( @RequestParam String path ) throws IOException {
		InputStream in = new FileInputStream(path);
		return IOUtils.toByteArray(in);
	}
	
}
