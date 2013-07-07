package com.mick88.convoytrucking.cards;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mick88.convoytrucking.R;
import com.mick88.convoytrucking.api.ApiRequest;
import com.mick88.convoytrucking.api.ApiRequest.ApiRequestListener;
import com.mick88.convoytrucking.api.entities.ApiEntity;
import com.mick88.convoytrucking.interfaces.Downloadable.DataStatus;
import com.mick88.convoytrucking.interfaces.OnDownloadListener;

/**
 * Base class for a Fragment that downloads and displays downloaded information.
 * @author Michal
 *
 * @param <T> type of data displayed, an ApiEntity
 */
public abstract class DownloadableDataCardFragment<T extends ApiEntity> extends DataCardFragment<T>
{
	private ApiRequest pendingRequest = null;
	protected abstract T processData(String json) throws JSONException;
	
	public boolean downloadData()
	{
		return downloadData(null);
	}
	
	/**
	 * Creates ApiRequest.
	 * Implementation must specify its request parameters
	 */
	protected ApiRequest createRequest()
	{
		if (application == null) return null;
		return new ApiRequest(application.getApiConnection());
	}
	
	/**
	 * Starts working in background.
	 * Returns false if download is already in progress
	 */
	public boolean downloadData(final OnDownloadListener downloadListener)
	{
		if (pendingRequest != null) return false;
		
		pendingRequest = createRequest()
			.SendAsync(new ApiRequestListener()
			{
				
				@Override
				public void onRequestComplete(ApiRequest apiRequest)
				{
					String result = apiRequest.getResult();
					
					try
					{
						setEntity(processData(result));
					}
					catch (JSONException e)
					{
						try
						{
							onRequestError(new JSONObject(result));
						} catch (JSONException e1)
						{
							e1.printStackTrace();
						}
					}
					
					if (downloadListener != null) downloadListener.onDownloadFinished();
					pendingRequest = null;	
					
				}
			});
		return true;
	}
	
	protected void onRequestError(JSONObject result)
	{
		try
		{
			Toast.makeText(activity, result.getString("message"), Toast.LENGTH_LONG).show();
		} 
		catch (JSONException e)
		{
			e.printStackTrace();
		}		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{		
		super.onCreate(savedInstanceState);
		
		// download data
		if (entity == null) downloadData();
	}
	
	/**
	 * Fills views with information.
	 * Base implementation just hides/shows content or progress bar
	 */
	@Override
	protected void fillViewContents()
	{
		if (rootView != null)
		{
			View progress = rootView.findViewById(R.id.progressBar);
			View content = rootView.findViewById(R.id.card_content);
			switch (getDataStatus())
			{
				case Empty:
					progress.setVisibility(View.GONE);
					content.setVisibility(View.INVISIBLE);
					break;
					
				case Loading:
					if (entity == null)
					{
						progress.setVisibility(View.VISIBLE);
						content.setVisibility(View.INVISIBLE);
						break;
					}
					// fall through
					
				case Loaded:
					progress.setVisibility(View.GONE);
					content.setVisibility(View.VISIBLE);
					break;
			}
		}
	}

	public DataStatus getDataStatus()
	{
		if (entity == null)
		{
			if (pendingRequest == null) return DataStatus.Empty;
			else return DataStatus.Loading;
		}
		else 
		{
			return DataStatus.Loaded;
		}
	}
}
