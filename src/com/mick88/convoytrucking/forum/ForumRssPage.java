package com.mick88.convoytrucking.forum;

import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mick88.convoytrucking.R;
import com.mick88.convoytrucking.base.BaseFragment;
import com.mick88.convoytrucking.forum.rss.RssItem;
import com.mick88.convoytrucking.forum.rss.RssPostAdapter;
import com.mick88.convoytrucking.forum.rss.RssReader;
import com.mick88.convoytrucking.interfaces.OnDownloadListener;
import com.mick88.convoytrucking.interfaces.RefreshListener;

public class ForumRssPage extends BaseFragment
{
	private static final String RSS_FEED_URL = "http://www.forum.convoytrucking.net/index.php?action=.xml;type=rss";
	AsyncTask<?, ?, ?> downloadTask=null;
	
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
		if (downloadTask == null) return false;
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
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			}
			
			protected void onPostExecute(List<RssItem> result) {
				downloadTask = null;
				ListView listView = (ListView) findViewById(R.id.listView);
				listView.setAdapter(new RssPostAdapter(activity, result));
				if (listener != null) listener.onDownloadFinished();
			};
			
			protected void onCancelled() {
				downloadTask = null;
			};
		}.execute();
	}

}
