package com.mick88.convoytrucking.api.entities;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class ApiEntity implements Serializable, Comparable<ApiEntity>
{
	private static final long serialVersionUID = -1131133648885275999L;
	protected boolean valid=false;
	
	public abstract void parseJson(JSONObject json) throws JSONException;
	
	public ApiEntity()
	{
		
	}
	
	public ApiEntity(JSONObject json) throws JSONException
	{
		this();
		parseJson(json);
	}
	
	public ApiEntity(String json) throws JSONException
	{
		this(new JSONObject(json));
	}
	
	public boolean isValid()
	{
		return valid;
	}
	
	@Override
	public int compareTo(ApiEntity another)
	{
		// TODO Auto-generated method stub
		return 0;
	}
}
