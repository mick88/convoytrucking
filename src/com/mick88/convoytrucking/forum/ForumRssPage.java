package com.mick88.convoytrucking.forum;

import android.os.Bundle;

import com.mick88.convoytrucking.R;
import com.mick88.convoytrucking.base.BaseFragment;
import com.mick88.convoytrucking.interfaces.OnDownloadListener;
import com.mick88.convoytrucking.interfaces.RefreshListener;

public class ForumRssPage extends BaseFragment
{
	@Override
	public void onCreate(Bundle arg0)
	{
		super.onCreate(arg0);
	}

	@Override
	protected int selectLayout()
	{
		return R.layout.list;
	}

	@Override
	public boolean refresh(RefreshListener listener)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void downloadData(OnDownloadListener listener)
	{
		// TODO Auto-generated method stub
		
	}

}
