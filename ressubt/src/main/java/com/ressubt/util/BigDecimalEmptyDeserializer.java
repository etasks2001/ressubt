package com.ressubt.util;

import java.lang.reflect.Type;
import java.math.BigDecimal;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class BigDecimalEmptyDeserializer implements JsonDeserializer<BigDecimal> {

    private static final String _0 = "0";

    @Override
    public BigDecimal deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
	try {
	    String element = jsonElement.getAsString();
	    if (element.length() == 0) {
		element = element.concat(_0);
	    }
	    element = element.replace(",", ".");
	    return new BigDecimal(element);
	} catch (NumberFormatException e) {
	    throw new JsonParseException(e);
	}
    }
}