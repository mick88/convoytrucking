package com.mick88.convoytrucking.server_info;

import java.util.ArrayList;

import org.json.JSONException;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.actionbarsherlock.app.ActionBar;
import com.mick88.convoytrucking.ConvoyTruckingApp;
import com.mick88.convoytrucking.R;
import com.mick88.convoytrucking.api.ApiConnection;
import com.mick88.convoytrucking.api.ApiRequest;
import com.mick88.convoytrucking.base.BaseFragment;
import com.mick88.convoytrucking.base.BasePageFragment;
import com.mick88.convoytrucking.cards.DataCardFragment;
import com.mick88.convoytrucking.twitter.Tweet;
import com.mick88.convoytrucking.twitter.TweetAdapter;
import com.mick88.convoytrucking.twitter.Twitter;
import com.mick88.convoytrucking.twitter.Twitter.OnTweetsFetchedListener;
import com.mick88.util.FontApplicator;

public class ServerInfoPage extends BasePageFragment<ServerInfoEntity>
{
	private static final String EXTRA_TWEETS = "tweets";
	DataCardFragment<ServerInfoEntity> serverInfoCard;
	ListView listTwitter;
	ArrayList<Tweet> tweets=null;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		serverInfoCard = new ServerInfoCard();
		if (savedInstanceState != null) setTweets((ArrayList<Tweet>) savedInstanceState.getSerializable(EXTRA_TWEETS));
	}
	
	public void setTweets(ArrayList<Tweet> tweets)
	{
		if (tweets != null)
		{
			this.tweets = tweets;
		}
	}
	
	@Override
	protected int selectLayout()
	{
		return R.layout.page_server_info;
	}
	
	@Override
	public BasePageFragment<ServerInfoEntity> setEntity(ServerInfoEntity entity)
	{
		
		if (serverInfoCard != null)
			serverInfoCard.setEntity(entity);
		return super.setEntity(entity);
	}
	
	@Override
	protected ServerInfoEntity createEntity(String json) throws JSONException
	{
		return new ServerInfoEntity(json);
	}
	
	@Override
	protected ApiRequest createRequest()
	{
		return super.createRequest().setShow(ApiConnection.SHOW_SERVER_INFO);
	}
	
	@Override
	public String getWebsiteUrl()
	{
		return "http://convoytrucking.net/index.php?show=info";
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		if (tweets != null)
		{
			try
			{
				outState.putSerializable(EXTRA_TWEETS, tweets);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		
		this.listTwitter = (ListView) findViewById(R.id.listView);
		View infoView = activity.getLayoutInflater().inflate(R.layout.server_info_header, listTwitter, false);
		final FontApplicator fontApplicator = new FontApplicator(activity.getAssets(), ConvoyTruckingApp.FONT_ROBOTO_LIGHT).applyFont(infoView);
		listTwitter.addHeaderView(infoView);
		listTwitter.setAdapter(new ArrayAdapter<Tweet>(activity, android.R.layout.simple_list_item_1, new Tweet[]{}));
		
		if (tweets != null ) listTwitter.setAdapter(new TweetAdapter(activity, tweets, fontApplicator));
		else 
		{
			final ProgressBar tweetProgressBar = (ProgressBar) findViewById(R.id.progressTweets);
			tweetProgressBar.setVisibility(View.VISIBLE);
			new Twitter(ConvoyTruckingApp.TWITTER_FEED_URL).fetchFeedAsync(new OnTweetsFetchedListener()			
			{
				
				@Override
				public void onTweetsFetched(ArrayList<Tweet> tweets)
				{
					ServerInfoPage.this.tweets = tweets;
					listTwitter.setAdapter(new TweetAdapter(activity, tweets, fontApplicator));
					tweetProgressBar.setVisibility(View.GONE);
				}
			});
		}
	
		getFragmentManager().beginTransaction()
				.replace(R.id.frame_server_info, serverInfoCard).commit();
		
		if (entity != null)
		{
			serverInfoCard.setEntity(entity);
		}
	}
	
	@Override
	public void setupActionBar(ActionBar actionBar)
	{
		super.setupActionBar(actionBar);
		actionBar.setTitle(R.string.title_server_info);
	}
	
}
