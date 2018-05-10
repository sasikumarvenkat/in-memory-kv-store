package com.demo.imkvs.models;

import lombok.Data;

@Data
public class PutRequest {

	private String key;
	private Object value;
}
