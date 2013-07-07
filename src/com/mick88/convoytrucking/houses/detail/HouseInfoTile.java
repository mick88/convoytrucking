package com.mick88.convoytrucking.houses.detail;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.mick88.convoytrucking.R;
import com.mick88.convoytrucking.cards.DataCardFragment;
import com.mick88.convoytrucking.houses.HouseEntity;

public class HouseInfoTile extends DataCardFragment<HouseEntity> implements OnClickListener
{

	@Override
	protected int getLayoutRes()
	{
		return R.layout.card_house_full;
	}
	
	@Override
	protected void fillViewContents()
	{
		super.fillViewContents();
		
		if (rootView != null && entity != null)
		{
			((TextView)rootView.findViewById(R.id.tvHouseCaption)).setText(entity.getAddress());
			((TextView)rootView.findViewById(R.id.tvHouseSlots)).setText(new StringBuilder().append(entity.getSlots()).append(' ').append("slots"));
			((TextView)rootView.findViewById(R.id.tvHousePrice)).setText(entity.getPriceFormat());
			entity.setBigImageToView((ImageView) rootView.findViewById(R.id.imHouseImage));
			
			if (entity.getOwnerId() == 0) rootView.findViewById(R.id.tvHouseOwnerLabel).setVisibility(View.GONE);
			else ((TextView)rootView.findViewById(R.id.tvHouseOwner)).setText(entity.getOwnerName());
			
			if (entity.isAvailable() == true || entity.getOwnerId() > 0) rootView.findViewById(R.id.tvUnavailableMessage).setVisibility(View.GONE);
			else rootView.findViewById(R.id.tvUnavailableMessage).setVisibility(View.VISIBLE);
			
			rootView.findViewById(R.id.imHouseImage).setOnClickListener(this);		
			
		}
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.imHouseImage:
				String url = entity.getImgBigUrl();
				if (url == null) return;
				
				Intent intent = new  Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(url));
				startActivity(intent);
				break;
		}
		
	}
	
}
