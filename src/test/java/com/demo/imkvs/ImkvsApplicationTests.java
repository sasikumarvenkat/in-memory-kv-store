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

import com.demo.imkvs.constants.ServiceConstants;
import com.demo.imkvs.models.PutRequest;
import com.google.gson.Gson;

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
		LOGGER.traceEntry();
		String namespace = "ns2";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/imkvs/" + namespace)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
		LOGGER.traceExit();
	}

	@Test
	public void deleteNameSpaceFailureForNoNameSpaceExists() throws Exception {
		LOGGER.traceEntry();
		String namespace = "ns0";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/imkvs/" + namespace)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		LOGGER.traceExit();
	}

	@Test
	public void addIntValueInKVStore() throws Exception {
		LOGGER.traceEntry();
		String namespace = "ns1";
		String key = "intKey";
		int value = 12;
		Gson gson = new Gson();
		PutRequest putRequest = PutRequest.builder().key(key).value(value).build();
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/imkvs/" + namespace + "/put")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(putRequest));
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
		LOGGER.traceExit();
	}

	@Test
	public void addStringValueInKVStore() throws Exception {
		LOGGER.traceEntry();
		String namespace = "ns1";
		String key = "StrKey";
		String value = "Sample String";
		Gson gson = new Gson();
		PutRequest putRequest = PutRequest.builder().key(key).value(value).build();
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/imkvs/" + namespace + "/put")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(putRequest));
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
		LOGGER.traceExit();
	}

	@Test
	public void addDoubleValueInKVStore() throws Exception {
		LOGGER.traceEntry();
		String namespace = "ns1";
		String key = "doubleKey";
		Double value = 12.34;
		Gson gson = new Gson();
		PutRequest putRequest = PutRequest.builder().key(key).value(value).build();
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/imkvs/" + namespace + "/put")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(putRequest));
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
		LOGGER.traceExit();
	}

	@Test
	public void addBooleanValueInKVStore() throws Exception {
		LOGGER.traceEntry();
		String namespace = "ns1";
		String key = "boolKey";
		boolean value = true;
		Gson gson = new Gson();
		PutRequest putRequest = PutRequest.builder().key(key).value(value).build();
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/imkvs/" + namespace + "/put")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(putRequest));
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
		LOGGER.traceExit();
	}

	@Test
	public void addJsonValueInKVStore() throws Exception {
		LOGGER.traceEntry();
		String namespace = "ns1";
		String key = "jsonKey";
		String json = "{\"name\": \"Sasi\", \"age\": 27} ";
		Gson gson = new Gson();
		Object value = gson.fromJson(json, Object.class);
		PutRequest putRequest = PutRequest.builder().key(key).value(value).build();
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/imkvs/" + namespace + "/put")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(putRequest));
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
		LOGGER.traceExit();
	}

	@Test
	public void addArrayJsonValueInKVStore() throws Exception {
		LOGGER.traceEntry();
		String namespace = "ns1";
		String key = "jsonArrKey";
		String json = "[ {\"name\": \"Sasi\", \"age\": 27}, {\"name\": \"Arun\", \"age\": 27} ]";
		Gson gson = new Gson();
		Object value = gson.fromJson(json, Object.class);
		PutRequest putRequest = PutRequest.builder().key(key).value(value).build();
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/imkvs/" + namespace + "/put")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(putRequest));
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
		LOGGER.traceExit();
	}

	@Test
	public void getValueForKeyInTheGivenNamespace() throws Exception {
		LOGGER.traceEntry();
		String namespace = "ns1";
		String key = "k1";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/imkvs/" + namespace + "/get")
				.param(ServiceConstants.KEY, key).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
		LOGGER.traceExit();
	}

	@Test
	public void getValueForKeyInTheGivenNamespaceError() throws Exception {
		LOGGER.traceEntry();
		String namespace = "ns0";
		String key = "k1";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/imkvs/" + namespace + "/get")
				.param(ServiceConstants.KEY, key).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		LOGGER.traceExit();
	}

	@Test
	public void getValuesForTheGivenNamespaceError() throws Exception {
		LOGGER.traceEntry();
		String namespace = "ns0";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/imkvs/" + namespace + "/values")
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		LOGGER.traceExit();
	}

	@Test
	public void getValuesForTheGivenNamespaceSuccess() throws Exception {
		LOGGER.traceEntry();
		String namespace = "ns1";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/imkvs/" + namespace + "/values")
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
		LOGGER.traceExit();
	}

	@Test
	public void deleteKeyInTheNamespaceSuccess() throws Exception {
		LOGGER.traceEntry();
		String namespace = "ns1";
		String key = "k1";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/imkvs/" + namespace + "/delete")
				.param(ServiceConstants.KEY, key).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());

		LOGGER.traceExit();
	}

	@Test
	public void deleteKeyInTheNamespaceError() throws Exception {
		LOGGER.traceEntry();
		String namespace = "ns0";
		String key = "k1";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/imkvs/" + namespace + "/delete")
				.param(ServiceConstants.KEY, key).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		LOGGER.traceExit();
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
