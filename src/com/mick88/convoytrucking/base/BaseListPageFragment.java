package com.mick88.convoytrucking.base;

import java.util.List;

import org.json.JSONException;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;

import com.mick88.convoytrucking.R;
import com.mick88.convoytrucking.api.ApiRequest;
import com.mick88.convoytrucking.api.ApiRequest.ApiRequestListener;
import com.mick88.convoytrucking.api.entities.ApiEntity;
import com.mick88.convoytrucking.api.entities.ApiEntityCollection;
import com.mick88.convoytrucking.interfaces.OnDownloadListener;

public abstract class BaseListPageFragment<ET extends ApiEntity, T extends ApiEntityCollection<ET>> 
	extends BasePageFragment<T> implements OnItemClickListener, OnScrollListener
{
	AbsListView absListView = null;
	protected int itemsPerPage = 20,
			page = 0,
			pageBufferSize = itemsPerPage;
	protected boolean hasMorePages=true;
	final String BUNDLE_PAGE = "page";
	
	/**
	 * Should instantiate EntityAdapter with entities provided
	 */
	protected abstract BaseEntityAdapter<ET> createAdapter(List<ET> entities);
	
	/**
	 * Called when user clicks on a card.
	 * @param entity
	 */
	protected abstract void onTilePressed(ET entity);
	
	@Override
	protected boolean fillViewContents()
	{
		if (super.fillViewContents())
		{
			fillList();
			return true;
		}
		else
		{
			showProgressBar();
			return false;
		}
	}
	
	@Override
	protected void downloadData(OnDownloadListener listener)
	{
		page=0;
		super.downloadData(listener);
	}
	
	private void fillList()
	{
		if (entity != null && absListView != null)
		{
			hideProgressBar();
			
			// these conditions prevend crash on Gingerbread devices
			if (absListView instanceof GridView)
			{
				GridView gridView = (GridView) absListView;
				gridView.setAdapter(createAdapter(entity.getEntities()));
			}
			else if (absListView instanceof ListView)
			{
				ListView listView = (ListView) this.absListView;
				listView.setAdapter(createAdapter(entity.getEntities()));
			}
		}
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long id)
	{
		/*
		 * Handle list item clicks
		 */
		
		Object clicked = adapter.getItemAtPosition(position);
		if (clicked instanceof ApiEntity)
		{
			onTilePressed((ET) clicked);
		}
		
	}

	@Override
	protected final int selectLayout()
	{
		return R.layout.page_generic_list;
	}
	
	@Override
	protected ApiRequest createRequest()
	{
		return super.createRequest()
				.setPage(page)
				.setLimit(itemsPerPage);
	}
	
	protected void hideProgressBar()
	{
		if (rootView != null)
		{				
			rootView.findViewById(R.id.progressBar)
				.setVisibility(View.GONE);
			rootView.findViewById(R.id.listView)
				.setVisibility(View.VISIBLE);
		}
	}
	
	protected void showProgressBar()
	{
		if (rootView != null)
		{
			rootView.findViewById(R.id.progressBar)
				.setVisibility(View.VISIBLE);
			rootView.findViewById(R.id.listView)
			.setVisibility(View.GONE);
		}
	}
	
	protected View findViewById(int id)
	{
		if (rootView == null) return null;
		else return rootView.findViewById(id);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		
		this.absListView = (AbsListView) rootView.findViewById(R.id.listView);
		absListView.setOnItemClickListener(this);
		absListView.setOnScrollListener(this);
		if (absListView instanceof ListView)
		{
			((ListView) absListView).setDivider(null);
			
		}

		fillViewContents();
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putInt(BUNDLE_PAGE, page);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		if (savedInstanceState != null)
		{
			if (savedInstanceState.containsKey(BUNDLE_PAGE))
			{
				page = savedInstanceState.getInt(BUNDLE_PAGE);
			}
		}
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onDestroyView()
	{
		absListView = null;
		super.onDestroyView();
	}
	
	void loadNextPage()
	{
		if (entity == null || pendingRequest != null) return;
		
//		page++;
//		Log.d(toString(), "Loading more elements. Page "+page);
		
		pendingRequest = createRequest().setPage(page+1).SendAsync(new ApiRequestListener()
		{
			
			@Override
			public void onRequestComplete(ApiRequest apiRequest)
			{
				pendingRequest = null;
				if (absListView == null || entity == null) return;
				try
				{
					T newEntity = createEntity(apiRequest.getResult());
					int count = newEntity.getEntities().size();
					hasMorePages = (count == itemsPerPage);
					entity.merge(newEntity);
					BaseEntityAdapter<ET> adapter = (BaseEntityAdapter<ET>) absListView.getAdapter();
					adapter.notifyDataSetChanged();
					page++;
				} 
				catch (JSONException e)
				{
					e.printStackTrace();
				}
				
			}
		});
	}
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount)
	{
		int lastVisible = firstVisibleItem + visibleItemCount;
		
		if (hasMorePages && lastVisible >= totalItemCount-pageBufferSize) loadNextPage();
//		Log.d(toString(), String.format("OnScroll(): first=%d visCount=%d total=%d", firstVisibleItem, visibleItemCount, totalItemCount));
		
	}
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState)
	{
		// TODO Auto-generated method stub
				
	}
}
