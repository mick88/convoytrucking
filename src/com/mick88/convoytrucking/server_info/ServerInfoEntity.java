package com.mick88.convoytrucking.server_info;

import java.io.Serializable;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import com.mick88.convoytrucking.api.entities.ApiEntity;

public class ServerInfoEntity extends ApiEntity implements Serializable
{

	public enum ServerState
	{
		ONLINE,
		OFFLINE,
		LOCKED,
	}
	
	private static final long serialVersionUID = 74759157264679163L;
	String serverIp, gamemode,
		status;
	int numPlayers, maxPlayers, vipSlots,
		restartTime, uptime, startTime, maxPing=0;
	ServerState serverState;
	
	public ServerInfoEntity()
	{
		super();
	}
	
	public ServerInfoEntity(JSONObject json) throws JSONException 
	{
		super(json);
	}
	
	public ServerInfoEntity(String json) throws JSONException 
	{
		super(json);
	}

	@Override
	public void parseJson(JSONObject json) throws JSONException
	{
		if (json.has("request_status") && json.getString("request_status").equalsIgnoreCase("ERROR")) 
			throw new JSONException("Error");
		
		this.serverIp = json.getString("server_ip");
		this.gamemode = json.getString("gamemode");
		this.status = json.getString("server_status");
		
		this.serverState = Enum.valueOf(ServerState.class, this.status);
		
		if (json.isNull("num_players") == false)this.numPlayers = json.getInt("num_players");
		this.maxPlayers = json.getInt("num_slots");
		this.vipSlots = json.getInt("num_vip_slots");
		this.restartTime = json.getInt("restart_time");
		this.startTime = json.getInt("start_time");
		if (json.isNull("uptime") == false) this.uptime = json.getInt("uptime");
		
		
		if (json.isNull("max_ping") == false) this.maxPing = json.getInt("max_ping");
		
		this.valid = true;
	}
	
	@Override
	public String toString()
	{
		if (this.valid == false) return "ERROR";
		else return String.format(Locale.getDefault(), "%s %d/%d", serverIp, numPlayers, maxPlayers);
	}
	
	public String getServerIp()
	{
		return serverIp;
	}
	
	public String getGamemode()
	{
		return gamemode;
	}
	
	public int getMaxPlayers()
	{
		return maxPlayers;
	}
	
	public boolean isOnline()
	{
		return serverState != ServerState.OFFLINE;
	}
	
	public ServerState getServerState()
	{
		return serverState;
	}
	
	public int getNumPlayers()
	{
		return numPlayers;
	}
	
	public int getRestartTime()
	{
		return restartTime;
	}
	
	public int getStartTime()
	{
		return startTime;
	}
	
	public String getStatus()
	{
		return status;
	}
	
	public int getMaxPing()
	{
		return maxPing;
	}
	
	public int getUptime()
	{
		return uptime;
	}
	
	public String getUptimeStr()
	{
		int h = uptime / 60 / 60;
		int m = (uptime / 60) % 60;
		return String.format(Locale.getDefault(), "%2dh %02dm", h,m);
	}
	
	public int getVipSlots()
	{
		return vipSlots;
	}
	
}
