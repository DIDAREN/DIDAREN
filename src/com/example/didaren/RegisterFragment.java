package com.example.didaren;

import java.io.InputStream;

import com.example.didaren.R.string;
import com.example.didaren.modules.ImportantNewsFragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterFragment extends Fragment{
	
	private static final String ARG_SECTION_NUMBER = "section_number";
	private RadioGroup radioGroup;
	private EditText nameEditText;
	private EditText sidEditText;
	private EditText pwdEditText;
	private ImageView triAngleImageView;
	private EditText classEditText;//���
	private String[] teaDepartmentStrings = {"ѧԺ","ѧ����","����"};
	private Spinner teaDepartmentSpinner;
	private ArrayAdapter<String> teaDepartmentAdapter;
	private boolean flag = true;
	//private Spinner collegeSpinner;
	//private Spinner yearSpinner;
	private String name;
	private String sid;
	private String pwd;
	private String identity;
	private String college;
	private String year;
	private String major;
	private String department;
	private UserInfo user;
	private Button registerButton;
	private ProgressDialog progressDialog;
	private static String[] collegeStrings = {"","�����ѧѧԺ","��ԴѧԺ","���Ͽ�ѧ�뻯ѧ����ѧԺ","����ѧԺ","����ѧԺ","����������ռ���ϢѧԺ","��е�������ϢѧԺ","���ù���ѧԺ","�����ѧԺ","","��Ϣ����ѧԺ","��ѧ������ѧԺ","�����β�","�鱦ѧԺ","","�����봫ýѧԺ","��������ѧԺ","���˼����ѧԺ","�����ѧԺ","���Ĺ�ѧԺ","","","�Զ���ѧԺ"};
	private static String[] dixueStrings = {"����ѧ(���ذ�)"};
	private static String[] ziyuanStrings = {"��Դ���鹤��(���ذ�)","��Դ���鹤��(����/��������)","ʯ�͹���","","�����ѧ","��Դ���鹤��(ú��ú�������̷���)","��Դ���鹤��(��������뿪������)"};
	private static String[] caihuaStrings = {"���Ͽ�ѧ�빤��(ʵ���)","Ӧ�û�ѧ","���Ͽ�ѧ�빤��","���ϻ�ѧ"};
	private static String[] huanjingStrings = {"ˮ��Դ�뻷������(ʵ���)","ˮ����ˮ��Դ����","��������","�����ѧ(ݼӢ��)","","����ˮ��ѧ�빤��"};
	private static String[] gongchengStrings = {"���ʹ���(ʵ���)","���ʹ���","��ľ����","","���鼼���빤��","��ȫ����"};
	private static String[] dikongStrings = {"��������ѧ(ʵ���)","���鼼���빤��(�������������)","","","������Ϣ��ѧ�뼼��"};
	private static String[] jidianStrings = {"","������Ϣ����","��е������켰���Զ���","","��ҵ���","ͨ�Ź���"};
	private static String[] jingguanStrings = {"���̹���","����ѧ","�г�Ӫ��","���ʾ�����ó��","���ѧ","���ι���","��Ϣ��������Ϣϵͳ","�������","ͳ��ѧ","���̹���"};
	private static String[] waiyuanStrings = {"","Ӣ��"};
	private static String[] xingongStrings = {"","�������","","ң�п�ѧ�뼼��","������Ϣ��ѧ","��湤��","��Ϣ����"};
	private static String[] shuliStrings = {"","��ѧ��Ӧ����ѧ","����ѧ","��Ϣ������ѧ"};
	private static String[] tiyuStrings = {"","�������ָ�������"};
	private static String[] zhubaoStrings = {"","��ʯ�����Ϲ���ѧ","��Ʒ���(�鱦���η���)"};
	private static String[] yichuanStrings = {"","�㲥����ѧ","","����ѧ","�������","�Ӿ��������","����"};
	private static String[] gongguanStrings = {"","��ѧ","��������","������ҵ����","��Ȼ��������Դ����","������Դ����"};
	private static String[] mayuanStrings = {"","˼�����ν���"};
	private static String[] jikeStrings = {"","�������ѧ�뼼��","��Ϣ��ȫ","���繤��"};
	private static String[] lisiguangStrings = {"","�����ѧݼӢ��"};
	private static String[] zidonghuaStrings = {"","�Զ���","��ؼ���������"};
	
	public static RegisterFragment newInstance() {
		RegisterFragment fragment = new RegisterFragment();
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
		 Log.d("RegisterFragment", getActivity().toString());
		 View rootView = inflater.inflate(R.layout.register, container, false);
		 radioGroup = (RadioGroup)rootView.findViewById(R.id.radioGroup);
		 nameEditText = (EditText)rootView.findViewById(R.id.name);
		 sidEditText = (EditText)rootView.findViewById(R.id.id);
		 pwdEditText = (EditText)rootView.findViewById(R.id.password);
		 classEditText = (EditText)rootView.findViewById(R.id.classNum);
		 triAngleImageView = (ImageView)rootView.findViewById(R.id.trangle);
		 teaDepartmentSpinner = (Spinner)rootView.findViewById(R.id.teaDepart);
		 teaDepartmentAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item, teaDepartmentStrings);
		 teaDepartmentAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		 teaDepartmentSpinner.setAdapter(teaDepartmentAdapter);
		 teaDepartmentSpinner.setOnItemSelectedListener(new SpinnerSelectedListener());
		 
		 //collegeEditText = (EditText)rootView.findViewById(R.id.college);
		 //collegeSpinner = (Spinner)rootView.findViewById(R.id.college);
		 //collegeAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item, collegeStrings);
		 //collegeAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		 //collegeSpinner.setAdapter(collegeAdapter);
		 //collegeSpinner.setTag("college");
		 //collegeSpinner.setOnItemSelectedListener(new SpinnerSelectedListener());
		 //yearEditText = (EditText)rootView.findViewById(R.id.year);
		 //yearSpinner = (Spinner)rootView.findViewById(R.id.year);
		 //yearAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,yearStrings);
		 //yearAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		 //yearSpinner.setAdapter(yearAdapter);
		 //yearSpinner.setTag("year");
		 //yearSpinner.setOnItemSelectedListener(new SpinnerSelectedListener());
		 registerButton = (Button)rootView.findViewById(R.id.Register);
		 radioGroup.setOnCheckedChangeListener(radioGroupChangeListener);
		 return rootView;
	 }
	 
	 @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		registerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Editable nameEditable,sidEditable,pwdEditable;
				nameEditable = nameEditText.getText();
				sidEditable = sidEditText.getText();
				pwdEditable = pwdEditText.getText();
				name = nameEditable.toString();
				sid = sidEditable.toString();
				pwd = pwdEditable.toString();
				if(identity == "student"){
					Editable classNumeEditable = classEditText.getText();
					String classNum = classNumeEditable.toString();
					checkClassNum(classNum);
				}if(major!=""&&college!=""){
					sendData();
				}else{
					Toast.makeText(getContext(), "�������޷�ʶ��ѧԺ��רҵ��", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	public void sendData(){
		 if(name.equals("")){
				Toast.makeText(getContext(), "����Ϊ�գ�����д��", Toast.LENGTH_SHORT).show();
			}else if(sid.equals("")){
				Toast.makeText(getContext(), "ѧ��Ϊ�գ�����д��", Toast.LENGTH_SHORT).show();
			}else if(pwd.equals("")){
				Toast.makeText(getContext(), "����Ϊ�գ�����д��", Toast.LENGTH_SHORT).show();
			}else if(identity.equals("")){
				Toast.makeText(getContext(), "��ѡ��ѧ������ʦ��", Toast.LENGTH_SHORT).show();
			}
			else{
				String url = "http://sqlweixin.duapp.com/app/register.php?name="+name+"&sid="+sid+"&pwd="+pwd+"&college="+college+"&major="+major+"&year="+year+"&identity="+identity+"&department="+department;
				Log.d("RegisterFragment url", url);
				loginThread(url);
				if(flag == true){
					progressDialog = new ProgressDialog(getContext());
	  				progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	  				progressDialog.setMessage("����ע�ᡣ");
	  				progressDialog.show();
				}else if(flag == false){
					Toast.makeText(getContext(), "ϵͳ����������ע��~", Toast.LENGTH_SHORT).show();
				}
			}
	 }
	 
	 /**
	     * �����Ƭ���ڵ�������
	     * @return context
	     */
	    private Context getContext() {
	    	return getActivity().getActionBar().getThemedContext();
	    }
	    
	  /*
	   * ����php�ļ�
	   */
	  	public void loginThread(final String url){
	  		new Thread(){
	  			public void run(){
	  				Message msg = new Message();
	  				InputStream is = ConnectPHP.getPHPData(url);
	  				user = ConnectPHP.checkLogin(is);
	  				msg.what = user.getId().length();
	  				handler.sendMessage(msg);
	  			}
	  		}.start();
	  	}
	  	
	  	public Handler handler = new Handler(){
	  		public void handleMessage(Message msg) {
	  			if(msg.what<0){
	  				Toast.makeText(getContext(), "ע��ʧ�ܣ�������ע�ᡣ", Toast.LENGTH_LONG).show();
	  			}else{
	  				progressDialog.dismiss();
	  				Toast.makeText(getContext(), "ע��ɹ���", Toast.LENGTH_LONG).show();
	  				FragmentManager fragmentManager = getActivity().getFragmentManager();
					FragmentTransaction transaction = fragmentManager.beginTransaction();
					transaction.replace(R.id.container, ImportantNewsFragment.newInstance(1));
					transaction.commit();
					getActivity().invalidateOptionsMenu();
	  			}
	  		}
	  	};
	  	
	  class SpinnerSelectedListener implements OnItemSelectedListener{
		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			Log.d("Spinner", teaDepartmentStrings[position]);
			department =teaDepartmentStrings[position];
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		} 
	  }
	  
	  private RadioGroup.OnCheckedChangeListener radioGroupChangeListener = new RadioGroup.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			if(checkedId == R.id.std_button){
				Log.d("RegisterFragment Radio", "STD");
				teaDepartmentSpinner.setVisibility(View.GONE);
				classEditText.setVisibility(View.VISIBLE);
				triAngleImageView.setVisibility(View.GONE);
				identity = "student";
			}else if(checkedId == R.id.tea_button){
				Log.d("RegisterFragment Radio", "tea");
				teaDepartmentSpinner.setVisibility(View.VISIBLE);
				triAngleImageView.setVisibility(View.VISIBLE);
				classEditText.setVisibility(View.GONE);
				identity = "teacher";
			}
		}
	};
	
	public void checkClassNum(String classNum){
		if(classNum.length()!=6){
			Toast.makeText(getContext(), "�����д����", Toast.LENGTH_SHORT).show();
		}else{
			String collegeString = classNum.substring(0, 2);
			String majorString = classNum.substring(2, 3);
			String yearString = classNum.substring(3,5);
			int collegeInt,majorInt;
			collegeInt = Integer.parseInt(collegeString);
			majorInt = Integer.parseInt(majorString);
			college = collegeStrings[collegeInt];
			if(collegeInt==1){
				Log.d("RegisterFragment checkClassNum",collegeInt+"");
				major = dixueStrings[majorInt];
			}else if(collegeInt==2){
				major = ziyuanStrings[majorInt];
			}else if(collegeInt==3){
				major = caihuaStrings[majorInt];
			}else if(collegeInt==4){
				major = huanjingStrings[majorInt];
			}else if(collegeInt==5){
				major = gongchengStrings[majorInt];
			}else if(collegeInt==6){
				major = dikongStrings[majorInt];
			}else if(collegeInt==7){
				major = jidianStrings[majorInt];
			}else if(collegeInt==8){
				major = jingguanStrings[majorInt];
			}else if(collegeInt==9){
				major = waiyuanStrings[majorInt];
			}else if(collegeInt==11){
				major = xingongStrings[majorInt];
			}else if(collegeInt==12){
				major = shuliStrings[majorInt];
			}else if(collegeInt==13){
				major = tiyuStrings[majorInt];
			}else if(collegeInt==14){
				major = zhubaoStrings[majorInt];
			}else if(collegeInt==16){
				major = yichuanStrings[majorInt];
			}else if(collegeInt==17){
				major = gongguanStrings[majorInt];
			}else if(collegeInt==18){
				major = mayuanStrings[majorInt];
			}else if(collegeInt==19){
				major = jikeStrings[majorInt];
			}else if(collegeInt==20){
				major = lisiguangStrings[majorInt];
			}else if(collegeInt==23){
				major = zidonghuaStrings[majorInt];
			}else{
				major = "";
			}
			year = yearString;
		}
	}
}

