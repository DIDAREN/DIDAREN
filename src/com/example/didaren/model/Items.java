package com.example.didaren.model;

import java.util.ArrayList;

public class Items {
	/**
	 * TYPE_TEXT��ʾ�ö���Ϊ�ı���Ϣ
	 */
	public static final int TYPE_TEXT = 0;
	/**
	 * TYPE_IMAGE��ʾ�ö���ΪͼƬ 
	 */
	public static final int TYPE_IMAGE = 1;
	
	private String mContent;
	private int mType;
	
	/**
	 * mMode����ı��ֶ����Ƿ��������ʽ��-1����������0�����ֶ����мӴ�����
	 */
	private int mMode = -1;
	/**
	 * mLocation��¼�Ӵ����ֵ�λ��
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
