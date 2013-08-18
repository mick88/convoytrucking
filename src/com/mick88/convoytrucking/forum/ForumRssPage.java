package com.mick88.convoytrucking.forum;

import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.mick88.convoytrucking.ConvoyTruckingApp;
import com.mick88.convoytrucking.R;
import com.mick88.convoytrucking.base.BaseFragment;
import com.mick88.convoytrucking.forum.rss.RssItem;
import com.mick88.convoytrucking.forum.rss.RssPostAdapter;
import com.mick88.convoytrucking.forum.rss.RssReader;
import com.mick88.convoytrucking.interfaces.OnDownloadListener;
import com.mick88.convoytrucking.interfaces.RefreshListener;
import com.mick88.util.FontApplicator;
import com.mick88.util.HttpUtils;

public class ForumRssPage extends BaseFragment implements OnItemClickListener
{
	private static final String RSS_FEED_URL = "http://www.forum.convoytrucking.net/index.php?action=.xml;type=rss";
	private AsyncTask<?, ?, ?> downloadTask=null;
	private ListView listView;
	
	@Override
	public void onCreate(Bundle arg0)
	{
		super.onCreate(arg0);
		downloadData(null);
	}

	@Override
	protected int selectLayout()
	{
		return R.layout.list;
	}

	@Override
	public boolean refresh(final RefreshListener listener)
	{
		if (downloadTask != null) return false;
		downloadData(new OnDownloadListener()
		{
			
			@Override
			public void onDownloadFinished()
			{
				listener.onRefreshFinished();
			}
		});
		return true;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		listView = (ListView) findViewById(R.id.listView);
		listView.setOnItemClickListener(this);
	}

	@Override
	protected void downloadData(final OnDownloadListener listener)
	{
		downloadTask = new AsyncTask<Void, Void, List<RssItem>>()
		{

			@Override
			protected List<RssItem> doInBackground(Void... params)
			{
				
				try
				{
					return new RssReader(RSS_FEED_URL).read();
				} 
				catch (Exception e)
				{
					e.printStackTrace();
					return null;
				}
			}
			
			protected void onPostExecute(List<RssItem> result) {
				downloadTask = null;
				if (result == null) return;
				
				HttpUtils webHandler = new HttpUtils();
				
				RssPostAdapter adapter = new RssPostAdapter(activity, result, new FontApplicator(activity.getAssets(), ConvoyTruckingApp.FONT_ROBOTO_LIGHT), webHandler, webHandler);
				
				listView.setAdapter(adapter);
				
				if (listener != null) listener.onDownloadFinished();
			};
			
			protected void onCancelled() {
				downloadTask = null;
			};
		}.execute();
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3)
	{
		RssItem rssItem = (RssItem) adapterView.getItemAtPosition(arg2);
		String link = rssItem.getLink();
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(link));
		startActivity(intent);
	}

}
