package com.mick88.convoytrucking.dialogs;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

public class PickerDialog<T> extends DialogFragment implements OnClickListener
{
	public interface OnItemPickedListener<T>
	{
		void onItemPicked(T item, int id);
	}
	
	int title=0;
	T [] items;
	OnItemPickedListener<T> itemPickedListener=null;
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{		
		Builder builder = getBuilder()
				.setItems(getItemStrings(), this);
		
		return builder.create();
	}
	
	protected Builder getBuilder()
	{
		return new Builder(getActivity())
//			.setIcon(R.drawable.ic_launcher)
			.setTitle(title);
	}
	
	public PickerDialog<T> setTitle(int title)
	{
		this.title = title;
		return this;
	}
	
	public PickerDialog<T> setItems(T[] items)
	{
		this.items = items;
		return this;
	}
	
	public String [] getItemStrings()
	{
		if (this.items == null) return new String[0];
		String[] items =new String[this.items.length];
		
		for (int i=0; i < items.length; i++)
		{
			items[i] = this.items[i].toString();
		}
				
		return items;
	}
	
	public PickerDialog<T> setItemPickedListener(OnItemPickedListener<T> itemPickedListener)
	{
		this.itemPickedListener = itemPickedListener;
		return this;
	}
	
	LayoutInflater getInflater()
	{
		return (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public void onClick(DialogInterface dialog, int which)
	{
		if (itemPickedListener != null)
		{
			itemPickedListener.onItemPicked(items[which], which);
		}
	}
}
