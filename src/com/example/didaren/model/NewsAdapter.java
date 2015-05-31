package com.example.didaren.model;

import java.util.List;

import com.example.didaren.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter {

	private List<News> newsList;
	Context context;
	
	public NewsAdapter(Context context, List<News> objects) {
		this.newsList = objects;
		this.context = context;
	}
		
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return (newsList == null) ? 0 : newsList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return newsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public class ViewHolder {
		TextView titleView;
		TextView authorView;
		TextView timeView;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		News news = (News)getItem(position);
		ViewHolder viewHolder = null;
		
		if (convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.news_item, null);
			viewHolder = new ViewHolder();
			viewHolder.titleView = (TextView) convertView.findViewById(R.id.news_title);
			viewHolder.authorView = (TextView) convertView.findViewById(R.id.news_author);
			viewHolder.timeView = (TextView) convertView.findViewById(R.id.news_time);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.titleView.setText(news.getTitle());
		viewHolder.authorView.setText("作者:"+news.getAuthor());
		viewHolder.timeView.setText("时间:"+news.getTime());
		return convertView;
	}

}