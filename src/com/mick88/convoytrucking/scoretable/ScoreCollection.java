package com.mick88.convoytrucking.scoretable;

import org.json.JSONException;
import org.json.JSONObject;

import com.mick88.convoytrucking.api.entities.ApiEntityCollection;

public class ScoreCollection extends ApiEntityCollection<ScoreEntity>
{
	private static final long serialVersionUID = 472799843870318300L;
	
	public ScoreCollection(String json) throws JSONException
	{
		super(json);
	}
	

	@Override
	protected ScoreEntity parseEntityJson(JSONObject json) throws JSONException
	{
		return new ScoreEntity(json);
	}
	
	
	
}
