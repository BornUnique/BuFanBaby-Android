package com.bufanbaby.android.model;

/**
 * Constants
 */
public interface CommonConsts {
	String IntentResult = "intent.result";
	String IntentResultTypeName = "intent.result.type";

	enum IntentResultType {
		OK,
		UNKNOWN_ERROR,
		CLIENT_INPUT_ERROR,
		CLIENT_UI_ERROR,
		CLIENT_SERVICE_ERROR,
		SERVER_GENERIC_ERROR,
	}
}
