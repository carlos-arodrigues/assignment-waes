package com.waes.rodrigues.carlos.assignment;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class DiffTest {
	
	protected String createJSON(String name, Integer age){
		JSONObject jsonObject = new JSONObject();
		try {
			if(StringUtils.isNoneBlank(name)){
				jsonObject.put("name", name);
			}
			if(age != null){
				jsonObject.put("age", age.toString());
			}
			return jsonObject.toString();
		} catch (JSONException e) {
			throw new RuntimeException();
		}
	}

}
