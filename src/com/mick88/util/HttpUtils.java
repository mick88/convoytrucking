package com.mick88.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xml.sax.XMLReader;

import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Html.ImageGetter;
import android.text.Html.TagHandler;

/**
 * Gets String from via http
 * @author Michal
 *
 */
public class HttpUtils implements ImageGetter, TagHandler
{
	public static String getString(String url) throws IOException
	{		
		HttpGet get = new HttpGet(url);
		DefaultHttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(get);
		InputStream stream = response.getEntity().getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		
		StringBuilder builder = new StringBuilder();
		
		String line;
		while ((line = reader.readLine()) != null)
		{
			builder.append(line);
		}
		
		reader.close();
		stream.close();
		response.getEntity().consumeContent();
				
		return builder.toString();
	}
	
	public static Drawable getImage(String url)
	{
		return null;
	}

	@Override
	public Drawable getDrawable(String source)
	{
		return getImage(source);
	}

	@Override
	public void handleTag(boolean opening, String tag, Editable output,
			XMLReader xmlReader)
	{
		
	}
}
