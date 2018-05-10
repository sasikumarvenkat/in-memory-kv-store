package com.demo.imkvs.api;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.imkvs.constants.ServiceConstants;
import com.demo.imkvs.delegate.ImkvsServiceDelegate;
import com.demo.imkvs.models.PutRequest;
import com.google.gson.Gson;

/**
 * In-Memory Key-Value Store Rest Controller Classes and API Methods
 * 
 * @author Sasikumar Venkatesh
 *
 */
@RequestMapping("/imkvs")
@RestController
public class ImkvsRestController {

	@Autowired
	ImkvsServiceDelegate serviceDelegate;

	private static final Logger LOGGER = LogManager.getFormatterLogger();

	/**
	 * Adds a value to the the key given into the namespace
	 * 
	 * @param namespace
	 * @param putRequest
	 * @return {@link ResponseEntity}
	 */
	@PostMapping(path = "/{namespace}/put", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> put(@PathVariable(ServiceConstants.NAMESPACE) String namespace,
			@RequestBody PutRequest putRequest) {
		LOGGER.entry(namespace, putRequest);
		serviceDelegate.put(namespace, putRequest.getKey(), putRequest.getValue());
		ResponseEntity<String> response = ResponseEntity.ok(ServiceConstants.SUCCESS);
		return LOGGER.traceExit(response);
	}

	/**
	 * Returns the value of the given key and namespace
	 * 
	 * @param key
	 * @param namespace
	 * @return {@link ResponseEntity}
	 */
	@GetMapping(path = "/{namespace}/get", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> get(@PathVariable(ServiceConstants.NAMESPACE) String namespace,
			@RequestParam(ServiceConstants.KEY) String key) {
		LOGGER.traceEntry(namespace, key);
		Object value = serviceDelegate.get(namespace, key);
		Gson gson = new Gson();
		ResponseEntity<String> response = ResponseEntity.ok(gson.toJson(value));
		return LOGGER.traceExit(response);
	}

	/**
	 * Deletes the given key in the namespace
	 * 
	 * @param namespace
	 * @param key
	 * @return {@link ResponseEntity}
	 */
	@DeleteMapping(path = "/{namespace}/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> delete(@PathVariable(ServiceConstants.NAMESPACE) String namespace,
			@RequestParam(ServiceConstants.KEY) String key) {
		LOGGER.entry(namespace, key);
		boolean isDeleted = serviceDelegate.delete(namespace, key);
		ResponseEntity<String> response = ResponseEntity
				.ok(isDeleted ? ServiceConstants.SUCCESS : ServiceConstants.FAILURE);
		return LOGGER.traceExit(response);
	}

	/**
	 * Returns all the values of KV Store
	 * 
	 * @param namespace
	 * @return {@link ResponseEntity}
	 */
	@GetMapping(path = "/{namespace}/values", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> values(@PathVariable(ServiceConstants.NAMESPACE) String namespace) {
		LOGGER.entry(namespace);
		Map<String, Object> kvStoreMap = serviceDelegate.values(namespace);
		ResponseEntity<Map<String, Object>> response = ResponseEntity.ok(kvStoreMap);
		return LOGGER.traceExit(response);
	}

}
