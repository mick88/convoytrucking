package com.mick88.convoytrucking.forum;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.mick88.convoytrucking.R;
import com.mick88.convoytrucking.base.BaseFragment;
import com.mick88.convoytrucking.interfaces.OnDownloadListener;
import com.mick88.convoytrucking.interfaces.RefreshListener;
import com.mick88.util.HttpGetter;

public class ForumRssPage extends BaseFragment implements OnDownloadListener
{
	private static final String RSS_FEED_URL = "http://www.forum.convoytrucking.net/index.php?action=.xml;type=rss";
	
	@Override
	public void onCreate(Bundle arg0)
	{
		super.onCreate(arg0);
		downloadData(this);
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
	
	private void downloadAsync() throws IOException, ParserConfigurationException, SAXException
	{
		InputStream inputStream = new URL(RSS_FEED_URL).openConnection().getInputStream();
		SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
		SaxHandler handler = new SaxHandler();
		saxParser.parse(inputStream, handler);				
	}

	@Override
	protected void downloadData(final OnDownloadListener listener)
	{
		new AsyncTask<Void, Void, String>()
		{

			@Override
			protected String doInBackground(Void... params)
			{
				
				try
				{
					downloadAsync();
					return HttpGetter.getString(RSS_FEED_URL);
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			}
			
			protected void onPostExecute(String result) {
				listener.onDownloadFinished();
			};
		}.execute();
	}

	@Override
	public void onDownloadFinished()
	{
		// TODO Auto-generated method stub
		
	}

}
