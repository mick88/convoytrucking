package com.mick88.convoytrucking.twitter;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.AsyncTask;

/**
 * Fetches and parses twitter feed
 */
public class Twitter
{
	public interface OnTweetsFetchedListener
	{
		void onTweetsFetched(ArrayList<Tweet> tweets);
	}

	final String url;

	public Twitter(String feed_url) {
		this.url = feed_url;
	}

	public ArrayList<Tweet> fetchFeed() throws IOException
	{
		Document doc = Jsoup
				.connect(url)
				.userAgent(
						"Mozilla/5.0 (Linux; U; Android 2.0; en-us; Droid Build/ESD20) AppleWebKit/530.17 (KHTML, like Gecko) Version/4.0 Mobile Safari/530.17")
				.get();
		Elements elements = doc.select("table.tweet");

		ArrayList<Tweet> tweets = new ArrayList<Tweet>(elements.size());
		for (Element element : elements)
		{
			Tweet tweet = Tweet.fromHtml(element);
			tweets.add(tweet);
		}

		return tweets;
	}

	public void fetchFeedAsync(
			final OnTweetsFetchedListener onTweetsFetchedListener)
	{
		new AsyncTask<Void, Void, ArrayList<Tweet>>()
		{

			@Override
			protected ArrayList<Tweet> doInBackground(Void... params)
			{
				try
				{
					return fetchFeed();
				} catch (IOException e)
				{
					e.printStackTrace();
					return null;
				}
			}

			protected void onPostExecute(ArrayList<Tweet> result)
			{
				onTweetsFetchedListener.onTweetsFetched(result);
			};
		}.execute();
	}
}
