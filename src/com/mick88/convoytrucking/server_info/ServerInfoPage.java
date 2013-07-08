package com.mick88.convoytrucking.server_info;

import org.json.JSONException;

import android.os.Bundle;
import android.view.View;

import com.actionbarsherlock.app.ActionBar;
import com.mick88.convoytrucking.R;
import com.mick88.convoytrucking.api.ApiConnection;
import com.mick88.convoytrucking.api.ApiRequest;
import com.mick88.convoytrucking.base.BasePageFragment;
import com.mick88.convoytrucking.cards.DataCardFragment;

public class ServerInfoPage extends BasePageFragment<ServerInfoEntity>
{
	DataCardFragment<ServerInfoEntity> serverInfoCard;
	
	@Override
	public void onCreate(Bundle arg0)
	{
		super.onCreate(arg0);
		serverInfoCard = new ServerInfoCard();
	}
	
	@Override
	protected int selectLayout()
	{
		return R.layout.page_server_info;
	}
	
	@Override
	public BasePageFragment<ServerInfoEntity> setEntity(ServerInfoEntity entity)
	{
		
		if (serverInfoCard != null)
			serverInfoCard.setEntity(entity);
		return super.setEntity(entity);
	}
	
	@Override
	protected ServerInfoEntity createEntity(String json) throws JSONException
	{
		return new ServerInfoEntity(json);
	}
	
	@Override
	protected ApiRequest createRequest()
	{
		return super.createRequest().setShow(ApiConnection.SHOW_SERVER_INFO);
	}
	
	@Override
	public String getWebsiteUrl()
	{
		return "http://convoytrucking.net/index.php?show=info";
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		
		getFragmentManager().beginTransaction()
				.replace(R.id.frame_server_info, this.serverInfoCard).commit();
		
		if (entity != null)
		{
			serverInfoCard.setEntity(entity);
		}
	}
	
	@Override
	public void setupActionBar(ActionBar actionBar)
	{
		super.setupActionBar(actionBar);
		actionBar.setTitle(R.string.title_server_info);
	}
	
}
