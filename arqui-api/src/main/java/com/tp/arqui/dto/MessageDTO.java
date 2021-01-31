package com.tp.arqui.dto;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
public class MessageDTO {
	
	private Integer id;

	private String toAddress;
	
	private String fromAddress;

	private String subject;

	private String message;

	private String attached;

	private Double sendDate;
	
	private Boolean read;
	
}
