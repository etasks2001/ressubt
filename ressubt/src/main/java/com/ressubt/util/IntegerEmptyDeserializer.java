package com.ressubt.util;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class IntegerEmptyDeserializer implements JsonDeserializer<Integer> {

    private static final String _0 = "0";

    @Override
    public Integer deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
	try {
	    String element = jsonElement.getAsString();
	    if (element.length() == 0) {
		element = element.concat(_0);
	    }
	    return Integer.parseInt(element);
	} catch (NumberFormatException e) {
	    throw new JsonParseException(e);
	}
    }
}