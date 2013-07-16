package com.mick88.convoytrucking.twitter;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mick88.convoytrucking.R;
import com.mick88.util.FontApplicator;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetAdapter extends ArrayAdapter<Tweet>
{
	final FontApplicator fontApplicator;
	final ImageLoader imageLoader;
	
	static class ViewHolder
	{
		TextView tvContent, tvAuthor, tvTime;
		ImageView imgIcon;
	}
	
	protected LayoutInflater getInflater()
	{
		return (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public TweetAdapter(Context context,
			List<Tweet> objects, FontApplicator fontApplicator) {
		super(context, 0, objects);
		this.fontApplicator = fontApplicator;
		this.imageLoader = ImageLoader.getInstance();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View view = convertView;
		final ViewHolder holder;
		if (view == null)
		{
			holder = new ViewHolder();
			view = getInflater().inflate(R.layout.card_tweet, parent, false);
			
			holder.tvContent = (TextView) view.findViewById(R.id.tvContent);
			holder.tvAuthor = (TextView) view.findViewById(R.id.tvAuthor);
			holder.tvTime = (TextView) view.findViewById(R.id.tvTime);
			holder.imgIcon = (ImageView) view.findViewById(R.id.imgIcon);
			holder.tvContent.setMovementMethod(LinkMovementMethod.getInstance());
			fontApplicator.applyFont(view);
			view.setTag(holder);
		}
		else holder = (ViewHolder) view.getTag();
		
		imageLoader.displayImage(getItem(position).getImgUrl(), holder.imgIcon);
		holder.tvContent.setText(getItem(position).getSpannedContent());
		holder.tvAuthor.setText(getItem(position).getAuthor());
		holder.tvTime.setText(getItem(position).getTime());
		
		return view;
	}

}
