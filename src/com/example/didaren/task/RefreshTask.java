package com.example.didaren.task;

import java.util.ArrayList;

import com.example.didaren.GetDataFromWeb;
import com.example.didaren.model.News;
import com.example.didaren.model.NewsAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class RefreshTask extends AsyncTask<String, Void, ArrayList<News>> {

	/**
	 * �ж�������ˢ�£�0������������ˢ�£�1��
	 */
	private int mode = -1;
	private Context mContext = null;
	private ArrayList<News> mNewsList = null;
	private NewsAdapter mAdapter = null;
	private PullToRefreshListView mRefreshListView = null;
	
	public RefreshTask(Context context, ArrayList<News> list, NewsAdapter adapter, PullToRefreshListView listView){
		mContext = context;
		mNewsList = list;
		mAdapter = adapter;
		mRefreshListView = listView;
	}
	
	@Override
	protected ArrayList<News> doInBackground(String... params) {
		// TODO Auto-generated method stub
		ArrayList<News> tempList = new ArrayList<News>();
		//��ȡ��ҳ��url
		String strUrl = params[0];
		//��ȡˢ�±�־������ or ����
		mode = Integer.parseInt(params[1]);
		if(GetDataFromWeb.isNetworkAvailable(mContext)) {
			tempList = GetDataFromWeb.moImportantNews(strUrl);
		}
		return tempList;
	}
	
	@Override
	protected void onPostExecute(ArrayList<News> result) {
		if(result.size() != 0) {
			switch (mode) {
			case 0:
				if(!mNewsList.isEmpty()) {
					if (!result.get(0).getHref().equals(mNewsList.get(0).getHref())) {
						mNewsList.removeAll(mNewsList);	
						mNewsList.addAll(result);
						mAdapter.notifyDataSetChanged();
					}	
				} else {	
					mNewsList.addAll(result);
					mAdapter.notifyDataSetChanged();
				}
				break;
			case 1:
				mNewsList.addAll(result);
				mAdapter.notifyDataSetChanged();
				break;
			default:
				break;
			}		
		} else {
			Toast.makeText(mContext, "���ݻ�ȡʧ�ܣ������³���", Toast.LENGTH_LONG).show();
		}	
		mRefreshListView.onRefreshComplete();
	}

}
