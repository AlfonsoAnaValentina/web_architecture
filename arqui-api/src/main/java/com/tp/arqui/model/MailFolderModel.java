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
@Table(name = "mail_folder")
public class MailFolderModel {
	@Id
	@Column(name = "id_mail_folder")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "id_mail")
	private Integer idMessage;
	
	@Column(name = "id_folder")
	private Integer idFolder;
}
