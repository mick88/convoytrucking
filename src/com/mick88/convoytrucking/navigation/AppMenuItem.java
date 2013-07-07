package com.mick88.convoytrucking.navigation;


/**
 * Represents menu item in navigation drawer
 * @author Michal
 *
 */
public class AppMenuItem
{
	public enum Page
	{
		ServerInfo,
		Scoretable,
		Houses,
		Vehicles,
	}
	
	private final int iconId, labelId;
	private final Page page;
	
	public AppMenuItem(int labelId, Page page, int icon)
	{
		this.labelId = labelId;
		this.page = page;
		this.iconId = icon;
	}
	
	public Page getPage()
	{
		return page;
	}
	
	/**
	 * Gets string resource id for label
	 */
	public int getLabelId()
	{
		return labelId;
	}
	
	public int getIconId()
	{
		return iconId;
	}
}
