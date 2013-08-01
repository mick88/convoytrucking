package com.mick88.convoytrucking.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;

import com.mick88.convoytrucking.api.entities.ApiError;

public class ApiRequest
{	
	public interface BeforeFinishListener
	{
		/**
		 * Called asynchronously afer download is finished to process data before returning to UI thread
		 */
		void onBeforeQueryFinished(ApiRequest apiRequest);
	}
	
	public interface ApiRequestListener
	{
		/**
		 * Called when request is complete.
		 * You can get data using getResult.. methods
		 */
		void onRequestComplete(ApiRequest apiRequest);
	}
	
	final ApiConnection apiConnection;
	final Map<String, String> params;
	private String result;
	private BeforeFinishListener beforeFinishListener = null;
	
	public boolean isEmpty()
	{
		return TextUtils.isEmpty(result);
	}
	
	public ApiRequest(ApiConnection apiConnection, Map<String,String> params)
	{
		this.params = params;
		this.apiConnection = apiConnection;
	}
	
	public ApiRequest(ApiConnection apiConnection)
	{
		this(apiConnection, new HashMap<String, String>());
	}
	
	public ApiRequest Send() throws IOException
	{
		this.result = apiConnection.getString(params);
		if (beforeFinishListener != null) 
			beforeFinishListener.onBeforeQueryFinished(this);
		return this;
	}
	
	/**
	 * Sends request in a separate thread and calls the listener in hte UI thread when finished
	 * @return
	 */
	@SuppressLint("NewApi")
	public ApiRequest SendAsync(final ApiRequestListener listener)
	{
		AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>()
		{

			@Override
			protected String doInBackground(Void... params)
			{
				try
				{
					Send();
					return getResult();
				} 
				catch (IOException e)
				{
					e.printStackTrace();
				}
				return "";
			}
			
			@Override
			protected void onPostExecute(String result)
			{
				super.onPostExecute(result);
				listener.onRequestComplete(ApiRequest.this);
			}
			
			@Override
			protected void onCancelled()
			{
				super.onCancelled();
				listener.onRequestComplete(ApiRequest.this);
			}
		};
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) task.execute(); 
		else task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[])null);
		return this;
	}
	
	public ApiRequest setParam(String key, String value)
	{
		params.put(key, value);
		return this;
	}
	
	public ApiRequest setParam(String key, int value)
	{
		return setParam(key, String.valueOf(value));
	}
	
	public ApiRequest setShow(String section)
	{
		return setParam(ApiConnection.PARAM_SHOW, section);
	}
	
	public ApiRequest setFilter(String filter)
	{
		return setParam(ApiConnection.PARAM_FILTER, filter);
	}
	
	public ApiRequest setFilter(Object filter)
	{
		return setFilter(filter.toString());
	}
	
	public ApiRequest setPage(int page)
	{
		return setParam(ApiConnection.PARAM_PAGE, page);
	}
	
	public ApiRequest setLimit(int limit)
	{
		return setParam(ApiConnection.PARAM_LIMIT, String.valueOf(limit));
	}
	
	public ApiRequest setSortBy(String field)
	{
		return setParam(ApiConnection.PARAM_SORT_BY, field);
	}
	
	public ApiRequest setSortDesc()
	{
		return setParam(ApiConnection.PARAM_SORT_DESC, "true");
	}
	
	public ApiRequest setId(int id)
	{
		return setParam(ApiConnection.PARAM_ID, String.valueOf(id));
	}
	
	public ApiRequest setId(Integer [] ids)
	{
		return setParam(ApiConnection.PARAM_ID, TextUtils.join(",", ids));
	}
	
	public String getResult()
	{
		if (result == null) return null;
		synchronized (result)
		{
			return result;
		}		
	}
	
	public JSONObject getResultJson() throws JSONException
	{
		return new JSONObject(getResult());
	}
	
	public JSONArray getResultJsonArray() throws JSONException
	{
		return new JSONArray(getResult());
	}
	
	public ApiError getError()
	{
		if (result == null) return null;
		
		try
		{
			return new ApiError(getResult());
		}
		catch (JSONException e)
		{
			return null;
		}
	}
	
	public ApiRequest setBeforeFinishListener(
			BeforeFinishListener beforeFinishListener)
	{
		this.beforeFinishListener = beforeFinishListener;
		return this;
	}
}
