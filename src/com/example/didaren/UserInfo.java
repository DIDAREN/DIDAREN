package com.example.didaren;

import android.util.Log;

public class UserInfo {
	private String name = null;
	private String college= null;
	private String major= null;
	private String identity= null;
	private String department= null;
	private int year = 0;
	private String id = null;
	
	public UserInfo(String name,String id,String identity,String college,String major,int year,String department){
		this.name = name;
		this.id = id;
		this.identity = identity;
		if(this.identity == "学生"){
			this.college = college;
			this.major = major;
			this.year = year;
		}else if(this.identity == "老师"){
			this.department = department;
		}
		Log.d("UserInfo",this.name+this.id+this.identity+this.college+this.major+this.year+this.department);
	}
	public void setName(String name){
		this.name = name;
	}
	
	public void setIdentity(String identity){
		this.identity = identity;
	}
	
	public void setMajor(String major){
		this.major = major;
	}
	
	public void setCollege(String college){
		this.college = college;
	}
	
	public void setYear(int year){
		this.year = year;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public void setDepartment(String department){
		this.department = department;
	}
	
	public String getName(){
		return name;
	}
	
	public String getIdentity(){
		return identity;
	}
	
	public String getMajor(){
		return major;
	}
	
	public String getCollege(){
		return college;
	}
	
	public int getYear(){
		return year;
	}
	
	public String getId(){
		return id;
	}
	
	public String getDepartment(){
		return department;
	}
}
