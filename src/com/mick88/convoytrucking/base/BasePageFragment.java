package com.mick88.convoytrucking.base;

import org.json.JSONException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.mick88.convoytrucking.api.ApiConnection;
import com.mick88.convoytrucking.api.ApiRequest;
import com.mick88.convoytrucking.api.ApiRequest.ApiRequestListener;
import com.mick88.convoytrucking.api.entities.ApiEntity;
import com.mick88.convoytrucking.api.entities.ApiError;
import com.mick88.convoytrucking.interfaces.OnDownloadListener;
import com.mick88.convoytrucking.interfaces.RefreshListener;

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
				String errorMessage = null;
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
							errorMessage = error.getMessage();
							
						} else
						{
							Log.e(getClass().getName(),
									"Unknown error while parsing "
											+ apiRequest.getResult());
							errorMessage = "Unknown error while parsing received data.";
						}
						
					}
				}
				else errorMessage = "No data received from server";
				
				if (errorMessage != null)
				{
					new AlertDialog.Builder(activity)
						.setTitle("Error")
						.setMessage(errorMessage).setPositiveButton("Retry", new DialogInterface.OnClickListener()
						{
							
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								downloadData(listener);								
							}
						})
						.setNegativeButton(android.R.string.cancel, null)
						.show();
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
	public void onCreate(Bundle arg0)
	{
		super.onCreate(arg0);

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
	
}
