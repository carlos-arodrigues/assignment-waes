package com.waes.rodrigues.carlos.assignment.domain.model.diff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class,
		DiffException.class })
public class DiffService {
	
	@Autowired
	private DiffRepository repository;
	
	@Transactional(readOnly = false)
	public Diff saveLeft(Long id, String left) throws DiffException{
		Diff diff = repository.findOne(id);
		diff.updateLeft(left);
		return repository.save(diff);
	}
	
	@Transactional(readOnly = false)
	public Diff saveRight(Long id, String right) throws DiffException{
		Diff diff = repository.findOne(id);
		diff.updateRight(right);
		return repository.save(diff);
	}	

}
