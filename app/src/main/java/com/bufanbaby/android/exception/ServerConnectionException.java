package com.bufanbaby.android.exception;

/**
 * Connection to Server exception
 */
public class ServerConnectionException extends ServerResponseException {
	public ServerConnectionException() {
	}

	public ServerConnectionException(String detailMessage) {
		super(detailMessage);
	}

	public ServerConnectionException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public ServerConnectionException(Throwable throwable) {
		super(throwable);
	}
}
