package com.example.didaren.task;

import java.util.ArrayList;

import com.example.didaren.GetDataFromWeb;
import com.example.didaren.model.Items;
import com.example.didaren.model.ItemsAdapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class GetContentTask extends AsyncTask<String, Void, ArrayList<Items>> {

	private Context mContext = null;
	private ArrayList<Items> mList = null;
	private ItemsAdapter mAdapter = null;
	private ProgressDialog mDialog = null;
	
	public GetContentTask(Context context, ArrayList<Items> list, ItemsAdapter adapter, ProgressDialog dialog) {
		mContext = context;
		mList = list;
		mAdapter = adapter;
		mDialog = dialog;
	}
		
	@Override
	protected ArrayList<Items> doInBackground(String... params) {
		// TODO Auto-generated method stub
		ArrayList<Items> list = new ArrayList<Items>();
		String strUrl = params[0];
		if(GetDataFromWeb.isNetworkAvailable(mContext)) {
			list = GetDataFromWeb.moImportantNewsContent(strUrl);
		}
		return list;
	}
	
	@Override 
	protected void onPostExecute(ArrayList<Items> result) {
		if(result.size() != 0) {
			mList.addAll(result);
			mAdapter.notifyDataSetChanged();
		} else {
			Toast.makeText(mContext, "数据获取失败，请重新尝试", Toast.LENGTH_LONG).show();
		}	
		mDialog.dismiss();
	}
}

























