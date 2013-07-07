package com.mick88.convoytrucking.player;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mick88.convoytrucking.ConvoyTruckingApp;
import com.mick88.convoytrucking.R;
import com.mick88.convoytrucking.cards.DataCardFragment;
import com.mick88.util.FontApplicator;

public class PlayerStatsCard extends DataCardFragment<PlayerEntity>
{
//	ListView listView=null;
	LinearLayout statsLayout=null;
	
	@Override
	protected int getLayoutRes()
	{
		return R.layout.card_player_stats;
	}
	
	@Override
	protected void fillViewContents()
	{
		super.fillViewContents();
		/*if (entity != null && listView != null)
		{
			StatsAdapter adapter = new StatsAdapter(activity, entity.getStats());
			listView.setAdapter(adapter.setFontApplicator(new FontApplicator(activity.getAssets(), ConvoyTruckingApp.FONT_ROBOTO_LIGHT)));
		}*/
		if (entity != null && statsLayout != null)
		{
			Context context = getActivity();
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			statsLayout.removeAllViews();
			for (PlayerStat stat : entity.getStats())
			{
				View statView = inflater.inflate(R.layout.stat_item, statsLayout, false);
				TextView tvKey = (TextView) statView.findViewById(R.id.tvKey),
						tvValue = (TextView) statView.findViewById(R.id.tvValue);
				
				tvKey.setText(stat.getKey());
				tvValue.setText(stat.getValueString());
				
				statsLayout.addView(statView);
			}
			new FontApplicator(activity.getAssets(), ConvoyTruckingApp.FONT_ROBOTO_LIGHT).applyFont(statsLayout);
//			StatsAdapter adapter = new StatsAdapter(activity, entity.getStats());
//			listView.setAdapter(adapter.setFontApplicator(new FontApplicator(activity.getAssets(), ConvoyTruckingApp.FONT_ROBOTO_LIGHT)));
		}
		
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
//		listView =  (ListView) findViewById(R.id.listView);
		statsLayout = (LinearLayout) findViewById(R.id.statsLayout);
		super.onViewCreated(view, savedInstanceState);		
		
	}
	
}
