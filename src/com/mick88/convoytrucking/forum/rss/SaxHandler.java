package com.mick88.convoytrucking.forum.rss;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxHandler extends DefaultHandler
{
	private static final String PUB_DATE = "pubDate",
			DESCRIPTION = "description",
    		LINK = "link",
    		TITLE = "title",
    		ITEM = "item";
	
	private StringBuilder stringBuilder=null;
	private RssItem currentItem=null;
	List<RssItem> rssItems=null;
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException
	{
		super.characters(ch, start, length);
		stringBuilder.append(ch, start, length);
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException
	{
		super.endElement(uri, localName, qName);
		if (currentItem != null)
		{
			if (localName.equalsIgnoreCase(TITLE))
				currentItem.setTitle(stringBuilder.toString());
			else if (localName.equalsIgnoreCase(DESCRIPTION))
				currentItem.setContent(stringBuilder.toString());
			else if (localName.equalsIgnoreCase(LINK))
				currentItem.setLink(stringBuilder.toString());
			else if (localName.equalsIgnoreCase(PUB_DATE))
				currentItem.setDate(stringBuilder.toString());
			else if (localName.equalsIgnoreCase(ITEM))
				rssItems.add(currentItem);
		}
		stringBuilder.setLength(0);
	}

	@Override
	public void startDocument() throws SAXException
	{
		super.startDocument();
		rssItems = new ArrayList<RssItem>();
		stringBuilder = new StringBuilder();
	}

	public void startElement(String uri, String localName, String qName,
			org.xml.sax.Attributes attributes) throws SAXException
	{
		super.startElement(uri, localName, qName, attributes);
		
		if (localName.equalsIgnoreCase(ITEM))
		{
			this.currentItem = new RssItem();
		}
	}
	
	public List<RssItem> getRssItems()
	{
		return rssItems;
	}
}
