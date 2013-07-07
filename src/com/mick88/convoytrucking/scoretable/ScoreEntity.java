package com.mick88.convoytrucking.scoretable;

import java.text.NumberFormat;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import com.mick88.convoytrucking.api.entities.ApiEntity;
import com.mick88.util.Distance;

public class ScoreEntity extends ApiEntity
{
	private static final long serialVersionUID = 7450509677751590866L;
	
	int position, playerid,
		truckloads, score, odo, convoy;
	String playername;
	
	public ScoreEntity(JSONObject json) throws JSONException
	{
		super(json);
	}

	@Override
	public void parseJson(JSONObject json) throws JSONException
	{
		position = json.getInt("position");
		
		playername = json.getString("player_name");
		playerid = json.getInt("player_id");
		
		score = json.getInt("score");
		odo = json.getInt("odo");
		convoy = json.getInt("convoy_score");
		truckloads = json.getInt("truck_loads");		
	}
	
	public int getPosition()
	{
		return position;
	}
	
	public int getConvoy()
	{
		return convoy;
	}
	
	public int getOdo()
	{
		return odo;
	}
	
	public String getOdoKm()
	{
//		NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
		Distance dist = new Distance(getOdo());
		return dist.toKmString();
//		return String.format("%skm", format.format(getOdo() / 1000));
	}
	
	public int getPlayerid()
	{
		return playerid;
	}
	
	public String getPlayername()
	{
		return playername;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public int getTruckloads()
	{
		return truckloads;
	}
	
	@Override
	public int compareTo(ApiEntity another)
	{
		if (another instanceof ScoreEntity)
		{
			ScoreEntity other = (ScoreEntity) another;
			if (this.position > other.position) return 1;
			else return -1;
		}
		return super.compareTo(another);
	}
	
}
