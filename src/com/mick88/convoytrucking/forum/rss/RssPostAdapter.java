package com.mick88.convoytrucking.forum.rss;

import java.util.List;

import com.mick88.convoytrucking.R;
import com.mick88.util.FontApplicator;

import android.content.Context;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Html.TagHandler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class RssPostAdapter extends ArrayAdapter<RssItem>
{

	final FontApplicator fontApplicator;
	final ImageGetter imageGetter;
	final TagHandler tagHandler;
	
	public RssPostAdapter(Context context, List<RssItem> objects, FontApplicator fontApplicator, ImageGetter imageGetter, TagHandler tagHandler) 
	{
		super(context, R.layout.card_forum_post, objects);
		this.fontApplicator = fontApplicator;
		this.imageGetter = imageGetter;
		this.tagHandler = tagHandler;
	}
	
	public RssPostAdapter(Context context, List<RssItem> objects, FontApplicator fontApplicator)
	{
		this(context, objects, fontApplicator, null, null);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View view = convertView;
		if (view == null)
		{
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.card_forum_post, parent, false);
			fontApplicator.applyFont(view);
		}
		
		TextView tvTitle = (TextView) view.findViewById(R.id.tvAuthor);
		TextView tvContent = (TextView) view.findViewById(R.id.tvContent);
		TextView tvTime = (TextView) view.findViewById(R.id.tvTime);
		
		
		tvTitle.setText(getItem(position).getTitle());
		tvContent.setText(Html.fromHtml(getItem(position).getDesciption(), imageGetter, tagHandler));
		tvTime.setText(getItem(position).getDate());
		
		return view;
	}

}
