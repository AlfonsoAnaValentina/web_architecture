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
@Table(name = "states")
public class ProvinceModel {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Integer id;
	
	@Column(name = "country_id")
	private Integer countryId;
	
	@Column(name = "state")
	private String name;
}
