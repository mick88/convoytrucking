package com.mick88.convoytrucking.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mick88.convoytrucking.api.entities.ApiEntity;
import com.mick88.convoytrucking.api.entities.ApiEntityCollection;
import com.mick88.convoytrucking.server_info.ServerInfoEntity;

public class ApiConnection
{
	public static final String 
			PARAM_SHOW = "show",
			PARAM_FILTER = "filter",
			PARAM_PAGE = "page",
			PARAM_LIMIT = "limit",
			PARAM_SORT_BY = "sort_by",
			PARAM_SORT_DESC = "sort_desc",
			PARAM_ID = "id";
	
	public static final String 
		SHOW_SERVER_INFO = "server_info",
		SHOW_HOUSES = "houses",
		SHOW_SCORETABLE = "scoretable",
		SHOW_VEHICLES = "vehicles",
		SHOW_PLAYER = "player",
		SHOW_PLAYERS = "players",
		SHOW_MAP = "map";
	
	
	private final String apiKey;
	private static final String apiUrl = "http://api.convoytrucking.net/api.php";
	
	public ApiConnection(String apiKey)
	{
		this.apiKey = apiKey;
	}
	
	public String getString(Map<String,String> params) throws IOException
	{
		StringBuilder url = new StringBuilder(apiUrl)
			.append('?').append("api_key=").append(apiKey);
		
		for (Map.Entry<String, String> entry : params.entrySet())
		{
			url.append('&')
				.append(entry.getKey())
				.append('=')
				.append(entry.getValue());
		}
		HttpGet get = new HttpGet(url.toString());
		DefaultHttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(get);
		InputStream stream = response.getEntity().getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		
		StringBuilder builder = new StringBuilder();
		
		String line;
		while ((line = reader.readLine()) != null)
		{
			builder.append(line);
		}
		
		reader.close();
		stream.close();
		response.getEntity().consumeContent();
				
		return builder.toString();
	}
	
	public JSONObject getJson(Map<String,String> params) throws IOException, JSONException
	{
		return new JSONObject(getString(params));
	}
	
	public JSONArray getJsonArray(Map<String,String> params) throws IOException, JSONException
	{
		return new JSONArray(getString(params));
	}
	
	public JSONObject getSection(String show, Map<String, String> params) throws IOException, JSONException
	{
		params.put(PARAM_SHOW, show);
		return getJson(params);
	}
	
	public JSONArray getSectionCollection(String show, Map<String, String> params) throws IOException, JSONException
	{
		params.put(PARAM_SHOW, show);
		return getJsonArray(params);
	}
	
	public JSONArray getSectionCollection(String show) throws IOException, JSONException
	{
		return getSectionCollection(show, new HashMap<String, String>());
	}
	
	public JSONObject getSection(String show) throws IOException, JSONException
	{
		return getSection(show, new HashMap<String, String>());
	}
	
	public <T extends ApiEntity> T getSectionEntity(String show, T entity) throws JSONException, IOException
	{
		if (entity instanceof ApiEntityCollection<?>) ((ApiEntityCollection<?>) entity).parseJsonArray(getSectionCollection(show));
		else entity.parseJson(getSection(show));
		return entity;
	}
	
	public ServerInfoEntity getServerInfo() throws IOException, JSONException
	{
		return getSectionEntity(SHOW_SERVER_INFO, new ServerInfoEntity());
	}
	
	public ApiRequest createRequest()
	{
		return new ApiRequest(this);
	}
}
