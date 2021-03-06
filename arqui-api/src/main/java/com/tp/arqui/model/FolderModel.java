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
@Table(name = "folder")
public class FolderModel {
	
	@Id
	@Column(name = "id_folder")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String label;
	
	@Column(name = "id_user")
	private Integer idUser;
}
