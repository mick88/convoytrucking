package com.mick88.util;


public class TimeConverter
{
	public static CharSequence breakDownSeconds(int seconds)
	{
		int days = (seconds / 60 / 60 / 24),
				hours = (seconds / 60 / 60) % 24,
				min = (seconds / 60) % 60;
		
		StringBuilder result = new StringBuilder();
		if (days > 0) result.append(days).append("d");
		if (days < 3 && hours > 0) result.append(' ')
			.append(hours)
			.append('h');
		
		if (hours <= 1 && days == 0)
		{
			result.append(' ').append(min).append('m');
		}
		
		return result;
	}
}
