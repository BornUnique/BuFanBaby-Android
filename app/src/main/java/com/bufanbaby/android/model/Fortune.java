package com.bufanbaby.android.model;

import java.util.Date;

/**
 * File object for uploading tracking
 */
public class Fortune extends AndroidModel {
	private String localUri;
	private String remoteUri;
	private Date createDate;

	public String getLocalUri() {
		return localUri;
	}

	public void setLocalUri(String localUri) {
		this.localUri = localUri;
	}

	public String getRemoteUri() {
		return remoteUri;
	}

	public void setRemoteUri(String remoteUri) {
		this.remoteUri = remoteUri;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
