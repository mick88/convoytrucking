package com.mick88.convoytrucking.houses;

import java.text.NumberFormat;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.widget.ImageView;

import com.mick88.convoytrucking.ConvoyTruckingApp;
import com.mick88.convoytrucking.R;
import com.mick88.convoytrucking.api.entities.ApiEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HouseEntity extends ApiEntity
{
	private static final long serialVersionUID = 6813566299556581976L;
	
	int id, price, slots, ownerId;
	boolean available;
	double x, y;
	String ownerName, location, 
		imgSmallUrl, imgBigUrl;
	
	// bitmap is not serializable
	/*transient Bitmap 
		imgSmall=null,
		imgBig = null;*/
	
	public HouseEntity(JSONObject json) throws JSONException
	{
		super(json);
	}
	
	@Override
	public void parseJson(JSONObject json) throws JSONException
	{
		id = json.getInt("id");
		price = json.getInt("price");
		slots = json.getInt("slots");
		if (json.isNull("ownerid") == false)
			ownerId = json.getInt("ownerid");
		else ownerId = 0;
		ownerName = json.getString("owner_name");
		x = json.getDouble("x");
		y = json.getDouble("y");
		available = json.getBoolean("availability");
		location = json.getString("location");
		
		if (json.isNull("picture_small"));
		else
		{
			JSONObject jsonSmallImg =json.getJSONObject("picture_small");
			if (jsonSmallImg != null) 
				imgSmallUrl = jsonSmallImg.getString("url");
	
			if (json.has("picture_big"))
			{
				JSONObject jsonBigImg = json.getJSONObject("picture_big");
				if (jsonBigImg != null) imgBigUrl = jsonBigImg.getString("url");
			}
		}
		
		valid = true;
	}
	
	public int getId()
	{
		return id;
	}
	
	public boolean isAvailable()
	{
		return available;
	}
	
	public int getPrice()
	{
		return price;
	}
	
	public int getSlots()
	{
		return slots;
	}
	
	public String getLocation()
	{
		return location;
	}
	
	public String getImgBigUrl()
	{
		return imgBigUrl;
	}
	
	public String getAddress()
	{
		return String.format(Locale.getDefault(), "%d %s", id, location);
	}
	
	public String getOwnerName()
	{
		return ownerName;
	}
	
	public int getOwnerId()
	{
		return ownerId;
	}
	
	public String getPriceFormat()
	{
		NumberFormat format = NumberFormat.getNumberInstance(Locale.US);		
		return String.format("$%s", format.format(price));
	}
	
	@Deprecated
	private void setImageToView(final String url, final ImageView view)
	{
		ImageLoader.getInstance().displayImage(url, view);
		/*if (url == null) 
		{
//			Log.e("House", "Image is null for house id "+id);
			return;
		}
		final Integer id = Integer.valueOf(this.id);
		view.setTag(id);
		
		new AsyncTask<String, Void, Bitmap>()
		{			
			@Override
			protected Bitmap doInBackground(String... params)
			{
				try
				{
					// TODO: cache image
					InputStream in = new java.net.URL(url).openStream();
					return BitmapFactory.decodeStream(in);
				} catch (Exception e)
				{
					e.printStackTrace();
					return null;
				}
			}
			
			@Override
			protected void onPostExecute(Bitmap result)
			{
				super.onPostExecute(result);
				imgSmall = result;
				Object tag = view.getTag();
				if ((result != null) && (tag instanceof Integer) && ((Integer)tag == id))
				{
					view.setImageBitmap(result);
				}
				
			}
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);*/
	}
	
	public void setSmallImageToView(ImageView view)
	{
		ImageLoader.getInstance().displayImage(imgSmallUrl, view, getDefaultDisplayImageOptions().showImageForEmptyUri(R.drawable.generic_house).build());
		/*if (imgSmall != null) view.setImageBitmap(imgSmall);
		else 
		{
			view.setImageResource(R.drawable.generic_house);
			setImageToView(imgSmallUrl, view);
		}*/
	}
	
	private static DisplayImageOptions.Builder getDefaultDisplayImageOptions()
	{
		return ConvoyTruckingApp.getDefaultDisplayImageOptions();
	}
	
	public void setBigImageToView(ImageView view)
	{
		ImageLoader.getInstance().displayImage(imgBigUrl, view, getDefaultDisplayImageOptions().showImageForEmptyUri(R.drawable.generic_house_big).build());
		/*if (imgBig != null) view.setImageBitmap(imgBig);
		else 
		{
			if (imgSmall != null) view.setImageBitmap(imgSmall);
//			else view.setImageResource(R.drawable.generic_house_big);
			setImageToView(imgBigUrl, view);
		}*/
	}
	
	@Override
	public int compareTo(ApiEntity another)
	{
		if (another instanceof HouseEntity)
		{
			HouseEntity anotherHouse = (HouseEntity) another;
			if (slots > anotherHouse.slots) return 1;
			else if (slots < anotherHouse.slots) return -1;
			else return 0;
		}
		return super.compareTo(another);
	}
	
}
