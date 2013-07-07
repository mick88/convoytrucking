package com.mick88.convoytrucking.vehicles;

import org.json.JSONException;
import org.json.JSONObject;

import com.mick88.convoytrucking.api.entities.ApiEntityCollection;

public class VehicleEntityCollection extends ApiEntityCollection<VehicleEntity>
{
	private static final long serialVersionUID = -3832631772849787542L;

	@Override
	protected VehicleEntity parseEntityJson(JSONObject json) throws JSONException
	{
		return new VehicleEntity(json);
	}

	public VehicleEntityCollection(String json) throws JSONException
	{
		super(json);
	}
}
