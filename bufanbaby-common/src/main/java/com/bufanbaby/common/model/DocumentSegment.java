package com.bufanbaby.common.model;

import java.io.Serializable;

/**
 * A reference to any part of the document, it is used to store geographic info
 * such as a region on a image, or a start time to end time of audio/video
 */
public class DocumentSegment implements Serializable {
	public static final double NONE = -1;
	private final double startX;
	private final double startY;
	private final double endX;
	private final double endY;

	public DocumentSegment(double startX, double endX) {
		this(startX, endX, NONE, NONE);
	}

	public DocumentSegment(double startX, double startY, double endX, double endY) {
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
	}

	public double getStartX() {
		return startX;
	}

	public double getStartY() {
		return startY;
	}

	public double getEndX() {
		return endX;
	}

	public double getEndY() {
		return endY;
	}
}
