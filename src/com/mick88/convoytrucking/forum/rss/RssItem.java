package com.mick88.convoytrucking.forum.rss;

public class RssItem
{
	private String title, link, desciption, date;
	
	public void setTitle(String title)
	{
		this.title = title.trim();
	}
	
	public void setLink(String link)
	{
		this.link = link;
	}
	
	public void setDate(String date)
	{
		this.date = date;
	}
	
	public void setDesciption(String desciption)
	{
		this.desciption = desciption.trim();
	}
	
	public String getDate()
	{
		return date;
	}
	
	public String getDesciption()
	{
		return desciption;
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
}
