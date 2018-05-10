package com.demo.imkvs.store.repository;

import com.demo.imkvs.models.InMemoryKeyValueStore;

/**
 * Imkvs Related repository methods
 * 
 * @author Sasikumar Venkatesh
 *
 */
public interface ImkvsRepository {

	/**
	 * Method to get or create a KVStore
	 * 
	 * @param namespace
	 * @return {@link InMemoryKeyValueStore}
	 */
	InMemoryKeyValueStore getOrCreateKVStore(String namespace);

}
