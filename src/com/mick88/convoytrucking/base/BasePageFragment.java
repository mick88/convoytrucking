package com.mick88.convoytrucking.base;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mick88.convoytrucking.ConvoyTruckingApp;
import com.mick88.convoytrucking.activities.MainActivity;
import com.mick88.convoytrucking.api.ApiConnection;
import com.mick88.convoytrucking.api.ApiRequest;
import com.mick88.convoytrucking.api.ApiRequest.ApiRequestListener;
import com.mick88.convoytrucking.api.entities.ApiEntity;
import com.mick88.convoytrucking.api.entities.ApiError;
import com.mick88.convoytrucking.cards.CardFragment;
import com.mick88.convoytrucking.interfaces.OnDownloadListener;
import com.mick88.convoytrucking.interfaces.RefreshListener;
import com.mick88.util.FontApplicator;

/**
 * Base page fragment. Displays information as cards.
 * 
 * @author Michal
 * 
 */
public abstract class BasePageFragment<T extends ApiEntity> extends
		BaseFragment
{
	public static final String EXTRA_ENTITY = "entity";
	protected T entity;
	protected MainActivity activity = null;
	protected ConvoyTruckingApp application;
	protected ViewGroup rootView = null;
	ApiConnection connection;
	ApiRequest pendingRequest = null;
	protected abstract T createEntity(String json) throws JSONException;

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putSerializable(EXTRA_ENTITY, entity);
	}

	/**
	 * Sets entity and updates views
	 * 
	 * @param entity
	 * @return
	 */
	public BasePageFragment<T> setEntity(T entity)
	{
		this.entity = entity;
		fillViewContents();
		return this;
	}
	
	protected void downloadData(final OnDownloadListener listener)
	{
		pendingRequest = createRequest().SendAsync(new ApiRequestListener()
		{
			@Override
			public void onRequestComplete(ApiRequest apiRequest)
			{
				if (apiRequest.isEmpty() == false)
				{
					try
					{
						setEntity(createEntity(apiRequest.getResult()));
					} catch (JSONException e)
					{
						ApiError error = apiRequest.getError();
						if (error != null)
						{
							Log.e(getClass().getName(), error.getMessage());
						} else
							Log.e(getClass().getName(),
									"Unknown error while parsing "
											+ apiRequest.getResult());
					}
				}
				pendingRequest = null;
				
				if (listener != null)
					listener.onDownloadFinished();
			}
		});
	}
	
	@Override
	protected boolean fillViewContents()
	{
		if (entity != null && super.fillViewContents())
		{
			return true;
		} 
		else
			return false;
	}


	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		this.activity = (MainActivity) activity;
		this.fontApplicator = new FontApplicator(activity.getAssets(),
				ConvoyTruckingApp.FONT_ROBOTO_LIGHT);
	}

	@Override
	public void onCreate(Bundle arg0)
	{
		super.onCreate(arg0);

		this.activity = (MainActivity) getActivity();
		this.application = (ConvoyTruckingApp) activity.getApplication();

		this.connection = application.getApiConnection();
		if (arg0 != null)
		{
			Object state = arg0.getSerializable(EXTRA_ENTITY);
			if (state instanceof ApiEntity)
			{
				setEntity((T) state);
			}
		}
	
		if (entity == null)
		{
			downloadData(null);
		}
	}

	public boolean refresh(final RefreshListener listener)
	{
		if (pendingRequest != null)
			return false;
	
		downloadData(new OnDownloadListener()
		{
	
			@Override
			public void onDownloadFinished()
			{
				if (listener != null)
					listener.onRefreshFinished();
			}
		});
		return true;
	}

	/**
	 * Creates empty request
	 */
	protected ApiRequest createRequest()
	{
		return connection.createRequest();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		rootView = (ViewGroup) inflater.inflate(selectLayout(), null);
		fontApplicator.applyFont(rootView);
		fillViewContents();

		setupActionBar(activity.getSupportActionBar());
		return rootView;
	}

	@Override
	public void onDestroyView()
	{
		rootView = null;
		super.onDestroyView();
	}

	protected View findViewById(int id)
	{
		if (rootView == null)
			return null;
		else
			return rootView.findViewById(id);
	}
}
