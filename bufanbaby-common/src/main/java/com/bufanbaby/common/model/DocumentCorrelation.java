package com.bufanbaby.common.model;

/**
 * Document relations to any users. The document relation is used to track
 * how the document is used for. Usually a document, or part of, is created
 * from a post. The document is a subject of the post.
 */
public class DocumentCorrelation extends BaseModel<String> {
	public static enum RelationType {
		USER, DOCUMENT,
	}

	private String documentId;

	private DocumentSegment segment;
	private String relationType;
	/**
	 * The target id refers to the id specified by relationType.
	 * E.g., if relationType = User, the targetId is userId
	 */
	private String targetId;

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public DocumentSegment getSegment() {
		return segment;
	}

	public void setSegment(DocumentSegment segment) {
		this.segment = segment;
	}

	public String getRelationType() {
		return relationType;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
}
