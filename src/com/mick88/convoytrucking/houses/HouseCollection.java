package com.mick88.convoytrucking.houses;

import org.json.JSONException;
import org.json.JSONObject;

import com.mick88.convoytrucking.api.entities.ApiEntityCollection;

public class HouseCollection extends ApiEntityCollection<HouseEntity>
{
	private static final long serialVersionUID = 6185120602621881365L;

	@Override
	protected HouseEntity parseEntityJson(JSONObject json) throws JSONException
	{
		return new HouseEntity(json);
	}
	
	public HouseCollection(String json) throws JSONException
	{
		super(json);
	}

}
