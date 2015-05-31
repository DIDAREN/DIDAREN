package com.example.didaren.modules;

import java.util.ArrayList;

import com.example.didaren.MainActivity;
import com.example.didaren.R;
import com.example.didaren.model.News;
import com.example.didaren.model.NewsAdapter;
import com.example.didaren.sqlite.NewsDatabaseHelper;
import com.example.didaren.task.DataWithDatabaseTask;
import com.example.didaren.task.RefreshTask;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class PreActivityFragment extends Fragment {
	private static final String ARG_SECTION_NUMBER = "section_number";
	private static final String FIRST_PAGE_URL = "http://www.xuegong.cug.edu.cn/category/activity";
	private static final String BASE_PAGE_URL = "http://www.xuegong.cug.edu.cn/category/activity/page/";

	private static final int MAX_PAGES = 20;
	
	private RefreshTask mRefreshTask = null;
	private DataWithDatabaseTask mDataTask = null;
	private NewsDatabaseHelper mDbHelper;
	
	private PullToRefreshListView mListView; 
	private ArrayList<News> mList = new ArrayList<News>();
	private NewsAdapter mAdapter;
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////
	
    public static PreActivityFragment newInstance(int sectionNumber) {
    	PreActivityFragment fragment = new PreActivityFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        //从数据库中获取新闻
        mDbHelper = new NewsDatabaseHelper(getContext(), "Activities.db", null, 1);
        String[] params = new String[1];
    	params[0] = "0";
    	mDataTask = (DataWithDatabaseTask) new DataWithDatabaseTask(mList, mDbHelper).execute(params);    
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.important_news_fragment, container, false);
        mListView = (PullToRefreshListView)rootView.findViewById(R.id.list);
        mAdapter = new NewsAdapter(getContext(), mList);
        mListView.setAdapter(mAdapter);
        
        mListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
        	
        	@Override
			public void onPullDownToRefresh( PullToRefreshBase<ListView> refreshView) {
        		 String[] params = new String[2];
        	     params[0] = FIRST_PAGE_URL;
        	     params[1] = "0";
        	     mRefreshTask = (RefreshTask) new RefreshTask(getContext(), mList, mAdapter, mListView).execute(params);
			}
        	
			@Override
			public void onPullUpToRefresh( PullToRefreshBase<ListView> refreshView) {
			    if ((mList.size() / 10) != MAX_PAGES) {
					String[] params = new String[2];
			        int nPage = 1 + mList.size() / 10;
			        if (nPage == 1) {
			        	params[0] = FIRST_PAGE_URL;
			        } else {
			        	params[0] = BASE_PAGE_URL + nPage;
			        }
			        params[1] = "1";
			        mRefreshTask = (RefreshTask) new RefreshTask(getContext(), mList, mAdapter, mListView).execute(params);
			       
			    } else {
			    	mListView.onRefreshComplete();
			    }				
			}
		});
        
        mListView.setOnItemClickListener(new OnItemClickListener() {

    		@Override
    		public void onItemClick(AdapterView<?> parent, View view, int position,
    				long id) {
    			// TODO Auto-generated method stub
    			News news = mList.get(position - 1);
    			FragmentManager fragmentManager = getActivity().getFragmentManager();
    			FragmentTransaction transaction = fragmentManager.beginTransaction();
    			transaction.replace(R.id.container, NewsContentFragment.newInstance(news));
    			transaction.addToBackStack(null);
    			transaction.commit();
    		}
        });
        
        return rootView;
    }
    
    @Override
	public void onPause() {
    	super.onPause();
    	//结束未完成的进程
    	if (mRefreshTask != null) {
    		mRefreshTask.cancel(true);
    	}
    	
    	if (mDataTask != null) {
    		mDataTask.cancel(true);
    	}
    }
    
    @Override
    public void onStop() {
    	super.onStop();
    	//保存新闻记录到数据库中
    	String[] params = new String[1];
    	params[0] = "1";
    	new DataWithDatabaseTask(mList, mDbHelper).execute(params);    
    }
    /**
     * 获得碎片所在的上下文
     * @return
     */
    private Context getContext() {
    	return getActivity().getActionBar().getThemedContext();
    }  
}
