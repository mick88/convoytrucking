package com.mick88.convoytrucking.server_info;

import java.util.Locale;

import android.widget.ProgressBar;
import android.widget.TextView;

import com.mick88.convoytrucking.R;
import com.mick88.convoytrucking.cards.DataCardFragment;

public class ServerInfoCard extends DataCardFragment<ServerInfoEntity>
{
	
	@Override
	protected void fillViewContents()
	{
		super.fillViewContents();
		
		if (this.entity != null && rootView != null)
		{
			try
			{
				((TextView) rootView.findViewById(R.id.tv_server_ip))
						.setText(entity.getServerIp());
				((TextView) rootView.findViewById(R.id.tv_server_gamemode))
						.setText(entity.getGamemode());
				((TextView) rootView.findViewById(R.id.tv_server_status))
						.setText(entity.getServerState().toString());
				((TextView) rootView.findViewById(R.id.tv_server_uptime))
						.setText(entity.getUptimeStr());
				ProgressBar progressBar = (ProgressBar) rootView
						.findViewById(R.id.pb_server_players);
				progressBar.setMax(entity.getMaxPlayers());
				progressBar.setProgress(entity.getNumPlayers());
				((TextView) rootView.findViewById(R.id.tv_server_players))
						.setText(String.format(Locale.getDefault(), "%d / %d",
								entity.getNumPlayers(), entity.getMaxPlayers()));
			} catch (NullPointerException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	
	
	@Override
	protected int getLayoutRes()
	{
		return R.layout.card_server_info;
	}
	
}
