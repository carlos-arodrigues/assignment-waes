package com.waes.rodrigues.carlos.assignment.controller;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
/*
 * Provide 2 http endpoints that accepts JSON base64 encoded binary data on both
endpoints
o <host>/v1/diff/<ID>/left and <host>/v1/diff/<ID>/right
The provided data needs to be diff-ed and the results shall be available on a third end
point
o <host>/v1/diff/<ID>
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.waes.rodrigues.carlos.assignment.domain.model.diff.Diff;
import com.waes.rodrigues.carlos.assignment.domain.model.diff.DiffException;
import com.waes.rodrigues.carlos.assignment.domain.model.diff.DiffRepository;
import com.waes.rodrigues.carlos.assignment.domain.model.diff.DiffService;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping(value = "/api/v1/diff/{id}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
public class DiffController {
	
	@Autowired
	private DiffService service;
	
	@Autowired
	private DiffRepository repository;
	
	@RequestMapping(value="/left", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Updating the JSON to left side")
	public void left(@PathVariable("id") Long id, @RequestBody byte[] leftEncoded) throws UnsupportedEncodingException, DiffException{
		String left = new String(Base64.getDecoder().decode(leftEncoded), "utf-8");
		service.saveLeft(id, left);
	}
	
	@RequestMapping(value="/right", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Updating the JSON to right side")
	public void right(@PathVariable("id") Long id, @RequestBody byte[] rightEncoded) throws UnsupportedEncodingException, DiffException{
		String right = new String(Base64.getDecoder().decode(rightEncoded), "utf-8");
		service.saveRight(id, right);
	}	
	
	@RequestMapping
	@ApiOperation(value = "Getting insights from json's compare")
	public ResponseEntity<String> get(@PathVariable("id") Long id) throws DiffException{
		final Diff diff = repository.findOne(id);
		if (diff == null){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(diff.getInsight(), HttpStatus.OK);
	}		
	
	

}
