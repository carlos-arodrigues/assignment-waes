package com.waes.rodrigues.carlos.assignment.domain.model.diff;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import com.waes.rodrigues.carlos.assignment.bundle.MessageBundle;
/**
 * 
 * To be used on conversions to/from JSON.
 * 
 * @author carlos
 *
 */
public class ValueVO implements Serializable{
	
	private static final long serialVersionUID = -2018609678750273042L;
	private String name;
	private Integer age;
	
	public void validate(){
		Validate.validState(StringUtils.isNoneBlank(name) && age != null, MessageBundle.getInstance().getString("error.msg.invalid.json"));
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
