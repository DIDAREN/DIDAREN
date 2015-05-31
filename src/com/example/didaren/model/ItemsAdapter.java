package com.example.didaren.model;

import java.util.ArrayList;
import java.util.List;

import com.example.didaren.R;
import com.example.didaren.task.BitmapLoaderTask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ItemsAdapter extends ArrayAdapter<Items> {

	private int mResourceId;
	
	private Context mContext;
		
	public ItemsAdapter(Context context, int resource, List<Items> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		mResourceId = resource;
		mContext = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Items items = getItem(position);
		View view;
		ViewHolder viewHolder;
		
		if (convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(mResourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.textLayout = (LinearLayout) view.findViewById(R.id.text_layout);
			viewHolder.imageLayout = (LinearLayout) view.findViewById(R.id.image_layout);
			viewHolder.textItem = (TextView) view.findViewById(R.id.text_item);
			viewHolder.imageItem = (ImageView) view.findViewById(R.id.image_item);
			view.setTag(viewHolder);
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		
		if (items.getType() == Items.TYPE_TEXT) {
			viewHolder.textLayout.setVisibility(View.VISIBLE);
			viewHolder.imageLayout.setVisibility(View.GONE);
			if (items.getMode() == 0) {
				SpannableString spannableString = new SpannableString(items.getContent());
				
				ArrayList<Integer> locationList = items.getLocation();
				for (int i=0; i<locationList.size(); i+=2) {
					int iStart = locationList.get(i);
					int iEnd = locationList.get(i+1);
					if(iStart < iEnd) {
						spannableString.setSpan(new StyleSpan(Typeface.BOLD), iStart, iEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
						spannableString.setSpan(new RelativeSizeSpan(1.05f), iStart, iEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
					}
				}
				viewHolder.textItem.setText(spannableString);
			} else {
				viewHolder.textItem.setText(items.getContent());
			}
		} else if (items.getType() == Items.TYPE_IMAGE) {
			viewHolder.textLayout.setVisibility(View.GONE);
			viewHolder.imageLayout.setVisibility(View.VISIBLE);
			loadBitmap(viewHolder.imageItem, mContext, items.getContent());
		}
		
		return view;		
	}
	
	class ViewHolder {
		LinearLayout textLayout;
		LinearLayout imageLayout;
		TextView textItem;
		ImageView imageItem;
	}
	
	public void loadBitmap(ImageView imageView, Context context, String URL) {
		
		if (cancelPotentialWork(imageView)) {
			final BitmapLoaderTask task = new BitmapLoaderTask(imageView, mContext);
			final AsyncDrawable asyncDrawable = new AsyncDrawable(mContext.getResources(), null, task);
			imageView.setImageDrawable(asyncDrawable);
			String[] params = new String[1];
			params[0] = URL;
			task.execute(params);
		}
	}
	
	public static boolean cancelPotentialWork(ImageView imageView) {
		
		final BitmapLoaderTask bitmapLoaderTask = getBitmapLoaderTask(imageView);
		
		if (bitmapLoaderTask != null) {	
			if (imageView.getDrawable() instanceof AsyncDrawable) {
				return false;
			} else {
				bitmapLoaderTask.cancel(true);
			}
		}
		return true;
	}
	
	private static BitmapLoaderTask getBitmapLoaderTask(ImageView imageView) {
		
		if(imageView != null) {
			final Drawable drawable = imageView.getDrawable();
			if (drawable instanceof AsyncDrawable) {
				final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
				return asyncDrawable.getBitmapWorkerTask();
			}
		}
		return null;
	}
}




















