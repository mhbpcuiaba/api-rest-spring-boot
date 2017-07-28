package br.com.mhbp.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.com.mhbp.util.DiffSessionService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = DiffController.class, secure = false)
public class DiffControllerUnitTest {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Autowired
	private MockMvc mockMvc;

	@SuppressWarnings("rawtypes")
	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@MockBean
	private DiffSessionService diffSessionService;

	
	 @Autowired
	    void setConverters(HttpMessageConverter<?>[] converters) {

	        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
	            .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
	            .findAny()
	            .orElse(null);

	        Assert.assertNotNull("the JSON message converter must not be null",
	                this.mappingJackson2HttpMessageConverter);
	    }
	
	private String leftOrRightEndpointSuccessExpected = "{\"data\":null,\"status\":200}";

	@Test
	public void requestLeftSuccess() throws Exception {

		String url = "/v1/diff/213/left";
		Data dataLeft = new Data("binarydata");
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url)
				                        .content(this.json(dataLeft))
				                        .contentType(contentType)
				                        .accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
		Assert.assertEquals(leftOrRightEndpointSuccessExpected, result.getResponse().getContentAsString());
	}

	@Test
	public void requestRightSuccess() throws Exception {

		String url = "/v1/diff/213/right";
		Data dataRight = new Data("binarydata");
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url)
										.content(this.json(dataRight))
						                .contentType(contentType)
				                        .accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
		Assert.assertEquals(leftOrRightEndpointSuccessExpected, result.getResponse().getContentAsString());
	}

	
	@Test
	public void requestDiffInternalServerError() throws Exception {

		String url = "/v1/diff/213";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url)
				                        .accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
	}

	
	@SuppressWarnings("unchecked")
	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}
}
