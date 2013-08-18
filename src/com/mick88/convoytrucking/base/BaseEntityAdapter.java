package com.mick88.convoytrucking.base;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.mick88.convoytrucking.ConvoyTruckingApp;
import com.mick88.convoytrucking.R;
import com.mick88.convoytrucking.api.entities.ApiEntity;
import com.mick88.util.FontApplicator;

public abstract class BaseEntityAdapter<T extends ApiEntity> extends ArrayAdapter<T>
{
	protected Context context;
	protected FontApplicator fontApplicator=null;
	
	protected abstract int selectItemLayout();
	protected abstract void fillItemContent(View view, T entity, int position);
	
	public BaseEntityAdapter(Context context, int textViewResourceId,
			List<T> objects)
	{
		super(context, textViewResourceId, objects);
		this.context = context;
//		this.items = objects;
		
		fontApplicator = new FontApplicator(getContext().getAssets(), ConvoyTruckingApp.FONT_ROBOTO_LIGHT);
	}
	
	protected LayoutInflater getInflater()
	{
		return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View view;
		if (convertView == null)
		{
			View card = getInflater().inflate(R.layout.card, parent, false);
			view = getInflater().inflate(selectItemLayout(), (ViewGroup) card.findViewById(R.id.card_root),  true);
			fontApplicator.applyFont(view);
			
			fillItemContent(view, getItem(position), position);
		}
		else
		{
			view = convertView;
			fillItemContent(view, getItem(position), position);
		}
		return view;
	}
	
}
