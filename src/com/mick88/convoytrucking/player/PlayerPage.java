package com.mick88.convoytrucking.player;

import org.json.JSONArray;
import org.json.JSONException;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.actionbarsherlock.app.ActionBar;
import com.mick88.convoytrucking.R;
import com.mick88.convoytrucking.api.ApiConnection;
import com.mick88.convoytrucking.api.ApiRequest;
import com.mick88.convoytrucking.api.ApiRequest.ApiRequestListener;
import com.mick88.convoytrucking.base.BasePageFragment;
import com.mick88.convoytrucking.houses.HouseEntity;
import com.mick88.convoytrucking.houses.HouseListPage.Filter;
import com.mick88.convoytrucking.houses.detail.HouseInfoTile;
import com.mick88.convoytrucking.interfaces.OnDownloadListener;

public class PlayerPage extends BasePageFragment<PlayerEntity>
{
	public static final String EXTRA_PLAYER_ID = "player_id";
	// TODO: pass playerid in param
	int playerid=0;
	private PlayerInfoCard playerInfoCard;
	private PlayerStatsCard playerStatsCard;
	private HouseInfoTile houseInfoTile = null;
	HouseEntity houseEntity=null;
	
	@Override
	public void onCreate(Bundle arg0)
	{
		super.onCreate(arg0);

		application.getApiConnection();
		
		playerInfoCard = new PlayerInfoCard();
		playerStatsCard = new PlayerStatsCard();
	}
	
	@Override
	protected int selectLayout()
	{
		return R.layout.page_player;
	}
	
	@Override
	public BasePageFragment<PlayerEntity> setEntity(PlayerEntity entity)
	{
		if (playerInfoCard != null && playerStatsCard != null)
		{
			playerInfoCard.setEntity(entity);
			playerStatsCard.setEntity(entity);
		}
		
		super.setEntity(entity);
		
		setupActionBar(activity.getSupportActionBar());
		
		if (entity.getHouseId() != 0) downloadHouseData();
		
		return this;
	}
	
	protected void downloadHouseData()
	{
		Log.d(toString(), "Downloading house data");
		new ApiRequest(application.getApiConnection())
			.setShow(ApiConnection.SHOW_HOUSES)
			.setFilter(Filter.details)
			.setId(entity.getHouseId())
			.SendAsync(new ApiRequestListener()
			{
				
				@Override
				public void onRequestComplete(ApiRequest apiRequest)
				{
					Log.d(toString(), "House data downloaded");
					try
					{
						setHouseEntity(new HouseEntity(apiRequest.getResultJsonArray().getJSONObject(0)));
					} catch (JSONException e)
					{
						e.printStackTrace();
					}
				}
			});
	}
	
	public void setHouseEntity(HouseEntity houseEntity)
	{
		this.houseEntity = houseEntity;
		if (houseEntity != null && houseInfoTile != null)
		{
			houseInfoTile.setEntity(houseEntity);
		}
	}
	
	@Override
	protected void downloadData(OnDownloadListener listener)
	{
		if (playerid > 0) super.downloadData(listener);
	}
	
	@Override
	protected boolean fillViewContents()
	{
		if (super.fillViewContents())
		{
			if (entity.getHouseId()==0)
			{
				findViewById(R.id.layoutPlayerHouse).setVisibility(View.GONE);
			}
			else 
			{
				houseInfoTile = new HouseInfoTile();
				getChildFragmentManager().beginTransaction().replace(R.id.fragment_house, houseInfoTile).commit();
				findViewById(R.id.layoutPlayerHouse).setVisibility(View.VISIBLE);
				if (houseEntity != null)
				{
					setHouseEntity(houseEntity);
				}
			}
			return true;
		}
		else return false;
	}
	
	@Override
	protected ApiRequest createRequest()
	{
		return super.createRequest()
				.setShow(ApiConnection.SHOW_PLAYER)
				.setId(playerid);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);		

		getChildFragmentManager().beginTransaction()
			.replace(R.id.fragment_info, playerInfoCard)
			.replace(R.id.fragment_stats, playerStatsCard)
			.commit();
		
		if (entity != null)
		{
			playerInfoCard.setEntity(entity);
			playerStatsCard.setEntity(entity);
		}
	}

	@Override
	protected PlayerEntity createEntity(String json) throws JSONException
	{
		JSONArray jsonArray = new JSONArray(json);
		return new PlayerEntity(jsonArray.getJSONObject(0));
	}
	
	public PlayerPage setPlayerid(int playerid)
	{
		this.playerid = playerid;
		return this;
	}

	@Override
	public void setupActionBar(ActionBar actionBar)
	{
		super.setupActionBar(actionBar);
		if (entity != null)
		{
			actionBar.setTitle(entity.getName());
			actionBar.setSubtitle(entity.getRank());		
		}
		
	}
	
}
