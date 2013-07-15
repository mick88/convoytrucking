package com.mick88.convoytrucking.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.mick88.convoytrucking.R;
import com.mick88.convoytrucking.base.BaseActivity;
import com.mick88.convoytrucking.base.BasePageFragment;
import com.mick88.convoytrucking.houses.HouseListPage;
import com.mick88.convoytrucking.interfaces.RefreshListener;
import com.mick88.convoytrucking.navigation.AppMenuItem;
import com.mick88.convoytrucking.scoretable.ScoretablePage;
import com.mick88.convoytrucking.server_info.ServerInfoPage;
import com.mick88.convoytrucking.vehicles.VehicleListPage;

public final class MainActivity extends BaseActivity
{
	
	@Override
	protected int selectLayout()
	{
		return R.layout.activity_main;
	}
	
	@Override
	protected void onCreate(Bundle arg0)
	{
		super.onCreate(arg0);
		
		if (getCurrentPage() == null)
		{
			openPage(new ServerInfoPage());
		}
	}
	
	/**
	 * Gets currently displayed page fragment
	 * @return
	 */
	public BasePageFragment<?> getCurrentPage()
	{
		Fragment fragment  = getSupportFragmentManager().findFragmentById(R.id.fragment);
		if (fragment instanceof BasePageFragment<?>)
		{
			 return (BasePageFragment<?>) fragment;
		}
		return null;
	}
	
	@Override
	protected boolean refresh(RefreshListener refreshListener)
	{
		if (getCurrentPage().refresh(refreshListener)) return true;
		return super.refresh(refreshListener);
	}
	
	/**
	 * Opens page without adding it to the back stack
	 */
	public void openPage(BasePageFragment<?> page)
	{		
		openPage(page, false);
	}
	
	/**
	 * Shows page fragment
	 * @param page PageFragement implementation
	 * @param addBackStack true to enable backbutton
	 */
	public void openPage(BasePageFragment<?> page, boolean addBackStack)
	{
		FragmentTransaction transaction = getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.fragment, page);
		
		if (addBackStack) transaction.addToBackStack(null);
		else getSupportFragmentManager().popBackStack();
		transaction.commit();
	}
	
	@Override
	protected void onDrawerItemSelected(AppMenuItem item)
	{
		openPage(item.getPage());
	}

}
