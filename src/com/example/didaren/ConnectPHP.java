package com.example.didaren;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.ParseException;
import android.util.Log;
import android.widget.Toast;

public class ConnectPHP{
	static InputStream is;
	public static InputStream getPHPData(String url){
		try{
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(url);
              HttpResponse response = httpclient.execute(httpget);
              HttpEntity entity = response.getEntity();
              is = entity.getContent();
		}catch(Exception e){
			Log.e("first log_tag", "Error converting result " + e.toString());
			e.printStackTrace();
		}
		return is;
	}
	
	//读取php返回inputstream,成功则返回用户
	public static UserInfo checkLogin(InputStream is){
		String result = null;
		String userName = null;
		String userCollege = null;
		int userYear = 0;
		String userId = null;
		String userIdentity = null;
		String userMajor = null;
		String userDepartment = null;
		UserInfo userInfo;
		try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            sb.append(reader.readLine() + "\n");
            String line = "0";
            while ((line = reader.readLine()) != null) {
               sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        } catch (Exception ex) {
            Log.e("second log_tag", "Error converting result " + ex.toString());
        }
        try {
        	JSONArray jArray = new JSONArray(result);
            JSONObject json_data = null;
            for (int i = 0; i < jArray.length(); i++) {
               json_data = jArray.getJSONObject(i);
               userName = json_data.getString("name");
               userId = json_data.getString("sid");
               Log.d("ConnectPHP json_data length", json_data.length()+"");
               if(json_data.length()==14){
            	   userIdentity = "学生";
	               userCollege = json_data.getString("college");
	               userMajor = json_data.getString("major");
	               userYear = json_data.getInt("year");
               }else if(json_data.length()==10){
            	   userIdentity = "老师";
            	   userDepartment = json_data.getString("department");
               }
            }
        } catch (JSONException e11) {
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        if(userIdentity==null){
        	userInfo = new UserInfo("", "", "", "", "", 0, "");
        	Log.d("ConnectPHP", "userIdentity null");
        }else{
        	userInfo = new UserInfo(userName,userId,userIdentity,userCollege,userMajor,userYear,userDepartment);
        }
		return userInfo;
	}
	
	public static String getSignInData(InputStream is){
		String result = null;
		try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            sb.append(reader.readLine() + "\n");
            String line = "0";
            while ((line = reader.readLine()) != null) {
               sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
            result = result.toLowerCase();
            result = result.trim();
            Log.d("重要", result);
            if(result.equals("1")){
            	result = "签到成功！";
            }else if(result.equals("2")){
            		result = "您已签到，无需重复签到。";
            	}
            else{
            	result = "网络出错，请重新扫码签到。";
            }
        } catch (Exception ex) {
            Log.e("second log_tag", "Error converting result " + ex.toString());
        }
       return result; 
	}
}
