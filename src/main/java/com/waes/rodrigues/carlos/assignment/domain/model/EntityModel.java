package com.waes.rodrigues.carlos.assignment.domain.model;

import java.io.Serializable;

import javax.persistence.Transient;

import com.waes.rodrigues.carlos.assignment.bundle.MessageBundle;


public class EntityModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected EntityModel(){}
	
	@Transient
	@org.springframework.data.annotation.Transient
	protected transient MessageBundle messageBundle = MessageBundle.getInstance();

}
