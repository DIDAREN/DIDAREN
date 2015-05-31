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
	private EditText classEditText;//班号
	private String[] teaDepartmentStrings = {"学院","学工处","教务处"};
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
	private static String[] collegeStrings = {"","地球科学学院","资源学院","材料科学与化学工程学院","环境学院","工程学院","地球物理与空间信息学院","机械与电子信息学院","经济管理学院","外国语学院","","信息工程学院","数学与物理学院","体育课部","珠宝学院","","艺术与传媒学院","公共管理学院","马克思主义学院","计算机学院","李四光学院","","","自动化学院"};
	private static String[] dixueStrings = {"地质学(基地班)"};
	private static String[] ziyuanStrings = {"资源勘查工程(基地班)","资源勘查工程(固体/油气方向)","石油工程","","海洋科学","资源勘查工程(煤及煤层气工程方向)","资源勘查工程(矿产调查与开发方向)"};
	private static String[] caihuaStrings = {"材料科学与工程(实验班)","应用化学","材料科学与工程","材料化学"};
	private static String[] huanjingStrings = {"水资源与环境工程(实验班)","水文与水资源工程","环境工程","生物科学(菁英班)","","地下水科学与工程"};
	private static String[] gongchengStrings = {"地质工程(实验班)","地质工程","土木工程","","勘查技术与工程","安全工程"};
	private static String[] dikongStrings = {"地球物理学(实验班)","勘查技术与工程(勘查地球物理方向)","","","地球信息科学与技术"};
	private static String[] jidianStrings = {"","电子信息工程","机械设计制造及其自动化","","工业设计","通信工程"};
	private static String[] jingguanStrings = {"工商管理","经济学","市场营销","国际经济与贸易","会计学","旅游管理","信息管理与信息系统","财务管理","统计学","工程管理"};
	private static String[] waiyuanStrings = {"","英语"};
	private static String[] xingongStrings = {"","软件工程","","遥感科学与技术","地理信息科学","测绘工程","信息工程"};
	private static String[] shuliStrings = {"","数学与应用数学","物理学","信息与计算科学"};
	private static String[] tiyuStrings = {"","社会体育指导与管理"};
	private static String[] zhubaoStrings = {"","宝石及材料工艺学","产品设计(珠宝首饰方向)"};
	private static String[] yichuanStrings = {"","广播电视学","","音乐学","环境设计","视觉传达设计","动画"};
	private static String[] gongguanStrings = {"","法学","行政管理","公共事业管理","自然地理与资源环境","土地资源管理"};
	private static String[] mayuanStrings = {"","思想政治教育"};
	private static String[] jikeStrings = {"","计算机科学与技术","信息安全","网络工程"};
	private static String[] lisiguangStrings = {"","地球科学菁英班"};
	private static String[] zidonghuaStrings = {"","自动化","测控技术与仪器"};
	
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
					Toast.makeText(getContext(), "输入班号无法识别学院或专业。", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	public void sendData(){
		 if(name.equals("")){
				Toast.makeText(getContext(), "姓名为空，请填写。", Toast.LENGTH_SHORT).show();
			}else if(sid.equals("")){
				Toast.makeText(getContext(), "学号为空，请填写。", Toast.LENGTH_SHORT).show();
			}else if(pwd.equals("")){
				Toast.makeText(getContext(), "密码为空，请填写。", Toast.LENGTH_SHORT).show();
			}else if(identity.equals("")){
				Toast.makeText(getContext(), "请选择学生或老师。", Toast.LENGTH_SHORT).show();
			}
			else{
				String url = "http://sqlweixin.duapp.com/app/register.php?name="+name+"&sid="+sid+"&pwd="+pwd+"&college="+college+"&major="+major+"&year="+year+"&identity="+identity+"&department="+department;
				Log.d("RegisterFragment url", url);
				loginThread(url);
				if(flag == true){
					progressDialog = new ProgressDialog(getContext());
	  				progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	  				progressDialog.setMessage("正在注册。");
	  				progressDialog.show();
				}else if(flag == false){
					Toast.makeText(getContext(), "系统出错，请重新注册~", Toast.LENGTH_SHORT).show();
				}
			}
	 }
	 
	 /**
	     * 获得碎片所在的上下文
	     * @return context
	     */
	    private Context getContext() {
	    	return getActivity().getActionBar().getThemedContext();
	    }
	    
	  /*
	   * 链接php文件
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
	  				Toast.makeText(getContext(), "注册失败，请重新注册。", Toast.LENGTH_LONG).show();
	  			}else{
	  				progressDialog.dismiss();
	  				Toast.makeText(getContext(), "注册成功！", Toast.LENGTH_LONG).show();
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
			Toast.makeText(getContext(), "班号填写错误。", Toast.LENGTH_SHORT).show();
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

