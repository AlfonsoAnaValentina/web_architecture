package com.tp.arqui.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "labeled_mail")
public class LabeledMailModel {
	@Id
	@Column(name = "id_mail_folder")
	private Integer id;
	
	@Column(name = "id_message")
	private Integer idMessage;
	
	@Column(name = "id_folder")
	private Integer idFolder;
	
	@Column(name = "id_user")
	private Integer idUser;
	
	@Column(name = "from_address")
	private String fromAddress;

	@Column(name = "email_subject")
	private String subject;

	@Column(name = "message_body")
	private String messageBody;

	private String attached;
	
	@Column(name = "send_date")
	private Long sendDate;
	
	@Column(name = "was_read")
	private Boolean read;
	
	@Column(name = "folder_label")
	private String folderLabel;
	
}
