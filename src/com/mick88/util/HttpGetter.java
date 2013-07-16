package com.mick88.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Gets String from via http
 * @author Michal
 *
 */
public class HttpGetter
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
}
