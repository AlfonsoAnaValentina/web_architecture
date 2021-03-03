package com.tp.arqui.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "message")
public class MessageModel {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Integer id;
	
	@Column(name = "from_address")
	private String fromAddress;

	@Column(name = "email_subject")
	private String subject;

	private String message;

	private String attached;
	
	@Column(name = "send_date")
	private Long sendDate;
	
	@Column(name = "was_read")
	private Boolean read;


}