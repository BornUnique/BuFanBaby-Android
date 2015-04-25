package com.bufanbaby.android.model;

import java.util.Date;
import java.util.List;

/**
 * A Post to Server
 */
public class Deed extends AndroidModel {
	private String content;
	private List<String> tags;
	private Date createDate;
	private String imageId;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
}
