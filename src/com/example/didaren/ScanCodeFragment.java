package com.example.didaren;

import java.io.InputStream;
import com.zxing.activity.CaptureActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ScanCodeFragment extends Fragment {
	
	private SharedPreferences pref;
	private static final String ARG_SECTION_NUMBER = "section_number";
	private Button scanButton;
	private TextView resultTextView;
	private TextView userNameTextView;
	private TextView meetingNameTextView;
	private TextView meetingPlaceTextView;
	private TextView meetingTimeTextView;
	public String sign_flag;//记录之前是否已经签到过
	private ProgressDialog progressDialog;
	private String meeting_time=null;//会议时间
	private String meeting_place=null;//会议地点
	private String meeting_name=null;//会议名称
	private ImageView notLogin;
	public static ScanCodeFragment newInstance(int sectionNumber) {
		ScanCodeFragment fragment = new ScanCodeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
	
	public ScanCodeFragment(){
	}
	
	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	          Bundle savedInstanceState) {
		 View rootView = null;
		 pref = getActivity().getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
		 boolean isRemember = pref.getBoolean("remember_LoginData", false);
		 if(isRemember == false){
			 rootView = inflater.inflate(R.layout.scancode_fragment, container, false);
			 notLogin = (ImageView)rootView.findViewById(R.id.notLogin);
			 notLogin.setVisibility(View.VISIBLE);
			 
		 }else{
			 rootView = inflater.inflate(R.layout.scancode_fragment, container, false);
			 scanButton = (Button) rootView.findViewById(R.id.scanButton);
			 resultTextView = (TextView)rootView.findViewById(R.id.result_string);
			 userNameTextView = (TextView)rootView.findViewById(R.id.user_name);
			 meetingNameTextView = (TextView)rootView.findViewById(R.id.meeting_name);
			 meetingPlaceTextView = (TextView)rootView.findViewById(R.id.meeting_place);
			 meetingTimeTextView = (TextView)rootView.findViewById(R.id.meeting_time);
			 scanButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(),CaptureActivity.class);
					startActivityForResult(intent, 0);
				}
			});
		 }
		 return rootView;
	 }
	 
	 @Override
	    public void onStart() {
	    	super.onStart();
	    	//list.setRefreshing();
	    }
	 	/**
	     * 获得碎片所在的上下文
	     * @return
	     */
	    private Context getContext() {
	    	return getActivity().getActionBar().getThemedContext();
	    }
	 
	 @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
			String qrdata = data.getExtras().getString("result");
			if(!qrdata.isEmpty()) {
				String[] data_split = qrdata.split("/");
				if(data_split.length==3){
					meeting_time = data_split[0];
					meeting_place = data_split[1];
					meeting_name = data_split[2];
					Log.d("meeting_data", meeting_name+meeting_place+meeting_time);
					String userName = pref.getString("userName", "");
					String url = "http://sqlweixin.duapp.com/app/sign_in.php?name="+userName+"&meeting_time="+meeting_time+"&meeting_place="+meeting_place+"&meeting_name="+meeting_name;
					Log.d("ScanCodeFragment onActivityResult", url);
					progressDialog = new ProgressDialog(getContext());
					Log.d("ScanCodeFragment Test", "这里");
					resultTextView.setText("正在加载，请稍后···");
					signIn(url);
					progressDialog.dismiss();
				}else{
					resultTextView.setText("该二维码信息不正确！");
				}
			}
			
	}
	 
	 public void signIn(final String url){
			new Thread(){
				public void run(){
					Message msg = new Message();
					InputStream is = ConnectPHP.getPHPData(url);
					sign_flag = ConnectPHP.getSignInData(is);
					Log.d("ScanCodeFragment signIn", sign_flag);
					if(!sign_flag.equals(null)){
						msg.what = 1;
					}else{
						msg.what = 0;
					}
					handler.sendMessage(msg);
				}
			}.start();
		}
	 private Handler handler = new Handler(){
		 @SuppressLint("HandlerLeak")
		public void handleMessage(Message msg){
			 if(msg.what==1){
				 String userName = pref.getString("userName", "");
				 userNameTextView.setText(userName);
				 meetingNameTextView.setText(meeting_name);
				 meetingPlaceTextView.setText(meeting_place);
				 meetingTimeTextView.setText(meeting_time);
				 resultTextView.setText(userName+"，"+sign_flag);
			 }else if(msg.what==0){
				 resultTextView.setText("网络出错，请重新扫码签到");
			 }
		 }
	 };
}
