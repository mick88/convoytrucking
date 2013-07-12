package com.mick88.convoytrucking.scoretable;

import java.util.List;
import java.util.Locale;

import org.json.JSONException;

import android.annotation.TargetApi;
import android.os.Build;
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
import com.mick88.convoytrucking.player.PlayerPage;

public class ScoretablePage extends BaseListPageFragment<ScoreEntity, ScoreCollection>
{

	final String BUNDLE_SORT_BY = "sort_by";
	
	public enum SortBy
	{
		truck_loads,
		score,
		odo,
		convoy_score,
	}
	SortBy sortBy = SortBy.truck_loads;
	
	/**
	 * Converts SortBy enum to human readable string
	 */
	public static String sortByToString(SortBy sortBy)
	{
		switch (sortBy)
		{
			case convoy_score:
				return "Convoy score";
				
			case odo:
				return "Odometer";
				
			case score:
				return "Score";
				
			case truck_loads:
				return "Truck loads";
		}
		return "";
	}
	
	@Override
	public String getWebsiteUrl()
	{
		String sort = (sortBy == SortBy.truck_loads?"loads" : sortBy.toString());
		return String.format(Locale.getDefault(), "http://convoytrucking.net/index.php?show=scoretable&sort=%s#table", sort);
	}
	
	
	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putSerializable(BUNDLE_SORT_BY, sortBy);
	}
	
	@Override
	public void onCreate(Bundle savedState)
	{
		super.onCreate(savedState);
		setHasOptionsMenu(true);
		if (savedState != null)
		{
			Object sort = savedState.getSerializable(BUNDLE_SORT_BY);
			if (sort instanceof SortBy)
			{
				sortBy = (SortBy) sort;
			}
		}
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.scoretable, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == R.id.action_sort)
		{
			new PickerDialog<String>().setTitle(R.string.sort_by)
				.setItems(new String[] {sortByToString(SortBy.convoy_score), sortByToString(SortBy.odo), sortByToString(SortBy.score), sortByToString(SortBy.truck_loads)} )
				.setItemPickedListener(new OnItemPickedListener<String>()
				{

					@Override
					public void onItemPicked(String item, int position)
					{
//						setSortBy(item);
						// SortBy.convoy_score, SortBy.odo, SortBy.score, SortBy.truck_loads
						switch(position)
						{
							case 0:
								setSortBy(SortBy.convoy_score);
								break;
								
							case 1:
								setSortBy(SortBy.odo);
								break;
								
							case 2:
								setSortBy(SortBy.score);
								break;
								
							case 3:
								setSortBy(SortBy.truck_loads);
								break;
						}
						
					}
				}).show(getFragmentManager(), null);
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Update subtitle to show sorting criteria
	 * @param actionBar
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void updateSortSubtitle()
	{
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) return;
		getActionBar().setSubtitle(sortByToString(sortBy));
	}
	
	public void setSortBy(SortBy sortBy)
	{
		if (this.sortBy != sortBy)
		{
			this.sortBy = sortBy;
			updateSortSubtitle();
			setEntity(null);
			downloadData(null);
		}
	}
	
	
	@Override
	protected ApiRequest createRequest()
	{
		return super.createRequest()
				.setShow(ApiConnection.SHOW_SCORETABLE)
				.setSortBy(sortBy.toString());
	}

	@Override
	protected ScoreCollection createEntity(String json) throws JSONException
	{
		return new ScoreCollection(json);
	}

	@Override
	protected BaseEntityAdapter<ScoreEntity> createAdapter(
			List<ScoreEntity> entities)
	{
		return new ScoreEntityAdapter(activity, entities, sortBy);
	}
	
	@Override
	protected void onTilePressed(ScoreEntity entity)
	{
		activity.openPage(new PlayerPage().setPlayerid(entity.getPlayerid()), true);
		
	}
	
	
	
	@Override
	public void setupActionBar(ActionBar actionBar)
	{
		super.setupActionBar(actionBar);

		actionBar.setTitle(R.string.title_scoretable);
		updateSortSubtitle();
	}

}
