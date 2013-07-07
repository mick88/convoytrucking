package com.mick88.convoytrucking.vehicles;

import java.util.List;

import org.json.JSONException;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.mick88.convoytrucking.BaseEntityAdapter;
import com.mick88.convoytrucking.api.ApiConnection;
import com.mick88.convoytrucking.api.ApiRequest;
import com.mick88.convoytrucking.base.BaseListPageFragment;

public class VehicleListPage extends BaseListPageFragment<VehicleEntity, VehicleEntityCollection>
{

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected BaseEntityAdapter<VehicleEntity> createAdapter(
			List<VehicleEntity> entities)
	{
		return new VehicleEntityAdapter(getActivity(), entities);
	}

	@Override
	protected void onTilePressed(VehicleEntity entity)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected VehicleEntityCollection createEntity(String json)
			throws JSONException
	{
		return new VehicleEntityCollection(json);
	}
	
	@Override
	protected ApiRequest createRequest()
	{
		return super.createRequest()
				.setShow(ApiConnection.SHOW_VEHICLES);
	}

}
