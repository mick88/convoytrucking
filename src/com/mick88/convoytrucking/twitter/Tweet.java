package com.mick88.convoytrucking.twitter;

import java.io.Serializable;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.text.Html;
import android.text.Spanned;
import android.util.Log;

public class Tweet implements Serializable
{
	private static final long serialVersionUID = 3520630151777599673L;
	private final String content, 
		author, 
		time,
		imgUrl;
	private transient Spanned spannedContent;
	private static final String twitter_url = "https://mobile.twitter.com";
	
	public Tweet(String content, String author, String time, String imgUrl)
	{
		this.content = content;
		this.author = author;
		this.time = time;
		this.imgUrl = imgUrl;
		this.spannedContent = Html.fromHtml(content);
	}
	
	/**
	 * parses html and returns a tweet
	 */
	public static Tweet fromHtml(Element html)
	{
		String imgUrl = html.select("img").get(0).attr("src");
		String author = html.select(".fullname").get(0).html();
		Element elementContent = html.select("div.tweet-text").get(0).select("div").get(0);
		Elements links = elementContent.select("a");

		for (Element link : links)
		{
			String url = link.attr("href");
			if (url.startsWith("http") == false)
			{
				// TODO: open in twitter client if installed
				link.attr("href", twitter_url+url);
				Log.d("Tweet new url", twitter_url+url);
			}
			else
			{
				String data = link.attr("data-url");
				if (data.startsWith("http"))
				{
					link.attr("href", data);
				}
			}
		}
		String content = elementContent.html();
		String time = html.select("td.timestamp").get(0).select("a").get(0).html();
		
		return new Tweet(content, author, time, imgUrl);
	}
	
	public String getAuthor()
	{
		return author;
	}
	
	public String getContent()
	{
		return content;
	}
	
	public String getImgUrl()
	{
		return imgUrl;
	}
	
	public String getTime()
	{
		return time;
	}
	
	@Override
	public String toString()
	{
		return getContent();
	}
	
	/**
	 * gets content as html
	 * @return
	 */
	public Spanned getSpannedContent()
	{
		if (spannedContent == null) spannedContent = Html.fromHtml(content);
		return spannedContent;
	}
}
