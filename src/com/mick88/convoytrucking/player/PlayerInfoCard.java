package com.mick88.convoytrucking.player;

import java.util.Locale;

import android.view.View;
import android.widget.TextView;

import com.mick88.convoytrucking.R;
import com.mick88.convoytrucking.cards.DataCardFragment;

public class PlayerInfoCard extends DataCardFragment<PlayerEntity>
{
	
	@Override
	protected int getLayoutRes()
	{
		return R.layout.card_player_info;
	}
	
	@Override
	protected void fillViewContents()
	{
		super.fillViewContents();
		if (rootView != null && entity != null)
		{
			((TextView)rootView.findViewById(R.id.tvPlayerName)).setText(entity.getName());
			((TextView)rootView.findViewById(R.id.tvPlayerScore)).setText(String.format(Locale.getDefault(), "Score: %d", entity.getScore()));
			
			TextView tvStaff = (TextView) rootView.findViewById(R.id.tvPlayerStaff);
			switch (entity.getStaffType())
			{
				case administrator:
					tvStaff.setText(R.string.admin);
					tvStaff.setVisibility(View.VISIBLE);
					break;
				case jr_mod:
					tvStaff.setText(R.string.jrmod);
					tvStaff.setVisibility(View.VISIBLE);
					break;
				case moderator:
					tvStaff.setText(R.string.moderator);
					tvStaff.setVisibility(View.VISIBLE);
					break;
				default:
					tvStaff.setVisibility(View.GONE);
					break;
			}
			
			findViewById(R.id.tvPlayerVip).setVisibility(entity.getVip()?View.VISIBLE:View.GONE);
			findTextViewById(R.id.tvLastSeen).setText(new StringBuilder(activity.getString(R.string.last_seen)).append(' ').append(entity.getLastSeenFormat()).append(" ago"));
			findTextViewById(R.id.tvTruckMissions).setText(new StringBuilder(activity.getString(R.string.truck_missions)).append(' ').append(entity.getTruckLoads()));
			findTextViewById(R.id.tvConvoyScore).setText(new StringBuilder(activity.getString(R.string.convoy_score)).append(' ').append(entity.getConvoyScore()));
			
			if (entity.getRegistrationDate() == null) findViewById(R.id.tvRegistered).setVisibility(View.GONE);
			else findTextViewById(R.id.tvRegistered).setText(new StringBuilder("Registered on ").append(entity.getRegistrationDate()));
		}
	}	
}
