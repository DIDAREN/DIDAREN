package com.example.didaren.modules;

import java.util.ArrayList;
import java.util.List;

import com.example.didaren.GetDataFromWeb;
import com.example.didaren.R;
import com.example.didaren.model.Items;
import com.example.didaren.model.ItemsAdapter;
import com.example.didaren.model.News;
import com.example.didaren.task.GetContentTask;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;


public class NewsContentFragment extends Fragment {
	
	private static final String NEWS_TITLE = "news_title";
	private static final String NEWS_AUTHOR = "news_author";
	private static final String NEWS_TIME = "news_time";
	private static final String NEWS_URL = "news_url";
	
	private TextView mTitle;
	private TextView mTime;
	private TextView mAuthor;
	
	private News news  = null;
	/**
	 *  mContentListView用来存放新闻内容
	 */
	private ListView mContentListView;
	
	private ItemsAdapter mAdapter;
	private ArrayList<Items> mList = new ArrayList<Items>();
	
	private GetContentTask mTask = null;
	
	private ProgressDialog progressDialog;

	public static NewsContentFragment newInstance(News news) {
    	NewsContentFragment fragment = new NewsContentFragment();
        Bundle args = new Bundle();
        args.putString(NEWS_TITLE, news.getTitle());
        args.putString(NEWS_AUTHOR, news.getAuthor());
        args.putString(NEWS_TIME, news.getTime());
        args.putString(NEWS_URL, news.getHref());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);        
        String strNewsTitle = getArguments().getString(NEWS_TITLE);
		String strNewsUrl = getArguments().getString(NEWS_URL);
		String strNewsAuthor = getArguments().getString(NEWS_AUTHOR);
		String strNewsTime = getArguments().getString(NEWS_TIME);	
		news = new News(strNewsTitle, strNewsUrl, strNewsAuthor, strNewsTime);          
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	View rootView = inflater.inflate(R.layout.news_content_fragment, container, false);
    	mTitle = (TextView) rootView.findViewById(R.id.news_title1);
    	mTitle.setText(news.getTitle());
    	mTime = (TextView) rootView.findViewById(R.id.news_time1);
    	mTime.setText("时间:" + news.getTime());
    	mAuthor = (TextView) rootView.findViewById(R.id.news_author1);
    	mAuthor.setText("作者:" + news.getAuthor());
    	
    	mAdapter = new ItemsAdapter(getContext(), R.layout.news_content_list_item, mList);
    	mContentListView = (ListView) rootView.findViewById(R.id.content_list);
    	mContentListView.setAdapter(mAdapter);
    	
    	
    	progressDialog = new ProgressDialog(getContext());
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setMessage("正在加载...");
		progressDialog.show();
    	String [] params = new String[1];
    	params[0] = news.getHref();
    	mTask = (GetContentTask) new GetContentTask(getContext(), mList, mAdapter, progressDialog).execute(params);
    	
		return rootView;
    }
    
    @Override
	public void onPause() {
    	super.onPause();
    	//结束未完成的进程
    	if (mTask != null) {
    		mTask.cancel(true);
    	}    	
    }
	
	/*
	public Bitmap returnBitMap(String url){  
        URL myFileUrl = null;    
        Bitmap bitmap = null;   
        try {    
            myFileUrl = new URL(url);    
        } catch (MalformedURLException e) {    
            e.printStackTrace();    
        }    
        try {    
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();    
            conn.setDoInput(true);    
            conn.connect();    
            InputStream is = conn.getInputStream();    
            bitmap = BitmapFactory.decodeStream(is);    
            is.close();    
        } catch (IOException e) {    
              e.printStackTrace();    
        }    
              return bitmap;    
    }*/   

	
    private Context getContext() {
    	return getActivity().getActionBar().getThemedContext();
    }
	
}
