package com.mick88.convoytrucking.houses;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mick88.convoytrucking.R;
import com.mick88.convoytrucking.base.BaseEntityAdapter;

public class HouseEntityAdapter extends BaseEntityAdapter<HouseEntity>
{
	class HouseViewContainer
	{
		final TextView tvCaption,
			tvPrice,
			tvSlots,
			tvOwner;
		final ImageView imgHouseImage;
		
		public HouseViewContainer(TextView tvCaption, TextView tvPrice, TextView tvSlots, ImageView imgHouseImage, TextView tvOwner)
		{
			this.tvCaption = tvCaption;
			this.tvPrice = tvPrice;
			this.tvSlots = tvSlots;
			this.tvOwner = tvOwner;
			this.imgHouseImage = imgHouseImage;
		}
	}
	
	final static int layout = R.layout.card_house_small;
	
	public HouseEntityAdapter(Context context,
			List<HouseEntity> objects)
	{
		super(context, layout, objects);
	}

	@Override
	protected int selectItemLayout()
	{
		return layout;
	}

	@Override
	protected void fillItemContent(View view, HouseEntity entity, int position)
	{
		HouseViewContainer container = (HouseViewContainer) view.getTag();
		
		if (container == null)
		{
			container = new HouseViewContainer((TextView) view.findViewById(R.id.tvHouseCaption),
					(TextView)view.findViewById(R.id.tvHousePrice),
					(TextView)view.findViewById(R.id.tvHouseSlots),
					(ImageView)view.findViewById(R.id.imHouseImage),
					(TextView)view.findViewById(R.id.tvHouseOwner));
		}
		
		container.tvCaption.setText(entity.getAddress());
		container.tvPrice.setText(entity.getPriceFormat());
		container.tvSlots.setText(String.format("%d slots", entity.getSlots()));
		
		if (entity.getOwnerId() > 1) container.tvOwner.setText(new StringBuilder("Owned by ").append(entity.getOwnerName()));
		else if (entity.isAvailable()) 
		{
			container.tvOwner.setText("Available");
			container.tvOwner.setTextColor(context.getResources().getColor(R.color.black));
		}
		else 
		{
			container.tvOwner.setTextColor(context.getResources().getColor(R.color.red));
			container.tvOwner.setText("Unavailable");
		}
		
		container.imgHouseImage.setImageResource(R.drawable.generic_house);
		entity.setSmallImageToView(container.imgHouseImage);
	}
	
}
