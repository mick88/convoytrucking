package com.mick88.convoytrucking.houses;

import java.util.List;

import org.json.JSONException;

import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.mick88.convoytrucking.BaseEntityAdapter;
import com.mick88.convoytrucking.R;
import com.mick88.convoytrucking.api.ApiConnection;
import com.mick88.convoytrucking.api.ApiRequest;
import com.mick88.convoytrucking.base.BaseListPageFragment;
import com.mick88.convoytrucking.dialogs.PickerDialog;
import com.mick88.convoytrucking.dialogs.PickerDialog.OnItemPickedListener;
import com.mick88.convoytrucking.houses.detail.HouseDetailsPage;

public class HouseListPage extends BaseListPageFragment<HouseEntity, HouseCollection>
{
	// Human readable representation
	private static final String
		HUMAN_ALL = "All houses",
		HUMAN_AVAILABLE = "Available for sale",
		HUMAN_FOR_SALE = "All for sale";
	public enum Filter
	{
		for_sale,
		pending,
		all,
		available,
		details
	}
	Filter filter = Filter.for_sale;
	final String BUNDLE_FILTER = "filter";
	
	private String filterToString(Filter filter)
	{
		switch (filter)
		{
			case all: return HUMAN_ALL;
			case available:return HUMAN_AVAILABLE;
//			case details:
			case for_sale: return HUMAN_FOR_SALE;
//			case pending:
			default: return "???";			
		}
	}
	
	@Override
	public void onCreate(Bundle savedState)
	{
		super.onCreate(savedState);
		setHasOptionsMenu(true);
		
		
		if (savedState != null)
		{
			if (savedState.containsKey(BUNDLE_FILTER))
			{
				filter = savedState.getParcelable(BUNDLE_FILTER);
			}
		}
	}
	
	@Override
	protected BaseEntityAdapter<HouseEntity> createAdapter(
			List<HouseEntity> entities)
	{
		return new HouseEntityAdapter(activity, entities);
	}
	
	@Override
	protected ApiRequest createRequest()
	{
		// TODO Auto-generated method stub
		return super.createRequest()
				.setShow(ApiConnection.SHOW_HOUSES)
				.setSortBy("slots")
				.setSortDesc()
				.setFilter(filter);
	}

	@Override
	protected void onTilePressed(HouseEntity entity)
	{
		activity.openPage(new HouseDetailsPage().setEntity(entity), true);		
	}

	@Override
	protected HouseCollection createEntity(String json) throws JSONException
	{
		return new HouseCollection(json);
	}
	
	@Override
	public String getWebsiteUrl()
	{
		return "http://convoytrucking.net/houses.php";
	}
	
	public void setFilter(Filter filter)
	{
		if (this.filter != filter)
		{
			this.filter = filter;
			setupActionBar(getActionBar());
			setEntity(null);
			downloadData(null);
		}		
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.houses, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == R.id.action_filter)
		{
			new PickerDialog<String>().setTitle(R.string.filter).setItems(new String[]{HUMAN_ALL, HUMAN_AVAILABLE, HUMAN_FOR_SALE})
				.setItemPickedListener(new OnItemPickedListener<String>()
			{

				@Override
				public void onItemPicked(String item, int position)
				{
					switch (position)
					{
						case 0:
							setFilter(Filter.all);
							break;
							
						case 1:
							setFilter(Filter.available);
							break;
							
						case 2:
							setFilter(Filter.for_sale);
							break;
					}
				}
			})
			.show(getFragmentManager(), null);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void setupActionBar(ActionBar actionBar)
	{
		super.setupActionBar(actionBar);
		actionBar.setTitle(R.string.title_houses);
		actionBar.setSubtitle(filterToString(this.filter));
		
	}
	
}
