package com.mick88.convoytrucking.navigation;

import com.mick88.convoytrucking.base.BaseFragment;


/**
 * Represents menu item in navigation drawer
 * @author Michal
 *
 */
public abstract class AppMenuItem
{	
	private final int iconId, labelId;
	
	public AppMenuItem(int labelId, int icon)
	{
		this.labelId = labelId;
		this.iconId = icon;
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
	
	public abstract BaseFragment getPage();
}
