package com.example.didaren.task;

import java.util.ArrayList;

import com.example.didaren.model.News;
import com.example.didaren.sqlite.NewsDatabaseHelper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

public class DataWithDatabaseTask extends
		AsyncTask<String, Void, ArrayList<News>> {

	private int mode = -1;	
	private ArrayList<News> mList;	
	private NewsDatabaseHelper mHelper;
	
	public DataWithDatabaseTask(ArrayList<News> newsList, NewsDatabaseHelper helper) {
		mList = newsList;
		mHelper = helper;
	}
		
	@Override
	protected ArrayList<News> doInBackground(String... params) {
		// TODO Auto-generated method stub
		mode = Integer.parseInt(params[0]);
		switch (mode) {
		case 0:
			GetDataFromSQLite();
		case 1:
			StoreDataIntoSQLite();
			break;
		}		
		return null;
	}
		
	private void GetDataFromSQLite() {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from News", null);
       	if (cursor.moveToFirst()){
       		do {
       			String href = cursor.getString(cursor.getColumnIndex("href"));
       			String title = cursor.getString(cursor.getColumnIndex("title"));
       			String author = cursor.getString(cursor.getColumnIndex("author"));
       			String time = cursor.getString(cursor.getColumnIndex("time"));
       			News temp = new News(title, href, author, time);
       			mList.add(temp);
       		} while(cursor.moveToNext());
       	}
       	cursor.close();
       	db.close(); 
	}
	
	private void StoreDataIntoSQLite() {
		SQLiteDatabase db = mHelper.getWritableDatabase();
	   	db.execSQL("delete from News");
	   	int nCount = 0;
	   	for(News news : mList) {   		
	   		db.execSQL("insert into News(href,title,author,time) values(?,?,?,?)",
	   				new String[]{news.getHref(), news.getTitle(), news.getAuthor(), news.getTime()});
	   		nCount++;
	   		if((nCount % 20) == 0) break;	   		
	   	}
	   	db.close();  
	}

}
