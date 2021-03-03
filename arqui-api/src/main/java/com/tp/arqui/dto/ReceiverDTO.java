package com.tp.arqui.dto;

import lombok.Data;

@Data

public class ReceiverDTO {

	public static final int CCO = 2;
	public static final int CC = 1;
	public static final int TO = 0;

	
	private Integer idMail;
	
	private Integer idReceiver;

	private Integer type;
}
