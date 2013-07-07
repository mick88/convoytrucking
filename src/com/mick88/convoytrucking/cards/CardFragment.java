package com.mick88.convoytrucking.cards;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mick88.convoytrucking.ConvoyTruckingApp;
import com.mick88.convoytrucking.R;
import com.mick88.convoytrucking.base.BaseActivity;

/**
 * Base class for all card fragments
 * @author Michal
 *
 */
public abstract class CardFragment extends Fragment
{
	protected ViewGroup rootView=null;
	protected View contentView=null;
	OnClickListener onClickListener=null;
	
	protected abstract int getLayoutRes();	
	
	protected abstract void fillViewContents();
	
	protected BaseActivity activity;
	protected ConvoyTruckingApp application;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.activity = (BaseActivity) getActivity();
		this.application = (ConvoyTruckingApp) activity.getApplication();
	}
	
	@Override
	public final View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		rootView = (ViewGroup) inflater.inflate(R.layout.card, container, false);
		int LayoutRes = getLayoutRes();
		if (LayoutRes != 0)
		{
			this.contentView = inflater.inflate(LayoutRes, rootView)
					.findViewById(R.id.card_content);
			
			if (contentView == null)
			{
				Log.e(toString(), "Card layout must have root element called 'R.id.card_content'!");
			}
		}
		
		
		return rootView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		applyRobotoFont(rootView);
		rootView.setOnClickListener(onClickListener);
		fillViewContents();
	}
	
	protected View findViewById(int id)
	{
		if (rootView == null) return null;
		else return rootView.findViewById(id);
	}
	
	protected TextView findTextViewById(int id)
	{
		return (TextView) findViewById(id);
	}
	
	protected void setLoading(boolean isLoading)
	{
		if (rootView == null || contentView == null) return;
		if (isLoading)
		{
			rootView.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
			contentView.setVisibility(View.INVISIBLE);
		}
		else 
		{
			rootView.findViewById(R.id.progressBar).setVisibility(View.GONE);
			contentView.setVisibility(View.VISIBLE);
		}
	}
	
	protected void applyRobotoFont(View view)
	{
		if (view == null)
			return;
		Typeface font = Typeface.createFromAsset(activity.getAssets(),
				"fonts/Roboto_Light.ttf");
		if (view instanceof TextView)
		{
			((TextView) view).setTypeface(font);
		} else if (view instanceof ViewGroup)
		{
			ViewGroup viewGroup = (ViewGroup) view;
			applyRobotoFont(viewGroup, font);
		}
	}

	protected void applyRobotoFont(ViewGroup viewGroup, Typeface font)
	{
		for (int i = 0; i < viewGroup.getChildCount(); i++)
		{
			View v = viewGroup.getChildAt(i);
			if (v instanceof TextView)
			{
				((TextView) v).setTypeface(font);
			} else if (v instanceof ViewGroup)
			{
				applyRobotoFont(v);
			}
		}
	}
	
	@Override
	public void onAttach(Activity activity)
	{
		if (activity instanceof BaseActivity)
		{
			((BaseActivity) activity).onCardAttached(this);
		}
		super.onAttach(activity);
	}
	
	@Override
	public void onDetach()
	{
		this.activity.onCardRemoved(this);
		super.onDetach();
	}
	
	public CardFragment setOnClickListener(OnClickListener onClickListener)
	{
		this.onClickListener = onClickListener;
		if (rootView != null)
		{
			rootView.setOnClickListener(onClickListener);
		}
		return this;
	}
}
