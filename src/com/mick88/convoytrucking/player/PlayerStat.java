package com.mick88.convoytrucking.player;

import com.mick88.util.Distance;
import com.mick88.util.TimeConverter;

public class PlayerStat
{
	enum StatType {Standard, Distance, Time}
	
	final String key;
	final int value;
	final StatType statType;
	final boolean unlocked;
	
	public PlayerStat(String key, int value, StatType statType, boolean unlocked)
	{
		this.key = key;
		this.value = value;
		this.unlocked = unlocked;
		this.statType = statType;
	}
	
	public PlayerStat(String key, int value)
	{
		this(key, value, StatType.Standard, false);
	}
	
	public PlayerStat(String key, int value, StatType statType)
	{
		this(key, value, statType, false);
	}
	
	public String getKey()
	{
		return key;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public CharSequence getValueString()
	{
		switch (statType)
		{
			// TODO: convert distance and time
			case Distance:
				return new Distance(value).toKmString();
			case Time:
				return TimeConverter.breakDownSeconds(value);
			default:
				return new StringBuilder().append(value);
		}
		
	}
}
