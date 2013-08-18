package com.mick88.convoytrucking.forum.rss;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class RssReader
{
	final URL url;
	
	public RssReader(String url) throws MalformedURLException
	{
		this.url = new URL(url);
	}
	
	public List<RssItem> read() throws IOException, ParserConfigurationException, SAXException
	{
		InputStream inputStream = url.openConnection().getInputStream();
		SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
		SaxHandler handler = new SaxHandler();
		saxParser.parse(inputStream, handler);		
		return handler.getRssItems();
	}
}
