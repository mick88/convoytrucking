package com.mick88.convoytrucking.navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mick88.convoytrucking.ConvoyTruckingApp;
import com.mick88.convoytrucking.R;
import com.mick88.util.FontApplicator;

public class MenuAdapter extends ArrayAdapter<AppMenuItem>
{
	static final int layout = R.layout.nav_drawer_item;
	FontApplicator fontApplicator;
	
	final AppMenuItem[] items;
	final Context context;
	
	public MenuAdapter(Context context,
			AppMenuItem[] objects)
	{
		super(context, layout, objects);
		this.context = context;
		this.items = objects;
		fontApplicator = new FontApplicator(context.getAssets(), ConvoyTruckingApp.FONT_ROBOTO_LIGHT);
	}
	
	void fillViewContents(View view, int id)
	{
		TextView tvText = (TextView) view.findViewById(android.R.id.text1);
		tvText.setText(items[id].getLabelId());
		
		ImageView imageView = (ImageView) view.findViewById(android.R.id.icon);
		imageView.setImageResource(items[id].getIconId());
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(layout, parent, false);
		fontApplicator.applyFont(view);
		fillViewContents(view, position);
		return view;
	}
	
}
