package com.bufanbaby.android.exception;

/**
 * Exception for handling Server response in another way
 */
public class ServerResponseException extends Exception {
	private int httpErrorCode;
	private String httpErrorDetail;

	public ServerResponseException() {
	}

	public ServerResponseException(String detailMessage) {
		super(detailMessage);
	}

	public ServerResponseException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public ServerResponseException(Throwable throwable) {
		super(throwable);
	}

	public int getHttpErrorCode() {
		return httpErrorCode;
	}

	public void setHttpErrorCode(int httpErrorCode) {
		this.httpErrorCode = httpErrorCode;
	}

	public String getHttpErrorDetail() {
		return httpErrorDetail;
	}

	public void setHttpErrorDetail(String httpErrorDetail) {
		this.httpErrorDetail = httpErrorDetail;
	}
}
