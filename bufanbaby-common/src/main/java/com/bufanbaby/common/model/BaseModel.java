package com.bufanbaby.common.model;

import java.io.Serializable;

/**
 * Ensure all the model are serializable for IPC related communication
 */
abstract public class BaseModel<T> implements Serializable {
	private T id;

	public T getId() {
		return id;
	}

	public void setId(T id) {
		this.id = id;
	}
}
