package com.mick88.convoytrucking.forum;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxHandler extends DefaultHandler
{
	private static final String PUB_DATE = "pubDate",
			DESCRIPTION = "description",
    		LINK = "link",
    		TITLE = "title",
    		ITEM = "item";
	
	private StringBuilder stringBuilder;
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException
	{
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException
	{
	};

	@Override
	public void startDocument() throws SAXException
	{
		// TODO Auto-generated method stub
		super.startDocument();
	}

	public void startElement(String uri, String localName, String qName,
			org.xml.sax.Attributes attributes) throws SAXException
	{
	};
}
