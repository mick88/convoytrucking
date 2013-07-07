package com.mick88.convoytrucking.houses;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mick88.convoytrucking.BaseEntityAdapter;
import com.mick88.convoytrucking.R;

public class HouseEntityAdapter extends BaseEntityAdapter<HouseEntity>
{
	class HouseViewContainer
	{
		final TextView tvCaption,
			tvPrice,
			tvSlots,
			tvGroupLabel,
			tvOwner;
		final ImageView imgHouseImage;
		
		public HouseViewContainer(TextView tvCaption, TextView tvPrice, TextView tvSlots, ImageView imgHouseImage, TextView tvOwner, TextView tvGroupLabel)
		{
			this.tvCaption = tvCaption;
			this.tvPrice = tvPrice;
			this.tvSlots = tvSlots;
			this.tvOwner = tvOwner;
			this.imgHouseImage = imgHouseImage;
			this.tvGroupLabel = tvGroupLabel;
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
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View view;
		if (convertView == null)
		{
			View outer = getInflater().inflate(R.layout.card_house_group, parent, false);
			View card = getInflater().inflate(R.layout.card, (ViewGroup) outer.findViewById(R.id.cardContainer), true);
			view = getInflater().inflate(selectItemLayout(), (ViewGroup) card.findViewById(R.id.card_root),  true);
			fontApplicator.applyFont(outer);
			view = outer;
			fillItemContent(view, getItem(position), position);
		}
		else
		{
			view = convertView;
			fillItemContent(view, getItem(position), position);
		}
		

		return view;
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
					(TextView)view.findViewById(R.id.tvHouseOwner),
					(TextView) view.findViewById(R.id.tvGroupLabel));
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

		if (position == 0 || getItem(position-1).getSlots() != entity.getSlots())
		{
			container.tvGroupLabel.setText(new StringBuilder().append(getItem(position).getSlots()).append(" slots:"));
			container.tvGroupLabel.setVisibility(View.VISIBLE);
		}
		else container.tvGroupLabel.setVisibility(View.GONE);
		
		container.imgHouseImage.setImageResource(R.drawable.generic_house);
		entity.setSmallImageToView(container.imgHouseImage);
	}
	
}
