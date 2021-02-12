package com.tp.arqui.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.tp.arqui.dto.FolderDTO;
import com.tp.arqui.dto.LabeledMailDTO;
import com.tp.arqui.dto.MailFolderDTO;
import com.tp.arqui.dto.MessageDTO;

public interface IMessageService {

	MessageDTO createMessage(@Valid MessageDTO req);

	Page<MessageDTO> getAllMessages(Pageable pageable);

	Page<MessageDTO> getAllSentMessages(String userMail, Pageable pageable);

	Page<MessageDTO> getAllReceivedMessages(String userMail, Pageable pageable);

	MessageDTO getMessage(Integer id);
		
	MessageDTO updateMessage(Integer id, @Valid MessageDTO req);

	void deleteMessages(List<Integer> ids);

	MessageDTO addImage(MultipartFile file, Integer string) throws FileNotFoundException, IOException;

	Page<LabeledMailDTO> getAllLabeledMessages(String ToAddress, Integer label, Pageable pageable);

	List<FolderDTO> getAllLabels(int idUser);

	FolderDTO createLabel(@Valid FolderDTO req);

	MailFolderDTO addLabeledMail(@Valid MailFolderDTO req);

	void addMultipleLabeledMail(List<MailFolderDTO> mails);

}
