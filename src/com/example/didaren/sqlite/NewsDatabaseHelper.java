package com.example.didaren.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class NewsDatabaseHelper extends SQLiteOpenHelper {

	//private Context mContext;
	
	//新闻表结构
	public static final String CREATE_NEWS = "create table News ("
			+ "id integer primary key  autoincrement,"
			+ "href text,"
			+ "title text,"
			+ "author text,"
			+ "time text)";
	
	
	
	public NewsDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
		//mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_NEWS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("drop table if exists News");
		onCreate(db);
	}
}
