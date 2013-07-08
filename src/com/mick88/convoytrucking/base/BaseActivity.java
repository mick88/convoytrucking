package com.mick88.convoytrucking.base;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewCompatJB;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.mick88.convoytrucking.ConvoyTruckingApp;
import com.mick88.convoytrucking.R;
import com.mick88.convoytrucking.cards.CardFragment;
import com.mick88.convoytrucking.cards.DownloadableDataCardFragment;
import com.mick88.convoytrucking.interfaces.OnDownloadListener;
import com.mick88.convoytrucking.interfaces.RefreshListener;
import com.mick88.convoytrucking.navigation.AppMenuItem;
import com.mick88.convoytrucking.navigation.MenuAdapter;
import com.mick88.util.FontApplicator;

/**
 * Base activity class for the app. Handles Navigation drawer and layout loading
 * 
 * @author Michal
 * 
 */
public abstract class BaseActivity extends SherlockFragmentActivity
{
	ActionBarDrawerToggle actionBarDrawerToggle;
	DrawerLayout drawerLayout=null;
	protected ConvoyTruckingApp application;
	protected List<CardFragment> cards = new ArrayList<CardFragment>();;

	protected abstract int selectLayout();

	@Override
	protected void onCreate(android.os.Bundle arg0)
	{
		super.onCreate(arg0);
		this.application = (ConvoyTruckingApp) getApplication();
		
		setContentView(R.layout.nav_drawer);

		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		int selectedLayoutId = selectLayout();
		if (selectedLayoutId != 0)
		{
			getLayoutInflater().inflate(selectedLayoutId,
					(ViewGroup) findViewById(R.id.content_root));
		}

		applyRobotoFont(drawerLayout);
		setupNavDrawer(drawerLayout);
	}

	/**
	 * Populates navigation drawer
	 */
	protected void setupNavDrawer(final DrawerLayout drawerLayout)
	{
		final ListView drawerList = (ListView) findViewById(R.id.nav_drawer);

		drawerList.setAdapter(new MenuAdapter(this, application.getMenuItems()));

		actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.ic_drawer, R.string.open_drawer,
				R.string.close_drawer);

		drawerLayout.setDrawerListener(actionBarDrawerToggle);
		drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		drawerList.setOnItemClickListener(new ListView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3)
			{
				onDrawerItemSelected((AppMenuItem) arg0.getItemAtPosition(arg2));
				drawerLayout.closeDrawers();
			}
		});
	}

	/**
	 * Super implementation executes the intent
	 * @param item Pressed item
	 */
	protected abstract void onDrawerItemSelected(AppMenuItem item);

	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);
		actionBarDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		actionBarDrawerToggle.onConfigurationChanged(newConfig);
	}

	
	protected void applyRobotoFont(View view)
	{
		new FontApplicator(getAssets(), ConvoyTruckingApp.FONT_ROBOTO_LIGHT).applyFont(view);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		/*if (actionBarDrawerToggle.onOptionsItemSelected(item))
			return true;*/
		if (item.getItemId() == android.R.id.home)
		{
			if (drawerLayout.isDrawerOpen(GravityCompat.START))
			{
				drawerLayout.closeDrawer(GravityCompat.START);
			}
			else
			{
				drawerLayout.openDrawer(GravityCompat.START);
			}
//			actionBarDrawerToggle.
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		int menuId = R.menu.main;
		MenuInflater menuInflater = getSupportMenuInflater();
		menuInflater.inflate(menuId, menu);
		return true;
	}

	/**
	 * Refreshes data. Base implementation refreshes a single card in
	 * card_fragment layout.
	 * 
	 * @return true if refresh has started
	 */
	protected boolean refresh(final RefreshListener refreshListener)
	{
		Fragment fragment = this.getSupportFragmentManager().findFragmentById(
				R.id.card_fragment);
		if (fragment instanceof DownloadableDataCardFragment)
		{
			if (((DownloadableDataCardFragment<?>) fragment)
					.downloadData(new OnDownloadListener()
					{

						@Override
						public void onDownloadFinished()
						{
							if (refreshListener != null)
								refreshListener.onRefreshFinished();
						}
					}))
				return true;
		}
		return false;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, final MenuItem item)
	{
		switch (item.getItemId())
		{
/*		case R.id.action_refresh:

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
				ProgressBar progressBar = new ProgressBar(this, null,
						android.R.attr.progressBarStyle);
				progressBar.setLayoutParams(params);
				item.setActionView(progressBar);
			}
			break;*/
		}
		return super.onMenuItemSelected(featureId, item);
	}

	public void onCardAttached(CardFragment card)
	{
		cards.add(card);
	}

	public void onCardRemoved(CardFragment card)
	{
		cards.remove(card);
	}

}
