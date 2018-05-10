package com.demo.imkvs.delegate;

import java.text.MessageFormat;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.imkvs.constants.BundleConstants;
import com.demo.imkvs.constants.BusinessErrorCode;
import com.demo.imkvs.models.InMemoryKeyValueStore;
import com.demo.imkvs.store.exception.BusinessException;
import com.demo.imkvs.store.repository.impl.ImkvsRepositoryImpl;

/**
 * Contains the delegated methods for performing the business logic/ core logic
 * of imkvs
 * 
 * @author Sasikumar Venaktesh
 *
 */
@Component
public class ImkvsServiceDelegate {

	public static final Logger LOGGER = LogManager.getLogger(ImkvsServiceDelegate.class);
	/**
	 * BUNDLE- Field used to get constant messages from the ImvsService Bundle
	 */
	public static final ResourceBundle BUNDLE = ResourceBundle.getBundle(BundleConstants.IMKVS_SERVICE);

	@Autowired
	ImkvsRepositoryImpl imkvsRepository;

	/**
	 * Returns the value for the key and given the namespace
	 * 
	 * @param namespace
	 * @param key
	 * @return {@link Object}
	 */
	public Object get(String namespace, String key) {
		LOGGER.entry(namespace, key);
		InMemoryKeyValueStore kvStore = getKVStoreFromImkvs(namespace);
		Object value = kvStore.get(key);
		return LOGGER.traceExit(value);
	}

	/**
	 * Adds a new key in the given namespace
	 * 
	 * @param namespace
	 * @param key
	 * @param value
	 * @return {@link Boolean}
	 */
	public boolean put(String namespace, String key, Object value) {
		LOGGER.entry(namespace, key, value);
		InMemoryKeyValueStore kvStore = getKVStoreFromImkvs(namespace);
		boolean isAdded = kvStore.put(key, value);
		return LOGGER.traceExit(isAdded);
	}

	/**
	 * Removes the key in the namespace
	 * 
	 * @param namespace
	 * @param key
	 * @return {@link Boolean}
	 */
	public boolean delete(String namespace, String key) {
		LOGGER.entry(namespace, key);
		InMemoryKeyValueStore kvStore = getKVStoreFromImkvs(namespace);
		boolean isAdded = kvStore.delete(key);
		return LOGGER.traceExit(isAdded);
	}

	/**
	 * Returns all the values of a given namespace
	 * 
	 * @param namespace
	 * @return {@link Map}
	 */
	public Map<String, Object> values(String namespace) {
		LOGGER.entry(namespace);
		InMemoryKeyValueStore kvStore = getKVStoreFromImkvs(namespace);
		return LOGGER.traceExit(kvStore.getMap());
	}

	/**
	 * Creates a new namespace in the imkvs
	 * 
	 * @param namespace
	 * @return {@link Boolean}
	 */
	public boolean createNamespace(String namespace) {
		LOGGER.entry(namespace);
		if (null == imkvsRepository.getKVStore(namespace)) {
			imkvsRepository.getOrCreateKVStore(namespace);
		} else {
			String errorMsg = MessageFormat.format(BUNDLE.getString("IMKVS3002E"), namespace);
			LOGGER.error(errorMsg);
			throw new BusinessException(BusinessErrorCode.NAME_SPACE_ALREADY_EXISTS, errorMsg);
		}
		return LOGGER.traceExit(true);
	}

	/**
	 * Method to remove a namespace from kvstore
	 * 
	 * @param namespace
	 * @return {@link Boolean}l
	 */
	public boolean deleteNamespace(String namespace) {
		LOGGER.entry(namespace);
		if (null != imkvsRepository.getKVStore(namespace)) {
			return LOGGER.traceExit(imkvsRepository.deleteNamespace(namespace));
		} else {
			String errorMsg = MessageFormat.format(BUNDLE.getString("IMKVS3001E"), namespace);
			LOGGER.error(errorMsg);
			throw new BusinessException(BusinessErrorCode.NO_NAME_SPACE_EXISTS, errorMsg);
		}
	}

	/**
	 * Method to check and returns the KVStore for a given namespace
	 * 
	 * @param namespace
	 * @return {@link InMemoryKeyValueStore}
	 */
	private InMemoryKeyValueStore getKVStoreFromImkvs(String namespace) {
		InMemoryKeyValueStore kvStore = imkvsRepository.getKVStore(namespace);
		if (null == kvStore) {
			String errorMsg = MessageFormat.format(BUNDLE.getString("IMKVS3001E"), namespace);
			LOGGER.error(errorMsg);
			throw new BusinessException(BusinessErrorCode.NO_NAME_SPACE_EXISTS, errorMsg);
		}
		return kvStore;
	}

}
