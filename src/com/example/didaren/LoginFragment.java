package com.example.didaren;

import java.io.InputStream;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class LoginFragment extends Fragment {

	private static final String ARG_SECTION_NUMBER = "section_number";
	private Button stuButton,teaButton;
	private String identity;
	private EditText idEditText;
	private EditText pwdEditText;
	private LinearLayout collegeLayout,majorLayout,yearLayout,departmentLayout;
	private String inputId;
	private String inputPwd;
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	private UserInfo user;
	private Button registerButton;
	private ProgressDialog progressDialog;
	
    public static LoginFragment newInstance() {
    	LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	View rootView = null;
		pref = getActivity().getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
		boolean isRemember = pref.getBoolean("remember_LoginData", false);
		Log.d("LoginFragment",isRemember+"");
		if(isRemember==true){
			rootView = inflater.inflate(R.layout.user_data, container,false);
			EditText nameEditText = (EditText)rootView.findViewById(R.id.userName);
			EditText idEditText = (EditText)rootView.findViewById(R.id.userId);
			EditText identityEditText = (EditText)rootView.findViewById(R.id.userIdentity);
			String  userIdentityString = pref.getString("userIdentity", "");
			identityEditText.setText("��ݣ�"+userIdentityString);
			if (userIdentityString.equals("ѧ��")) {
				EditText collegeEditText = (EditText)rootView.findViewById(R.id.userCollege);
				EditText majorEditText = (EditText)rootView.findViewById(R.id.userMajor);
				EditText yearEditText = (EditText)rootView.findViewById(R.id.userYear);
				collegeLayout = (LinearLayout)rootView.findViewById(R.id.collegeLinearLayout);
				majorLayout = (LinearLayout)rootView.findViewById(R.id.majorLinearLayout);
				yearLayout = (LinearLayout)rootView.findViewById(R.id.yearLinearLayout);
				collegeLayout.setVisibility(View.VISIBLE);
				majorLayout.setVisibility(View.VISIBLE);
				yearLayout.setVisibility(View.VISIBLE);
				String userCollegeString = pref.getString("userCollege", "");
				String userMajorString = pref.getString("userMajor", "");
				int userYear = pref.getInt("userYear", 0);
				collegeEditText.setText("ѧԺ��"+userCollegeString);
				majorEditText.setText("רҵ��"+userMajorString);
				yearEditText.setText("�꼶��20"+userYear+"��");
			}else if(userIdentityString.equals("��ʦ")){
				EditText departmentEditText = (EditText)rootView.findViewById(R.id.userDepartment);
				departmentLayout = (LinearLayout)rootView.findViewById(R.id.departmentLinearLayout);
				departmentLayout.setVisibility(View.VISIBLE);
				String userDepartmentString = pref.getString("userDepartment", "");
				departmentEditText.setText("���ţ�"+userDepartmentString);
			}else{
				Toast.makeText(getContext(), "ϵͳ����������", Toast.LENGTH_SHORT).show();
			}
			Button logOffButton = (Button)rootView.findViewById(R.id.logOff);
			String userIdString = pref.getString("userId", "");
			int userYearInt = pref.getInt("userYear", 0);
			String userYearString = Integer.toString(userYearInt);
			String userNameString = pref.getString("userName", "");
			nameEditText.setText("������"+userNameString);
			idEditText.setText("ѧ�ţ�"+userIdString);
			/*
			 * �˳���¼
			 */
			logOffButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					SharedPreferences.Editor editor = getActivity().getSharedPreferences("userInfo", Activity.MODE_ENABLE_WRITE_AHEAD_LOGGING).edit();
					editor.clear().commit();
					FragmentManager fragmentManager = getActivity().getFragmentManager();
					FragmentTransaction transaction = fragmentManager.beginTransaction();
					transaction.replace(R.id.container, LoginFragment.newInstance());
					transaction.commit();
					getActivity().invalidateOptionsMenu();
				}
			});
		}else{
			rootView = inflater.inflate(R.layout.login, container, false);
			//radioGroup = (RadioGroup)rootView.findViewById(R.id.radioGroup);
			//stuRadioButton = (RadioButton)rootView.findViewById(R.id.std_button);
			//teaRadioButton = (RadioButton)rootView.findViewById(R.id.tea_button);
			idEditText = (EditText)rootView.findViewById(R.id.LoginId);
			pwdEditText = (EditText)rootView.findViewById(R.id.password);
			stuButton = (Button)rootView.findViewById(R.id.stuButton);
			teaButton = (Button)rootView.findViewById(R.id.teaButton);
			registerButton = (Button)rootView.findViewById(R.id.Register);
			//radioGroup.setOnCheckedChangeListener(radioGroupChangeListener);
			stuButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Editable input_id,input_pwd;
					input_id = idEditText.getText();
					inputId = input_id.toString();
					input_pwd = pwdEditText.getText();
					inputPwd = input_pwd.toString();
					identity = "ѧ��";
					sendData(inputId,inputPwd);
				}
			});
			teaButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Editable input_id,input_pwd;
					input_id = idEditText.getText();
					inputId = input_id.toString();
					input_pwd = pwdEditText.getText();
					inputPwd = input_pwd.toString();
					identity = "��ʦ";
					sendData(inputId,inputPwd);
				}
			});
			registerButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.d("LoginFragment", getActivity().toString());
					FragmentManager fragmentManager = getActivity().getFragmentManager();
					FragmentTransaction transaction = fragmentManager.beginTransaction();
					transaction.replace(R.id.container, RegisterFragment.newInstance());
					transaction.addToBackStack(null);
					transaction.commit();
				}
			});
		}
        return rootView;
    }
    
    private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if(msg.what == 0 ) {
				progressDialog.dismiss();
				Toast.makeText(getContext(), "�˺�/�������", Toast.LENGTH_SHORT).show();
				idEditText.setText("");
				pwdEditText.setText("");
			} else {
				/********************���û��˺�����洢������********************/
				//editor = pref.edit();
				SharedPreferences.Editor editor = getActivity().getSharedPreferences("userInfo", Activity.MODE_PRIVATE).edit();
				editor.putBoolean("remember_LoginData", true);
				Log.d("LoginFragment editor", user.getName());
				Log.d("LoginFragment editor", ""+user.getId());
				if(user.getIdentity()=="ѧ��"){
					editor.putString("userName", user.getName());
					editor.putString("userCollege", user.getCollege());
					editor.putInt("userYear", user.getYear());
					editor.putString("userId", user.getId());
					editor.putString("userMajor", user.getMajor());
					editor.putString("userIdentity", user.getIdentity());
				}else if(user.getIdentity()=="��ʦ"){
					editor.putString("userName", user.getName());
					editor.putString("userId", user.getId());
					editor.putString("userIdentity", user.getIdentity());
					editor.putString("userDepartment", user.getDepartment());
				}else{
					Toast.makeText(getContext(), "ϵͳ����������", Toast.LENGTH_SHORT).show();
				}
				editor.commit();
				//intent.putExtra("user_id",""+user_id);
				//intent.putExtra("url", "http://www.xuegong.cug.edu.cn/category/news/page/");
				//intent.putExtra("page", "1");
				//startActivity(intent);
				progressDialog.dismiss();
				FragmentManager fragmentManager = getActivity().getFragmentManager();
				FragmentTransaction transaction = fragmentManager.beginTransaction();
				transaction.replace(R.id.container, LoginFragment.newInstance());
				transaction.commit();
				getActivity().invalidateOptionsMenu();
			}
		}
	};
	
	public void sendData(String id,String pwd){
		if(id.equals("")){
			Toast.makeText(getContext(), "�˺�Ϊ�գ�����д��", Toast.LENGTH_SHORT).show();
		}else if(pwd.equals("")){
			Toast.makeText(getContext(), "����Ϊ�գ�����д��", Toast.LENGTH_SHORT).show();
		}else if(identity == ""){
			Toast.makeText(getContext(), "��ѡ����ݵ�¼", Toast.LENGTH_SHORT).show();
		}else{
			progressDialog = new ProgressDialog(getContext());
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage("���ڵ�¼");
			progressDialog.show();
			String url = null;
			if(identity == "ѧ��"){
				url = "http://sqlweixin.duapp.com/app/stu_check_login.php?sid="+id+"&pwd="+pwd;
			}else if(identity == "��ʦ"){
				url = "http://sqlweixin.duapp.com/app/tea_check_login.php?sid="+id+"&pwd="+pwd;
			}
			Log.d("LoginFragment sendData", url);
			loginThread(url);
		}
	}
	
	//����php�ļ�
	public void loginThread(final String url){
		new Thread(){
			public void run(){
				Message msg = new Message();
				InputStream is = ConnectPHP.getPHPData(url);
				user = ConnectPHP.checkLogin(is);
				Log.d("LoginFragment", msg.what+"");
				msg.what = user.getId().length();
				Log.d("LoginFragment", user.getId());
				handler.sendMessage(msg);
			}
		}.start();
	}
		
    /**
     * 	 �����Ƭ���ڵ�������
	 * @return
	 */
	private Context getContext() {
	   return getActivity().getActionBar().getThemedContext();
	}
	   
}
