package com.tp.arqui.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@IdClass(ReceiverId.class)
@Table(name = "mail_receivers")
public class ReceiverModel {
	@Id
	@Column(name = "id_mail")
	private Integer idMail;
	
	@Id
	@Column(name = "id_receiver")
	private Integer idReceiver;

	private Integer type;
}


@Entity
class ReceiverId implements Serializable {
    @Id
    private Integer idMail;
    @Id
    private Integer idReceiver;

}