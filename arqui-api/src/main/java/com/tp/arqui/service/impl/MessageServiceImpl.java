package com.tp.arqui.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tp.arqui.dto.FolderDTO;
import com.tp.arqui.dto.LabeledMailDTO;
import com.tp.arqui.dto.MailFolderDTO;
import com.tp.arqui.dto.MessageDTO;
import com.tp.arqui.model.FolderModel;
import com.tp.arqui.model.LabeledMailModel;
import com.tp.arqui.model.MailFolderModel;
import com.tp.arqui.model.MessageModel;
import com.tp.arqui.model.repository.FolderRepository;
import com.tp.arqui.model.repository.LabeledMailRepository;
import com.tp.arqui.model.repository.MailFolderRepository;
import com.tp.arqui.model.repository.MessageRepository;
import com.tp.arqui.service.IMessageService;

@Service
public class MessageServiceImpl implements IMessageService {

	private final static Logger LOGGER = Logger.getLogger(MessageServiceImpl.class.getName());

	@Autowired
	private MessageRepository msgRepository;
	@Autowired
	private LabeledMailRepository labeledMsgRepository;
	@Autowired
	private FolderRepository folderRepository;
	@Autowired
	private MailFolderRepository mailFolderRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Page<MessageDTO> getAllMessages(Pageable pageable) {
		Page<MessageModel> reportList = msgRepository.findAll(pageable);
		List<MessageDTO> aux = new ArrayList<MessageDTO>();
		for (MessageModel prodModel : reportList.getContent()) {
			aux.add(modelMapper.map(prodModel, MessageDTO.class));
		}
		Page<MessageDTO> response = new PageImpl<MessageDTO>(aux, pageable, reportList.getTotalElements());
		return response;
	}

	@Override
	public Page<MessageDTO> getAllSentMessages(String userMail, Pageable pageable) {
		Page<MessageModel> reportList = msgRepository.findByFromAddress(userMail, pageable);
		List<MessageDTO> aux = new ArrayList<MessageDTO>();
		for (MessageModel prodModel : reportList.getContent()) {
			aux.add(modelMapper.map(prodModel, MessageDTO.class));
		}
		Page<MessageDTO> response = new PageImpl<MessageDTO>(aux, pageable, reportList.getTotalElements());
		return response;

	}

	@Override
	public Page<MessageDTO> getAllReceivedMessages(String userMail, Pageable pageable) {
		Page<MessageModel> reportList = msgRepository.findByToAddress(userMail, pageable);
		List<MessageDTO> aux = new ArrayList<MessageDTO>();
		for (MessageModel msgModel : reportList.getContent()) {
			aux.add(modelMapper.map(msgModel, MessageDTO.class));
		}
		Page<MessageDTO> response = new PageImpl<MessageDTO>(aux, pageable, reportList.getTotalElements());
		return response;
	}

	@Override
	public MessageDTO updateMessage(Integer id, @Valid MessageDTO req) {
		Optional<MessageModel> mail = msgRepository.findById(id);
		if (mail.isPresent()) {
			mail.get().setRead(true);
			MessageModel readMsg = msgRepository.save(mail.get());

			return modelMapper.map(readMsg, MessageDTO.class);
		}
		throw new RuntimeException("You are trying to update an invalid message");
	}

	@Override
	public MessageDTO getMessage(Integer id) {
		Optional<MessageModel> response = msgRepository.findById(id);
		if (response.isPresent()) {

			return modelMapper.map(response.get(), MessageDTO.class);
		}
		throw new RuntimeException("You are trying to retrieve an invalid message");
	}

	@Override
	public void deleteMessages(List<Integer> ids) {
		ids.forEach(id -> msgRepository.deleteById(id));
	}

	@Override
	public MessageDTO createMessage(@Valid MessageDTO req) {

		MessageModel request = modelMapper.map(req, MessageModel.class);
		request.setSendDate(System.currentTimeMillis());
		request.setRead(false);
		MessageModel newMsg = msgRepository.save(request);
		req.setId(newMsg.getId());
		return req;

	}

	@Override
	public MessageDTO addImage(MultipartFile file, Integer id) throws FileNotFoundException, IOException {
		File f = new File("src/main/resources/" + System.currentTimeMillis() + ".png");
		try (OutputStream os = new FileOutputStream(f)) {
			os.write(file.getBytes());
		}
		Optional<MessageModel> mail = msgRepository.findById(id);
		if (mail.isPresent()) {
			mail.get().setAttached(f.getAbsolutePath());
			MessageModel readMsg = msgRepository.save(mail.get());

			return modelMapper.map(readMsg, MessageDTO.class);
		}
		throw new RuntimeException("You are trying to update an invalid message");

	}

	@Override
	public Page<LabeledMailDTO> getAllLabeledMessages(String toAddress, Integer label, Pageable pageable) {
		Page<LabeledMailModel> reportList = labeledMsgRepository.findByToAddressAndIdFolder(toAddress, label, pageable);
		List<LabeledMailDTO> aux = new ArrayList<LabeledMailDTO>();
		for (LabeledMailModel labeledMsgModel : reportList.getContent()) {
			aux.add(modelMapper.map(labeledMsgModel, LabeledMailDTO.class));
		}
		Page<LabeledMailDTO> response = new PageImpl<LabeledMailDTO>(aux, pageable, reportList.getTotalElements());
		return response;
	}

	@Override
	public List<FolderDTO> getAllLabels(int id) {
		Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
		Page<FolderModel> reportList = folderRepository.findDistinctByIdUser(id, pageable);
		List<FolderDTO> aux = new ArrayList<FolderDTO>();
		
		for (FolderModel folderModel : reportList.getContent()) {
			aux.add(modelMapper.map(folderModel, FolderDTO.class));
			
		}
		return aux;
	}

	@Override
	public FolderDTO createLabel(@Valid FolderDTO req) {
		FolderModel request = modelMapper.map(req, FolderModel.class);
		FolderModel newLabel = folderRepository.save(request);
		req.setId(newLabel.getId());
		return req;
	}

	@Override
	public MailFolderDTO addLabeledMail(@Valid MailFolderDTO req) {
		MailFolderModel request = modelMapper.map(req, MailFolderModel.class);
		Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
		Page<MailFolderModel> labeled = mailFolderRepository.findByIdMessage(request.getIdMessage(), pageable);
		if (labeled.isEmpty()) {
			MailFolderModel newLabel = mailFolderRepository.save(request);
			req.setId(newLabel.getId());
		}

		return req;
	}
	
	@Override
	public void addMultipleLabeledMail(List<MailFolderDTO> mails) {
			
		mails.forEach(mail -> addLabeledMail(mail));
	}

}
