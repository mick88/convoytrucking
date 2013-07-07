package com.mick88.convoytrucking.api.entities;

import org.json.JSONException;
import org.json.JSONObject;

public class ApiError extends ApiEntity
{
	private static final long serialVersionUID = -8348467671915188581L;
	String message;
	String errorClass;
	
	public String getMessage()
	{
		return message;
	}
	
	public String getErrorClass()
	{
		return errorClass;
	}
	
	public ApiError(String json) throws JSONException
	{
		super(json);
	}
	
	public ApiError(JSONObject json) throws JSONException
	{
		super(json);
	}

	@Override
	public void parseJson(JSONObject json) throws JSONException
	{
		if (json.getString("request_status").equals("ERROR"))
		{
			message = json.getString("message");
			errorClass = json.getString("error_class");
		}		
	}
	
	@Override
	public String toString()
	{
		return message;
	}
}
