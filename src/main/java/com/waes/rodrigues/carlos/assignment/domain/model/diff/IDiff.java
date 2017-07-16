package com.waes.rodrigues.carlos.assignment.domain.model.diff;

import java.io.Serializable;

public interface IDiff extends Serializable {
	
	public Long getId();

	public String getLeft();

	public String getRight();

}
