package com.mick88.convoytrucking.scoretable;

import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.mick88.convoytrucking.BaseEntityAdapter;
import com.mick88.convoytrucking.R;
import com.mick88.convoytrucking.scoretable.ScoretablePage.SortBy;

public class ScoreEntityAdapter extends BaseEntityAdapter<ScoreEntity>
{
	class ScoreCardContainer
	{
		final TextView tvPlayerPos, tvPlayername, tvScore;
		
		public ScoreCardContainer(TextView tvPlayerPos, TextView tvPlayerName, TextView tvScore)
		{
			this.tvPlayername = tvPlayerName;
			this.tvPlayerPos = tvPlayerPos;
			this.tvScore = tvScore;
		}
	}
	
	final static int layout  = R.layout.card_score;
	private final SortBy sortBy;
	
	public ScoreEntityAdapter(Context context,
			List<ScoreEntity> objects, SortBy sortBy)
	{
		super(context, layout, objects);
		this.sortBy = sortBy;
	}

	@Override
	protected int selectItemLayout()
	{
		return layout;
	}

	@Override
	protected void fillItemContent(View view, ScoreEntity entity, int position)
	{
		ScoreCardContainer container = (ScoreCardContainer) view.getTag();
		if (container == null)
		{
			container = new ScoreCardContainer((TextView)view.findViewById(R.id.tvPlayerPos), 
					(TextView)view.findViewById(R.id.tvPlayername),
					(TextView)view.findViewById(R.id.tvPlayerScore));
			view.setTag(container);
		}
		
		container.tvPlayername.setText(String.valueOf(entity.getPlayername()));
		container.tvPlayerPos.setText(String.valueOf(entity.getPosition()));
		
		String score=null;
		switch (sortBy)
		{
			case convoy_score:
				score = String.valueOf(entity.getConvoy());
				break;
				
			case odo:
				score = entity.getOdoKm();
				break;
				
			case score:
				score = String.valueOf(entity.getScore());
				break;
				
			case truck_loads:
				score  = String.valueOf(entity.getTruckloads());
				break;
				
			/*default:
				tvScore.setText(String.valueOf(String.format(Locale.getDefault(), 
						"S: %d T: %d C: %d O: %s", entity.getScore(), entity.getTruckloads(), entity.getConvoy(), entity.getOdoKm())));
				break;*/				
		}
		
		container.tvScore.setText(String.valueOf(String.format(Locale.getDefault(), 
				"%s: %s",ScoretablePage.sortByToString(sortBy), score)));
		
	}

		
}
