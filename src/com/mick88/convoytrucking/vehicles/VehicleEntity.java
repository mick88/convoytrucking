package com.mick88.convoytrucking.vehicles;

import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import com.mick88.convoytrucking.api.entities.ApiEntity;

public class VehicleEntity extends ApiEntity
{
	private static final long serialVersionUID = 7452716227546927236L;
	
	int modelId,
		price,
		topSpeed;
	String name;

	@Override
	public void parseJson(JSONObject json) throws JSONException
	{
		modelId = json.getInt("modelid");
		price = json.getInt("price");
		topSpeed = json.getInt("top_speed");
		name = json.getString("name");
	}
	
	public VehicleEntity(JSONObject json) throws JSONException
	{
		super(json);
	}

	public int getModelId()
	{
		return modelId;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getTopSpeedKph()
	{
		return topSpeed;
	}
	
	public CharSequence getTopSpeedKphString()
	{
		return new StringBuilder().append(getTopSpeedKph()).append(" kph");
	}
	
	public int getPrice()
	{
		return price;
	}
	
	public CharSequence getPriceString()
	{
		return new StringBuilder("$").append(getPrice());
	}
	
	public String getThumbnailUrl()
	{
		return String.format(Locale.getDefault(), "http://convoytrucking.net/pic/vehicles//Vehicle_%d.jpg", modelId);
	}
	
	public String getImageUrl()
	{
		return String.format(Locale.getDefault(), "http://convoytrucking.net/pic/vehicles//large/vehicle_%d.jpg", modelId);
	}
}
