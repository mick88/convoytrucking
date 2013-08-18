package com.mick88.convoytrucking.forum.rss;

import org.xml.sax.XMLReader;

import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Html.TagHandler;
import android.text.Spanned;


public class RssItem implements ImageGetter, TagHandler
{
	private String title, link, date;
	Spanned content;
	
	public void setTitle(String title)
	{
		this.title = title.trim();
	}
	
	public void setLink(String link)
	{
		this.link = link.trim();
	}
	
	public void setDate(String date)
	{
		this.date = date.trim();
	}
	
	public void setContent(String desciption)
	{
		this.content = Html.fromHtml(desciption.trim(), this, this);
	}
	
	public Spanned getContent()
	{
		return content;
	}
	
	public String getDate()
	{
		return date;
	}
	
	public String getLink()
	{
		return link;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	@Override
	public String toString()
	{
		return getTitle();
	}

	@Override
	public void handleTag(boolean opening, String tag, Editable output,
			XMLReader xmlReader)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Drawable getDrawable(String source)
	{
		return null;
	}
}
