package com.waes.rodrigues.carlos.assignment.client;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.waes.rodrigues.carlos.assignment.domain.model.diff.DiffException;

@Component
public class DiffServiceClient{
	
	@Autowired
	private RestTemplate restTemplate;
	
	public void updateLeftValue(final Long id, final String jsonLeft) throws DiffException {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		String jsonBase64;
		try {
			jsonBase64 = Base64.getEncoder().encodeToString(jsonLeft.getBytes("utf-8"));
			HttpEntity<?> requestEntity = new HttpEntity<Object>(jsonBase64, requestHeaders);
			ResponseEntity<byte[]> responseEntity = restTemplate.exchange("http://localhost:8080/waes/api/v1/diff/{id}/left/", HttpMethod.PUT, requestEntity, byte[].class, id);
			if(responseEntity.getStatusCodeValue() != HttpStatus.OK.value()){
				throw new DiffException(responseEntity.getBody().toString());
			}
		} catch (UnsupportedEncodingException e) {
			throw new DiffException();
		}
	}
	
	public void updateRightValue(final Long id, final String jsonRight) throws DiffException {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		String jsonBase64;
		try {
			jsonBase64 = Base64.getEncoder().encodeToString(jsonRight.getBytes("utf-8"));
			HttpEntity<?> requestEntity = new HttpEntity<Object>(jsonBase64, requestHeaders);
			ResponseEntity<byte[]> responseEntity = restTemplate.exchange("http://localhost:8080/waes/api/v1/diff/{id}/right/", HttpMethod.PUT, requestEntity, byte[].class, id);
			if(responseEntity.getStatusCodeValue() != HttpStatus.OK.value()){
				throw new DiffException(responseEntity.getBody().toString());
			}
		} catch (UnsupportedEncodingException e) {
			throw new DiffException();
		}
	}
	
	public String getInsghts(final Long id) throws DiffException{
		ResponseEntity<String> responseEntity = this.restTemplate.getForEntity("http://localhost:8080/waes/api/v1/diff/{id}", String.class, id);
		if(responseEntity.getStatusCodeValue() != HttpStatus.OK.value()){
			throw new DiffException(responseEntity.getBody().toString());
		}
		return responseEntity.getBody();
	}
}