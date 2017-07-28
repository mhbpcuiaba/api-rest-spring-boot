package br.com.mhbp.integration;

import static org.hamcrest.Matchers.contains;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.mhbp.common.ApiResponse;
import br.com.mhbp.controller.Data;
import br.com.mhbp.enums.DiffResultEnum;
import br.com.mhbp.util.Base64Util;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RestApiIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@SuppressWarnings("rawtypes")
	@Test
	public void shouldGetResultDiffEqual() {
		HttpEntity<Data> httpEntityLeft = new HttpEntity<Data>(new Data(Base64Util.encodeString("abce")), new HttpHeaders());
		HttpEntity<Data> httpEntityRight = new HttpEntity<Data>(new Data(Base64Util.encodeString("abce")), new HttpHeaders());
		HttpEntity httpEntityDiff = new HttpEntity<String>(new HttpHeaders());
		
		ResponseEntity<ApiResponse> responseLeft = this.restTemplate.postForEntity("/v1/diff/{id}/left", httpEntityLeft, ApiResponse.class, "mhbp");
		
		Assert.assertEquals(HttpStatus.OK, responseLeft.getStatusCode());
		
		ResponseEntity<ApiResponse> responseRight = this.restTemplate.postForEntity("/v1/diff/{id}/right", httpEntityRight,	ApiResponse.class, "mhbp");
		Assert.assertEquals(HttpStatus.OK, responseRight.getStatusCode());
		
		ResponseEntity<ApiResponse> responseDiff = this.restTemplate.postForEntity("/v1/diff/{id}", httpEntityDiff,	ApiResponse.class, "mhbp");
		Assert.assertEquals(HttpStatus.OK, responseDiff.getStatusCode());
		
		ApiResponse body = responseDiff.getBody();
		Map m = (LinkedHashMap) body.getData();
		Assert.assertEquals(DiffResultEnum.EQUAL.value(), m.get("resultDiff"));
		Assert.assertNull(m.get("positionWithDifferences"));
		
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void shouldGetResultDiffNotEqualSize() {
		HttpEntity<Data> httpEntityLeft = new HttpEntity<Data>(new Data(Base64Util.encodeString("abce3")), new HttpHeaders());
		HttpEntity<Data> httpEntityRight = new HttpEntity<Data>(new Data(Base64Util.encodeString("abce")), new HttpHeaders());
		HttpEntity httpEntityDiff = new HttpEntity<String>(new HttpHeaders());
		
		ResponseEntity<ApiResponse> responseLeft = this.restTemplate.postForEntity("/v1/diff/{id}/left", httpEntityLeft, ApiResponse.class, "mhbp");
		
		Assert.assertEquals(HttpStatus.OK, responseLeft.getStatusCode());
		
		ResponseEntity<ApiResponse> responseRight = this.restTemplate.postForEntity("/v1/diff/{id}/right", httpEntityRight,	ApiResponse.class, "mhbp");
		Assert.assertEquals(HttpStatus.OK, responseRight.getStatusCode());
		
		ResponseEntity<ApiResponse> responseDiff = this.restTemplate.postForEntity("/v1/diff/{id}", httpEntityDiff,	ApiResponse.class, "mhbp");
		Assert.assertEquals(HttpStatus.OK, responseDiff.getStatusCode());
		
		ApiResponse body = responseDiff.getBody();
		Map m = (LinkedHashMap) body.getData();
		Assert.assertEquals(DiffResultEnum.NOT_EQUAL_SIZE.value(), m.get("resultDiff"));
		Assert.assertNull(m.get("positionWithDifferences"));
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void shouldGetResultDiffDifferences() {
		HttpEntity<Data> httpEntityLeft = new HttpEntity<Data>(new Data(Base64Util.encodeString("abcd")), new HttpHeaders());
		HttpEntity<Data> httpEntityRight = new HttpEntity<Data>(new Data(Base64Util.encodeString("abce")), new HttpHeaders());
		HttpEntity httpEntityDiff = new HttpEntity<String>(new HttpHeaders());
		
		ResponseEntity<ApiResponse> responseLeft = this.restTemplate.postForEntity("/v1/diff/{id}/left", httpEntityLeft, ApiResponse.class, "mhbp");
		
		Assert.assertEquals(HttpStatus.OK, responseLeft.getStatusCode());
		
		ResponseEntity<ApiResponse> responseRight = this.restTemplate.postForEntity("/v1/diff/{id}/right", httpEntityRight,	ApiResponse.class, "mhbp");
		Assert.assertEquals(HttpStatus.OK, responseRight.getStatusCode());
		
		ResponseEntity<ApiResponse> responseDiff = this.restTemplate.postForEntity("/v1/diff/{id}", httpEntityDiff,	ApiResponse.class, "mhbp");
		Assert.assertEquals(HttpStatus.OK, responseDiff.getStatusCode());
		
		ApiResponse body = responseDiff.getBody();
		Map m = (LinkedHashMap) body.getData();
		Assert.assertEquals(DiffResultEnum.DIFFERENCES.value(), m.get("resultDiff"));
		MatcherAssert.assertThat( (List<Integer>) m.get("positionWithDifferences"), contains(3));
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void shouldFailWhenThereIsNoData() {
		HttpEntity httpEntityDiff = new HttpEntity<String>(new HttpHeaders());
		ResponseEntity responseDiff = this.restTemplate.postForEntity("/v1/diff/{id}", httpEntityDiff,	Object.class, "mhbpq");
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseDiff.getStatusCode());
	}

}
