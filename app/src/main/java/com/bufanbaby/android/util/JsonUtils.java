package com.bufanbaby.android.util;

import com.bufanbaby.android.exception.DataConversionException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provide simple JSON use
 */
public abstract class JsonUtils {
	private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);
	private static final ObjectMapper json;

	static {
		json = new ObjectMapper();
		json.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
		json.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
	}

	public static String toJson(Object obj) throws DataConversionException {
		try {
			return json.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			DataConversionException ex = new DataConversionException(e);
			throw ex;
		}
	}
}
