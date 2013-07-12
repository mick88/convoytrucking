package com.mick88.convoytrucking.houses.detail;

import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;

import com.actionbarsherlock.app.ActionBar;
import com.mick88.convoytrucking.R;
import com.mick88.convoytrucking.api.ApiConnection;
import com.mick88.convoytrucking.api.ApiRequest;
import com.mick88.convoytrucking.api.ApiRequest.ApiRequestListener;
import com.mick88.convoytrucking.base.BasePageFragment;
import com.mick88.convoytrucking.houses.HouseEntity;
import com.mick88.convoytrucking.houses.HouseListPage.Filter;
import com.mick88.convoytrucking.player.PlayerEntity;
import com.mick88.convoytrucking.player.PlayerInfoCard;

public class HouseDetailsPage extends BasePageFragment<HouseEntity> implements OnClickListener
{
	HouseInfoTile infoTile;
	PlayerInfoCard ownerInfoCard=null;
	PlayerEntity playerEntity=null;
	
	@Override
	public void onCreate(Bundle arg0)
	{
		super.onCreate(arg0);
		infoTile = new HouseInfoTile();
		
		if (entity != null) 
		{
			infoTile.setEntity(entity);
			downloadData(null); // download further data
		}
		// TODO: download player data
		downloadOwnerData();
	}
	
	void setPlayerEntity(PlayerEntity player)
	{
		if (ownerInfoCard == null) ownerInfoCard = new PlayerInfoCard();
		ownerInfoCard.setEntity(player);
	}
	
	private void downloadOwnerData()
	{
		if (entity == null || entity.getOwnerId() == 0) return;
		
		new ApiRequest(application.getApiConnection())
			.setShow(ApiConnection.SHOW_PLAYER)
			.setId(entity.getOwnerId()).SendAsync(new ApiRequestListener()
			{
				
				@Override
				public void onRequestComplete(ApiRequest apiRequest)
				{
					try
					{
						JSONObject json = apiRequest.getResultJsonArray().getJSONObject(0);
						PlayerEntity player = new PlayerEntity(json);
						setPlayerEntity(player);
						
					} catch (JSONException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
	}
	
	@Override
	public void setupActionBar(ActionBar actionBar)
	{
		super.setupActionBar(actionBar);
		actionBar.setTitle("House details");
	}
	
	@Override
	protected int selectLayout()
	{
		return R.layout.page_house_details;
	}
	
	@Override
	protected ApiRequest createRequest()
	{
		return super.createRequest()				
				.setShow(ApiConnection.SHOW_HOUSES)
				.setFilter(Filter.details)
				.setId(entity.getId());
	}
	
	@Override
	public BasePageFragment<HouseEntity> setEntity(HouseEntity entity)
	{
		if (infoTile != null) infoTile.setEntity(entity);
		
		return super.setEntity(entity);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		
		FragmentTransaction transaction =	getChildFragmentManager()
			.beginTransaction()
			.replace(R.id.fragment_info, infoTile);
		
		if (entity != null && entity.getOwnerId() > 0)
		{
			ownerInfoCard = new PlayerInfoCard();
			transaction.replace(R.id.fragment_owner_info, ownerInfoCard);
			rootView.findViewById(R.id.layoutHouseOwner).setVisibility(View.VISIBLE);
			
//			rootView.findViewById(R.id.layoutHouseOwner).setOnClickListener(this);
		}
		
		transaction.commit();
		
	}
	
	@Override
	protected HouseEntity createEntity(String json) throws JSONException
	{
		JSONArray array = new JSONArray(json);
		return new HouseEntity(array.getJSONObject(0));
	}

	@Override
	public void onClick(View v)
	{
		/*switch (v.getId())
		{
			case R.id.layoutHouseOwner:
				activity.openPage(new PlayerPage().setPlayerid(entity.getOwnerId()), true);
				break;
		}*/
	}
	
	@Override
	public String getWebsiteUrl()
	{
		if (entity == null) return null;
		return String.format(Locale.getDefault(), "http://convoytrucking.net/houses.php?houseid=%d", entity.getId());
	}
	
	
	
}
