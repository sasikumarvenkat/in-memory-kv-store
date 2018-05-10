package com.demo.imkvs.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.imkvs.constants.ServiceConstants;
import com.demo.imkvs.delegate.ImkvsServiceDelegate;

@RequestMapping("/imkvs")
@RestController
public class NamespaceRestController {

	private static final Logger LOGGER = LogManager.getLogger(NamespaceRestController.class);

	@Autowired
	ImkvsServiceDelegate serviceDelegate;

	/**
	 * Method to add a new namespace to kvStore
	 * 
	 * @param namespace
	 * @return {@link ResponseEntity}
	 */
	@PostMapping(path = "/{namespace}")
	public ResponseEntity<String> createNamespace(@PathVariable(ServiceConstants.NAMESPACE) String namespace) {
		LOGGER.entry(namespace);
		boolean isCreated = serviceDelegate.createNamespace(namespace);
		ResponseEntity<String> response = ResponseEntity
				.ok(isCreated ? ServiceConstants.SUCCESS : ServiceConstants.FAILURE);
		return LOGGER.traceExit(response);
	}

	/**
	 * Method to delete namespace from imkvs
	 * 
	 * @param namespace
	 * @return {@link ResponseEntity}
	 */
	@DeleteMapping(path = "/{namespace}")
	public ResponseEntity<String> deleteNamespace(@PathVariable(ServiceConstants.NAMESPACE) String namespace) {
		LOGGER.entry(namespace);
		boolean isDeleted = serviceDelegate.deleteNamespace(namespace);
		ResponseEntity<String> response = ResponseEntity
				.ok(isDeleted ? ServiceConstants.SUCCESS : ServiceConstants.FAILURE);
		return LOGGER.traceExit(response);
	}
}
