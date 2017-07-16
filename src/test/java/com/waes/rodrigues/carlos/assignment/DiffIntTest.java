package com.waes.rodrigues.carlos.assignment;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Encoder;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.waes.rodrigues.carlos.assignment.client.DiffServiceClient;
import com.waes.rodrigues.carlos.assignment.domain.model.diff.DiffException;


@Sql(scripts = { "/script/diff.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = { "/script/shutdown.sql" }, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class DiffIntTest extends SpringTest {

	@Autowired
	private WebApplicationContext webApplicationContext;
	@Autowired
	private DiffServiceClient serviceClient;

	private MockMvc mockMvc;
	private Encoder enc;

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		enc = Base64.getEncoder();
	}

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {
		mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();
		Assert.assertNotNull("the JSON message converter must not be null", mappingJackson2HttpMessageConverter);
	}

	@Test
	@Rollback
	public void shouldUpdateLeft() throws UnsupportedEncodingException, JSONException {
		configureUrl("/api/v1/diff/{id}/left");
		String carlos = enc.encodeToString(createJSON("Carlos", 37).getBytes("utf-8"));
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		HttpEntity<?> requestEntity = new HttpEntity<Object>(carlos, requestHeaders);
		ResponseEntity<byte[]> responseEntity = this.restTemplate.exchange(url, HttpMethod.PUT, requestEntity, byte[].class, "1");
		Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	@Rollback
	public void shouldNotUpdateLeft() throws Exception {
		configureUrl("/api/v1/diff/{id}/left");
		String carlos = enc.encodeToString(createJSON("Carlos", null).toString().getBytes("utf-8"));
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		HttpEntity<?> requestEntity = new HttpEntity<Object>(carlos, requestHeaders);
		ResponseEntity<byte[]> responseEntity = this.restTemplate.exchange(url, HttpMethod.PUT, requestEntity, byte[].class, "1");
		Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}	

	@Test
	@Rollback
	public void shouldUpdateRight() throws UnsupportedEncodingException{
		configureUrl("/api/v1/diff/{id}/right");
		String silvana = enc.encodeToString(createJSON("Silvana", 39).getBytes("utf-8"));
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		HttpEntity<?> requestEntity = new HttpEntity<Object>(silvana, requestHeaders);
		ResponseEntity<byte[]> responseEntity = this.restTemplate.exchange(url, HttpMethod.PUT, requestEntity, byte[].class, "1");
		Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	@Rollback
	public void shouldNotUpdateRight() throws UnsupportedEncodingException {
		configureUrl("/api/v1/diff/{id}/right");
		String carlos = enc.encodeToString(createJSON("Carlos", null).toString().getBytes("utf-8"));
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		HttpEntity<?> requestEntity = new HttpEntity<Object>(carlos, requestHeaders);
		ResponseEntity<byte[]> responseEntity = this.restTemplate.exchange(url, HttpMethod.PUT, requestEntity, byte[].class, "1");
		Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}		
	
	@Test
	@Rollback
	public void shouldReturnInsight() throws UnsupportedEncodingException {
		configureUrl("/api/v1/diff/{id}");
		ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(url, String.class, "2");
		Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
		Assertions.assertThat(!responseEntity.getBody().isEmpty());
	}	
	
	@Test
	@Rollback
	public void shouldNotReturnInsight() throws UnsupportedEncodingException {
		configureUrl("/api/v1/diff/{id}");
		ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(url, String.class, "3");
		Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.NO_CONTENT.value());
	}	
	
	@Test
	@Rollback
	public void shouldReturnInsightFromClient() throws UnsupportedEncodingException, DiffException {
		String insight = serviceClient.getInsghts(2l);
		Assertions.assertThat(StringUtils.isNoneBlank(insight));
	}	
	
	@Test
	@Rollback
	public void shouldUpdateLeftFromClient() throws UnsupportedEncodingException, DiffException {
		serviceClient.updateLeftValue(1l, createJSON("Carlos", 37));
	}	
	
	@Test
	@Rollback
	public void shouldUpdateRightFromClient() throws UnsupportedEncodingException, DiffException {
		serviceClient.updateRightValue(1l, createJSON("Carlos", 37));
	}	

}
