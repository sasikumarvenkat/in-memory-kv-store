package com.demo.imkvs.models;

import java.util.Map;

import lombok.Data;

/**
 * Model Object for In-Memory KV Store
 * 
 * @author Sasikumar Venkatesh
 *
 */
@Data
public class InMemoryKeyValueStore {

	private String namespace;
	private Map<String, Object> map;

	/**
	 * Returns the Serialized Object for the given key
	 * 
	 * @param key
	 * @return {@link Object}
	 */
	public Object get(String key) {
		return map.get(key);
	}

	/**
	 * Adds the new value for a key in a given namespace
	 * 
	 * @param key
	 * @param value
	 * @return {@link Boolean}
	 */
	public boolean put(String key, Object value) {
		map.put(key, value);
		return true;
	}

	/**
	 * Removes the given key from KVStore
	 * 
	 * @param key
	 * @return {@link Boolean}
	 */
	public boolean delete(String key) {
		map.remove(key);
		return true;
	}
}
