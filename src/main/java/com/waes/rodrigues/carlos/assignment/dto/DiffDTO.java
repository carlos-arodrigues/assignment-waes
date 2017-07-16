package com.waes.rodrigues.carlos.assignment.dto;

import com.waes.rodrigues.carlos.assignment.domain.model.diff.IDiff;

public class DiffDTO implements IDiff{
	
	private static final long serialVersionUID = 1929601349732125989L;
	
	private Long id;
	private String left;
	private String right;
	
	public DiffDTO(){}
	
	public DiffDTO(Long id, String left, String right){
		this.id = id;
		this.left = left;
		this.right = right;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public String getLeft() {
		return this.left;
	}

	@Override
	public String getRight() {
		return this.right;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLeft(String left) {
		this.left = left;
	}

	public void setRight(String right) {
		this.right = right;
	}

}
