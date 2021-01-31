package com.tp.arqui.dto;

import javax.persistence.Column;

import lombok.Data;

@Data
public class LabeledMailDTO {
	private Integer id;
	
	private Integer idMessage;
	
	private Integer idFolder;

	private String toAddress;
	
	private String fromAddress;

	private String subject;

	private String messageBody;

	private String attached;
	
	private Long sendDate;
	
	private Boolean read;
	
	private String folderLabel;
}
