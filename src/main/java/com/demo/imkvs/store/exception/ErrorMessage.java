package com.demo.imkvs.store.exception;

import org.springframework.beans.BeanUtils;

public class ErrorMessage {
	int status;
	int code;
	String message;
	String link;
	String developerMessage;

	public ErrorMessage(BusinessException ex) {
		try {
			BeanUtils.copyProperties(this, ex);
		} catch (Exception e) {
			// TODO Handle this
			e.printStackTrace();
		}
	}

	public ErrorMessage() {
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDeveloperMessage() {
		return developerMessage;
	}

	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
