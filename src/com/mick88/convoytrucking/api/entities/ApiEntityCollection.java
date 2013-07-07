package com.mick88.convoytrucking.api.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


public abstract class ApiEntityCollection<T extends ApiEntity> extends ApiEntity
{
	private static final long serialVersionUID = 8069211750693884899L;
	protected List<T> entities;
	
	protected abstract T parseEntityJson(JSONObject json) throws JSONException;
	
	public ApiEntityCollection()
	{
		super();
		entities = new ArrayList<T>();
	}
	
	public ApiEntityCollection(JSONArray json) throws JSONException
	{
		this();
		parseJsonArray(json);
	}
	
	public ApiEntityCollection(String json) throws JSONException
	{
		this(new JSONArray(json));
	}
	
	@Override
	public final void parseJson(JSONObject json) throws JSONException
	{
		Log.w("ApiEntityCollection", "Do not call parseJson on a Collection! use parseJsonArray instead for efficiency!");
		parseJsonArray(new JSONArray(json.toString()));
	}
	
	public boolean addEntity(T entity)
	{
		entities.add(entity);
		return true;
	}
	
	public void addEntities(Collection<T> entities)
	{
		this.entities.addAll(entities);
	}
	
	/**
	 * Adds all entities from the collection toe self
	 * @param collection
	 */
	public void merge(ApiEntityCollection<T> collection)
	{
		addEntities(collection.getEntities());
	}
	
	public final void parseJsonArray(JSONArray array) throws JSONException
	{
		for (int i=0; i < array.length(); i++)
		{
			addEntity(parseEntityJson(array.getJSONObject(i)));
		}
		valid=true;
	}
	
	public List<T> getEntities()
	{
		return entities;
	}
	
	public void sortEntities()
	{
		Collections.sort(entities);
	}

}
