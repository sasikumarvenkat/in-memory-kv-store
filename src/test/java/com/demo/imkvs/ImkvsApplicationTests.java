package com.demo.imkvs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImkvsApplicationTests {

	private static final Logger LOGGER = LogManager.getLogger(ImkvsApplicationTests.class);

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() throws Exception {
		LOGGER.traceEntry();
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		String namespace = "ns1";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/imkvs/" + namespace)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());

		LOGGER.traceExit();
	}

	@Test
	public void addNameSpaceSuccess() throws Exception {
		LOGGER.traceEntry();
		String namespace = "ns2";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/imkvs/" + namespace)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
		LOGGER.traceExit();
	}

	@Test
	public void addNamespaceFailureForNameSpaceAlreadyExists() throws Exception {
		LOGGER.traceEntry();
		String namespace = "ns1";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/imkvs/" + namespace)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		LOGGER.traceExit();
	}

	@Test
	public void deleteNameSpaceSuccess() throws Exception {

		String namespace = "ns2";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/imkvs/" + namespace)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void deleteNameSpaceFailureForNoNameSpaceExists() throws Exception {
		String namespace = "ns0";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/imkvs/" + namespace)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}

	@After
	public void clearImkvs() throws Exception {
		LOGGER.traceEntry();

		String namespace = "ns1";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/imkvs/" + namespace)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());

		LOGGER.traceExit();
	}
}
