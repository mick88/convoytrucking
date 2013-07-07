package com.mick88.convoytrucking.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.mick88.convoytrucking.R;
import com.mick88.convoytrucking.api.ApiRequest;
import com.mick88.convoytrucking.api.ApiRequest.ApiRequestListener;
import com.mick88.convoytrucking.api.entities.ApiEntity;
import com.mick88.convoytrucking.api.entities.ApiEntityCollection;
import com.mick88.convoytrucking.cards.CardFragment;
import com.mick88.convoytrucking.cards.DataCardFragment;
import com.mick88.convoytrucking.interfaces.Downloadable.DataStatus;
import com.mick88.convoytrucking.interfaces.OnDownloadListener;
import com.mick88.convoytrucking.interfaces.RefreshListener;

/**
 * Activity which handles cards and their data.
 * Downloads data at start
 * @author Michal
 */
@Deprecated
public abstract class BaseListActivity<T extends ApiEntityCollection<?>> extends BaseActivity
{
	protected T collection=null;
	protected Map<ApiEntity,DataCardFragment<?>> fragmentMap;
		
	private ApiRequest pendingRequest=null;
	private final String 
		BUNDLE_COLLECTION="collection";
	
	/**
	 * Implementation should create a new DataCardFragment with its entity set to entity.
	 * @return new Fragment
	 */
	abstract protected DataCardFragment<?> createDataFragment(ApiEntity entity); 
	protected abstract T processData(String json) throws JSONException;
	
	/**
	 * Called to fill its views at appropriate time
	 */
	protected void fillViewContents()
	{
		View progress = findViewById(R.id.progressBar);
		
		switch (getDataStatus())
		{
			case Empty:
				progress.setVisibility(View.GONE);
				break;				
			case Loading:
				if (collection == null)
				{
					progress.setVisibility(View.VISIBLE);
					break;
				}				
			case Loaded:
				progress.setVisibility(View.GONE);
				break;
		}
	}
	
	public void setCollection(T collection)
	{
		this.collection = collection;
		
		removeCards();
		addCards();
		
		fillViewContents();
	}
	
	private void addCards()
	{
		if (collection == null) return;
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		
		@SuppressWarnings("unchecked")
		List<ApiEntity> entities = (List<ApiEntity>) collection.getEntities();

		for (ApiEntity entity : entities)
		{						
			DataCardFragment<?> fragment = createDataFragment(entity);
			transaction.add(R.id.activity_root, fragment);			
		}
		transaction.commit();
	}
	
	protected void removeCards()
	{
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		for (CardFragment fragment : cards)
		{
			transaction.remove(fragment);
		}
		transaction.commit();
		getSupportFragmentManager().popBackStack();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstance)
	{
		super.onCreate(savedInstance);

		fragmentMap = new HashMap<ApiEntity, DataCardFragment<?>>();
		
		if (savedInstance != null)
		{
			Serializable collection = savedInstance.getSerializable(BUNDLE_COLLECTION);
			if (collection instanceof ApiEntityCollection<?>)
			{
				this.collection = (T) collection;
			}
		}
		if (this.collection == null)
		{
			downloadData();
			fillViewContents();
		}
	}
	
	public boolean downloadData()
	{
		return downloadData(null);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putSerializable(BUNDLE_COLLECTION, collection);
	}
	
	/**
	 * Base implementation downloads data and calls onDownloadFinished when done
	 */
	@Override
	protected boolean refresh(final RefreshListener refreshListener)
	{
		return downloadData(new OnDownloadListener()
		{			
			@Override
			public void onDownloadFinished()
			{				
				if (refreshListener != null) refreshListener.onRefreshFinished();				
			}
		});		
	}	

	public DataStatus getDataStatus()
	{
		if (collection == null)
		{
			return pendingRequest==null?(DataStatus.Empty):DataStatus.Loading;
		}
		else
		{
			return DataStatus.Loaded;
		}
	}
		
	
	
	public void onDataDownloaded() 
	{
		
	}
	
	protected boolean downloadData(final OnDownloadListener listener)
	{
		if (pendingRequest != null) return false;
		
		pendingRequest = createRequest()
				.SendAsync(new ApiRequestListener()
		{
			
			@Override
			public void onRequestComplete(ApiRequest apiRequest)
			{
				String result = apiRequest.getResult();
				T data=null;
				try
				{
					data = processData(result);
				}
				catch (JSONException e)
				{
					if (TextUtils.isEmpty(result) == false)
						try
						{
							onDownloadError(new JSONObject(result));
						} catch (JSONException e1)
						{
							e1.printStackTrace();
						}
				}
				setCollection(data);
				
				if (listener != null) listener.onDownloadFinished();
				pendingRequest = null;
				
			}
		});
		return true;
	}
	
	protected void onDownloadError(JSONObject result)
	{
		try
		{
			Toast.makeText(this, result.getString("message"), Toast.LENGTH_LONG).show();
		} catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	/**
	 * Creates request for API. 
	 * Super implementation creates blank request.
	 * Overridden implementation should add it's own parameters
	 */
	protected ApiRequest createRequest()
	{
		return new ApiRequest(application.getApiConnection());
	}
}
