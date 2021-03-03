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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tp.arqui.dto.FolderDTO;
import com.tp.arqui.dto.LabeledMailDTO;
import com.tp.arqui.dto.MailFolderDTO;
import com.tp.arqui.dto.MessageDTO;
import com.tp.arqui.dto.ReceiverDTO;
import com.tp.arqui.model.FolderModel;
import com.tp.arqui.model.LabeledMailModel;
import com.tp.arqui.model.MailFolderModel;
import com.tp.arqui.model.MessageModel;
import com.tp.arqui.model.ReceiverModel;
import com.tp.arqui.model.UserModel;
import com.tp.arqui.model.repository.FolderRepository;
import com.tp.arqui.model.repository.LabeledMailRepository;
import com.tp.arqui.model.repository.MailFolderRepository;
import com.tp.arqui.model.repository.MessageRepository;
import com.tp.arqui.model.repository.ReceiverRepository;
import com.tp.arqui.model.repository.UserRepository;
import com.tp.arqui.service.IMessageService;
import com.tp.arqui.service.IReceiverService;

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
	private UserRepository userRepository;
	@Autowired
	private ReceiverRepository receiverRepository;
	@Autowired
	private IReceiverService receiverService;
	
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
			MessageDTO msg = modelMapper.map(prodModel, MessageDTO.class);
			msg.setToAddress(new ArrayList<>());
			msg.getToAddress().addAll(getEmailName(msg, ReceiverDTO.TO));

			msg.setToCcAddress(new ArrayList<>());
			msg.getToCcAddress().addAll(getEmailName(msg, ReceiverDTO.CC));

			msg.setToCcoAddress(new ArrayList<>());
			msg.getToCcoAddress().addAll(getEmailName(msg, ReceiverDTO.CCO));

			
			aux.add(msg);
		}
		Page<MessageDTO> response = new PageImpl<MessageDTO>(aux, pageable, reportList.getTotalElements());
		return response;

	}

	private List<String> getEmailName(MessageDTO msg, int type) {
		List<String> emails = new ArrayList<>();
		List<ReceiverModel> toRec = receiverRepository.findByIdMailAndType(msg.getId(), type);
		for (ReceiverModel recModel : toRec) {
			Optional<UserModel> usr = userRepository.findById(recModel.getIdReceiver());
			emails.add(usr.isPresent() ? usr.get().getMail() : "Unknown User");
		}
		return emails;
	}

	@Override
	public Page<MessageDTO> getAllReceivedMessages(String userMail, Pageable pageable) {
		UserModel idUser = userRepository.findByMail(userMail);
		List<ReceiverDTO> receivedMsgs = receiverService.getReceivedMails(idUser.getId());
		List<MessageDTO> aux = new ArrayList<MessageDTO>();
		for (ReceiverDTO msgModel : receivedMsgs) {
			Optional<MessageModel> findById = msgRepository.findById(msgModel.getIdMail());
			aux.add(modelMapper.map(findById.get(), MessageDTO.class));
		}
		Page<MessageDTO> response = new PageImpl<MessageDTO>(aux, pageable, receivedMsgs.size());
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
		
		List<ReceiverDTO> aa = new ArrayList<>();
		
		for (String add : req.getToAddress()) {
			
			ReceiverDTO ee = new ReceiverDTO();
			ee.setIdMail(newMsg.getId());
			UserModel usr = userRepository.findByMail(add);
			
			ee.setIdReceiver(usr.getId());
			
			ee.setType(0);
			
			aa.add(ee);
		}
		receiverService.setMultiplesReceivers(aa);
		
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
		UserModel idUser = userRepository.findByMail(toAddress);
		List<ReceiverDTO> receivedMsgs = receiverService.getReceivedMails(idUser.getId());
		List<LabeledMailDTO> aux = new ArrayList<LabeledMailDTO>();
		for (ReceiverDTO msgModel : receivedMsgs) {
			Page<LabeledMailModel> reportList = labeledMsgRepository.findByIdMessageAndIdFolder(msgModel.getIdMail(), label, pageable);
			for (LabeledMailModel l : reportList) {
				aux.add(modelMapper.map(l, LabeledMailDTO.class));

			}
		}
		
		Page<LabeledMailDTO> response = new PageImpl<LabeledMailDTO>(aux, pageable, aux.size());
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
