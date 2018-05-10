package com.demo.imkvs.store.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.demo.imkvs.models.InMemoryKeyValueStore;
import com.demo.imkvs.store.repository.ImkvsRepository;

/**
 * Contains the implementation methods of In-Memory KV Store Repository
 * 
 * @author Sasikumar Venkatesh
 *
 */
@Component
public class ImkvsRepositoryImpl implements ImkvsRepository {

	private static final Logger LOGGER = LogManager.getLogger(ImkvsRepositoryImpl.class);

	private static List<InMemoryKeyValueStore> imkvStore = new ArrayList<>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.demo.imkvs.store.repository.ImkvsRepository#getOrCreateKVStore(java.lang.
	 * String)
	 */
	@Override
	public InMemoryKeyValueStore getOrCreateKVStore(String namespace) {
		LOGGER.entry(namespace);
		// Check whether the namespace is exists or not

		InMemoryKeyValueStore kvStore = getKVStore(namespace);
		if (null == kvStore) {
			// Initialize new name space
			kvStore = new InMemoryKeyValueStore();
			kvStore.setNamespace(namespace);
			kvStore.setMap(new HashMap<>());
			imkvStore.add(kvStore);
		}
		return kvStore;
	}

	/**
	 * Method to get the namespace from the store
	 * 
	 * @param namespace
	 * @return {@link InMemoryKeyValueStore}
	 */
	public InMemoryKeyValueStore getKVStore(String namespace) {
		LOGGER.entry(namespace);
		// Linear Search can be changed to binary search
		for (InMemoryKeyValueStore kvStore : imkvStore) {
			if (kvStore.getNamespace().equalsIgnoreCase(namespace)) {
				LOGGER.traceExit();
				return kvStore;
			}
		}
		LOGGER.traceExit();
		return null;
	}

	/**
	 * Removes a namespace from a KVStore
	 * 
	 * @param namespace
	 * @return {@link Boolean}
	 */
	public boolean deleteNamespace(String namespace) {
		LOGGER.entry(namespace);
		InMemoryKeyValueStore kvStore = getKVStore(namespace);
		boolean isRemoved = imkvStore.remove(kvStore);
		return LOGGER.traceExit(isRemoved);
	}

}
