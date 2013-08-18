package com.mick88.convoytrucking;

import android.app.Application;

import com.mick88.convoytrucking.api.ApiConnection;
import com.mick88.convoytrucking.base.BaseFragment;
import com.mick88.convoytrucking.forum.ForumRssPage;
import com.mick88.convoytrucking.houses.HouseListPage;
import com.mick88.convoytrucking.navigation.AppMenuItem;
import com.mick88.convoytrucking.scoretable.ScoretablePage;
import com.mick88.convoytrucking.server_info.ServerInfoPage;
import com.mick88.convoytrucking.vehicles.VehicleListPage;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ConvoyTruckingApp extends Application
{

	private final String apiKey = "25598957d53b91fc9dccdddd860b199a";
	public static final String TWITTER_FEED_URL = "http://mobile.twitter.com/ConvoyTrucking";
	
	public static final String 
		FONT_ROBOTO_LIGHT = "fonts/Roboto_Light.ttf",
		FONT_ROBOTO_THIN = "fonts/Roboto_Thin.ttf",
		FONT_ROBOTO_REGULAR = "fonts/Roboto_Regular.ttf";
	
	
	protected ApiConnection apiConnection;
	private AppMenuItem[] menuItems;

	public String getApiKey()
	{
		return apiKey;
	}

	public ApiConnection getApiConnection()
	{
		return apiConnection;
	}
	
	public static DisplayImageOptions.Builder getDefaultDisplayImageOptions()
	{
		return new DisplayImageOptions.Builder()
		.cacheInMemory()
		.cacheOnDisc();
	}

	@Override
	public void onCreate()
	{
		super.onCreate();
		
		ImageLoaderConfiguration configuration =
		new ImageLoaderConfiguration.Builder(getApplicationContext())
			.defaultDisplayImageOptions(
					getDefaultDisplayImageOptions().build())
			.build();
			
		
		ImageLoader.getInstance().init(configuration);
		
		
		apiConnection = new ApiConnection(apiKey);
		menuItems = new AppMenuItem[]
		{
			new AppMenuItem(R.string.title_server_info, R.drawable.ic_nav_server_info){

				@Override
				public BaseFragment getPage()
				{
					return new ServerInfoPage();
				}
				
			},
			new AppMenuItem(R.string.title_scoretable, R.drawable.ic_nav_scoretable)
			{

				@Override
				public BaseFragment getPage()
				{
					return new ScoretablePage();
				}
				
			},
			new AppMenuItem(R.string.title_houses, R.drawable.ic_nav_house)
			{

				@Override
				public BaseFragment getPage()
				{
					return new HouseListPage();
				}
				
			},
			new AppMenuItem(R.string.title_vehicles, R.drawable.ic_nav_vehicles)
			{

				@Override
				public BaseFragment getPage()
				{
					return new VehicleListPage();
				}
				
			},
			/*new AppMenuItem(R.string.title_forum_posts, R.drawable.ic_nav_rss)
			{
				
				@Override
				public BaseFragment getPage()
				{
					return new ForumRssPage();
				}
			},*/
		};		
		
		
	}
	
	public AppMenuItem[] getMenuItems()
	{
		return menuItems;
	}
}
