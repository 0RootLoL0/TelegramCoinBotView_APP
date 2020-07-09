/*
 * Copyright © 2020 . Lorem Popov Vasily.
 * Licensed under the GNU GPL, Version 3
 */

package io.github.rootlol.jsonsimpleconverter;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class JsonObjectRequestBodyConverter implements Converter<ResponseBody, JSONObject>{
    final static JsonObjectRequestBodyConverter INSTANCE = new JsonObjectRequestBodyConverter();
    @Override
    public JSONObject convert(ResponseBody value) throws IOException {
        JSONObject weatherJsonObject = null;
        try {
            weatherJsonObject = (JSONObject) JSONValue.parseWithException(value.string());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return weatherJsonObject;
    }
}