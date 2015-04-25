package com.bufanbaby.android.exception;

/**
 * Data Conversion Exception
 */
public class DataConversionException extends Exception {
	private Object source;

	public DataConversionException() {
	}


	public DataConversionException(String detailMessage) {
		super(detailMessage);
	}

	public DataConversionException(Throwable throwable) {
		super(throwable);
	}

	public DataConversionException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public Object getSource() {
		return source;
	}

	public void setSource(Object source) {
		this.source = source;
	}
}
