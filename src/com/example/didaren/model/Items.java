package com.example.didaren.model;

import java.util.ArrayList;

public class Items {
	/**
	 * TYPE_TEXT表示该段落为文本信息
	 */
	public static final int TYPE_TEXT = 0;
	/**
	 * TYPE_IMAGE表示该段落为图片 
	 */
	public static final int TYPE_IMAGE = 1;
	
	private String mContent;
	private int mType;
	
	/**
	 * mMode标记文本字段中是否有特殊格式：-1代表正常；0代表字段中有加粗文字
	 */
	private int mMode = -1;
	/**
	 * mLocation记录加粗文字的位置
	 */
	private ArrayList<Integer> mLocation = new ArrayList<Integer>();

	public Items(String content, int type) {
		mContent = content;
		mType = type;
	}
	
	public Items(int type) {
		mType = type;
	}
	
	
	public void setMode(int mode) {
		mMode = mode;
	}
	
	public int getMode() {
		return mMode;
	}
	
	public ArrayList<Integer> getLocation() {
		return mLocation;
	}
	
	public void setLocation(Integer location) {
		mLocation.add(location);
	}
	
	public String getContent() {
		return mContent;
	}
	
	public void setContent(String content) {
		mContent = content;
	}
	
	public int getType() {
		return mType;
	}

}
