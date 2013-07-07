package com.mick88.convoytrucking.player;

import com.mick88.convoytrucking.R;
import com.mick88.util.FontApplicator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class StatsAdapter extends ArrayAdapter<PlayerStat>
{

	FontApplicator fontApplicator=null;
	
	public StatsAdapter(Context context, PlayerStat[] objects)
	{
		super(context, R.layout.stat_item, R.id.tvValue, objects);
		// TODO Auto-generated constructor stub
	}
	
	public StatsAdapter setFontApplicator(FontApplicator fontApplicator)
	{
		this.fontApplicator = fontApplicator;
		return this;
	}
	
	private LayoutInflater getInflater()
	{
		return (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View view = convertView;
		if (view == null) 
		{
			view = getInflater().inflate(R.layout.stat_item, parent, false);
			fontApplicator.applyFont(view);
		}
		
		TextView tvKey = (TextView) view.findViewById(R.id.tvKey);
		TextView tvValue = (TextView) view.findViewById(R.id.tvValue);
		
		tvKey.setText(getItem(position).getKey());
		tvValue.setText(getItem(position).getValueString());
		
		return view;		
		
	}
	
}
