package com.mick88.convoytrucking.cards;

import java.io.Serializable;

import android.os.Bundle;

import com.mick88.convoytrucking.api.entities.ApiEntity;

/**
 * A fragment that shows ApiEntity in a card.
 * Handles saving instance state and recreating it in onCreate.
 * @author Michal
 *
 * @param <T> Type of data shown
 */
public abstract class DataCardFragment<T extends ApiEntity> extends CardFragment
{
	protected T entity=null;
	
	private final String BUNDLE_ENTITY = "entity";
	
	public DataCardFragment()
	{
		super();
	}
	
	public DataCardFragment(T entity)
	{
		this();
		setEntity(entity);
	}
	
	@Override
	protected void fillViewContents()
	{
		if (rootView != null)
		{
			setLoading(entity == null);
		}
	}
	
	public DataCardFragment<T> setEntity(T entity)
	{
		this.entity = entity;
		if (rootView != null) fillViewContents();
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		// Restore entity
		if (savedInstanceState != null)
		{
			Serializable entity = savedInstanceState.getSerializable(BUNDLE_ENTITY);
			if (entity instanceof ApiEntity)
			{
				setEntity((T) entity);
			}
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putSerializable(BUNDLE_ENTITY, (Serializable) this.entity);		
	}
	
	
}
