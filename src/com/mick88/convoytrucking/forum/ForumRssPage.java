package com.mick88.convoytrucking.forum;

import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mick88.convoytrucking.R;
import com.mick88.convoytrucking.base.BaseFragment;
import com.mick88.convoytrucking.forum.rss.RssItem;
import com.mick88.convoytrucking.forum.rss.RssReader;
import com.mick88.convoytrucking.interfaces.OnDownloadListener;
import com.mick88.convoytrucking.interfaces.RefreshListener;

public class ForumRssPage extends BaseFragment
{
	private static final String RSS_FEED_URL = "http://www.forum.convoytrucking.net/index.php?action=.xml;type=rss";
	
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
	public boolean refresh(RefreshListener listener)
	{
		// TODO Auto-generated method stub
		return false;
	}	

	@Override
	protected void downloadData(OnDownloadListener listener)
	{
		new AsyncTask<Void, Void, List<RssItem>>()
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
				ListView listView = (ListView) findViewById(R.id.listView);
				listView.setAdapter(new ArrayAdapter<RssItem>(activity, android.R.layout.simple_list_item_1, android.R.id.text1, result));
			};
		}.execute();
	}

}
