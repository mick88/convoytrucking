package com.mick88.convoytrucking.base;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.mick88.convoytrucking.ConvoyTruckingApp;
import com.mick88.convoytrucking.R;
import com.mick88.convoytrucking.activities.MainActivity;
import com.mick88.convoytrucking.interfaces.OnDownloadListener;
import com.mick88.convoytrucking.interfaces.RefreshListener;
import com.mick88.util.FontApplicator;

/**
 * base fragment for all pages
 */
public abstract class BaseFragment extends SherlockFragment
{
	protected ViewGroup rootView = null;
	protected MainActivity activity = null;
	protected ConvoyTruckingApp application;
	protected FontApplicator fontApplicator = null;

	/**
	 * specifies which layout should be inflated for this fragment
	 * @return
	 */
	protected abstract int selectLayout();
	
	/**
	 * Downloads data in background if not already downloading 
	 * @return false if data is already being downloaded
	 */
	public abstract boolean refresh(final RefreshListener listener);
	
	/**
	 * Downloads data in background
	 */
	protected abstract void downloadData(final OnDownloadListener listener);

	/**
	 * Allows fragment to introduce required settings to the actionbar. Super
	 * implementation sets title to app name and navigation mode to default
	 * 
	 * @param actionBar
	 */
	public void setupActionBar(ActionBar actionBar)
	{
		actionBar.setLogo(R.drawable.convoy_trucking_logo);
		actionBar.setDisplayUseLogoEnabled(true);
		actionBar.setTitle(R.string.app_name);
		actionBar.setSubtitle(null);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
	}

	protected ActionBar getActionBar()
	{
		if (activity == null)
			return null;
		return activity.getSupportActionBar();
	}

	/**
	 * Should return url to the website equivalent of this page or null if there
	 * is none.
	 */
	public String getWebsiteUrl()
	{
		return null;
	}

	

	/**
	 * checks if view is created and entity exists, then calls fillContents()
	 * returns true if both conditions are true
	 */
	protected boolean fillViewContents()
	{
		return (rootView != null);
	}

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		Log.d(toString(), "Fragment attached");
		this.activity = (MainActivity) activity;
		this.fontApplicator = new FontApplicator(activity.getAssets(),
				ConvoyTruckingApp.FONT_ROBOTO_LIGHT);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(android.os.Bundle arg0)
	{
		super.onCreate(arg0);
	
		Log.d(toString(), "Fragment created");
	
		this.activity = (MainActivity) getActivity();
		this.application = (ConvoyTruckingApp) activity.getApplication();
		
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		super.onCreateOptionsMenu(menu, inflater);
	
		inflater.inflate(R.menu.page, menu);
		if (getWebsiteUrl() == null)
			menu.findItem(R.id.action_web).setVisible(false);
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.action_web:
				String url = getWebsiteUrl();
				if (url == null) return true;
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(url));
				startActivity(intent);
				return true;
	
			case R.id.action_page_refresh:
				if (refresh(new RefreshListener()
				{
	
					@Override
					public void onRefreshFinished()
					{
						item.setActionView(null);
	
					}
				}))
				{
					LayoutParams params = new LayoutParams(
							LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
					ProgressBar progressBar = new ProgressBar(getActivity(), null,
							android.R.attr.progressBarStyle);
					progressBar.setLayoutParams(params);
					item.setActionView(progressBar);
				}
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		Log.d(toString(), "Fragment view created");
		rootView = (ViewGroup) inflater.inflate(selectLayout(), container, false);
		fontApplicator.applyFont(rootView);
		fillViewContents();
	
		setupActionBar(activity.getSupportActionBar());
		return rootView;
	}
	
	@Override
	public void onDestroyView()
	{
		Log.d(toString(), "Fragment view destroyed");
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