package com.bufanbaby.common.model;

/**
 * Relation between post and documentmeta
 */
public class PostDocument extends BaseModel<String> {
	public static enum ReferType {
		LOCAL, REMOTE
	}

	private String documentId;
	private String postId;

	/**
	 * The referType specifies how the document is used by a post.
	 * It is the value of ReferType. E.g. LOCAL means the document
	 * only stays in client.
	 */
	private String referType;

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getReferType() {
		return referType;
	}

	public void setReferType(String referType) {
		this.referType = referType;
	}
}
