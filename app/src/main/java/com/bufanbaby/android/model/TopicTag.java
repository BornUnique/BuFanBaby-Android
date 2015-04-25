package com.bufanbaby.android.model;

/**
 * Tag
 */
public class TopicTag extends AndroidModel {
	private String name;
	private boolean isNew;
	private boolean isSelected;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
}
