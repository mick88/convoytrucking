package com.mick88.util;

import java.util.Locale;

/**
 * Converts miles and kilometres from metres
 * @author Michal
 *
 */
public class Distance
{
	enum DistanceUnit
	{
		Metre,
		Foot,
		Kilometre,
		Mile,
	}
	
	final static float MILE_RATIO = 1f / 1609.344f,
			KM_RATIO = 1f / 1000f,
			FOOT_RATIO = 3.2808399f;
	
	final static String MILE_POSTFIX = "miles",
			KM_POSTFIX = "km";
	
	final float metres;	
	
	public Distance(float metres)
	{
		this.metres = metres;
	}
	
	public Distance(int metres)
	{
		this.metres = metres;
	}
	
	public float getDistance(DistanceUnit units)
	{
		switch (units)
		{
			case Kilometre:
				return metres * KM_RATIO;
			case Foot:
				return metres * FOOT_RATIO;
			case Metre:
				return metres;
			case Mile:
				return metres * MILE_RATIO;			
			default:
				return 0f;
		}
	}
	
	public String getUnitPostfix(DistanceUnit units)
	{
		switch (units)
		{
			case Kilometre:
				return KM_POSTFIX;
			case Foot:
				return "feet";
			case Metre:
				return "m";
			case Mile:
				return MILE_POSTFIX;			
			default:
				return "";
		}
	}
	
	public int getDistanceInt(DistanceUnit unit)
	{
		return (int) getDistance(unit);
	}
	
	public float toKm()
	{
		return getDistance(DistanceUnit.Kilometre);
	}
	
	public int toKmInt()
	{
		return getDistanceInt(DistanceUnit.Kilometre);
	}
	
	public float toMiles()
	{
		return getDistance(DistanceUnit.Mile);
	}
	
	public float toMilesInt()
	{
		return getDistanceInt(DistanceUnit.Mile);
	}
	
	public String toKmString()
	{
		return String.format(Locale.getDefault(), "%d %s", toKmInt(), KM_POSTFIX);
	}
	
	public static DistanceUnit getPreferredUnits()
	{
		// TODO: implement properly
		Locale locale = Locale.getDefault();
		if (locale.equals(Locale.ENGLISH)) return DistanceUnit.Mile;
		else return DistanceUnit.Kilometre;
	}
}
