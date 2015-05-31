package com.example.didaren.model;

import java.lang.ref.WeakReference;

import com.example.didaren.task.BitmapLoaderTask;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

public class AsyncDrawable extends BitmapDrawable {

	private final WeakReference<BitmapLoaderTask> bitmapLoaderTaskReference;
	

	public AsyncDrawable(Resources res, Bitmap bitmap, BitmapLoaderTask bitmapLoaderTask) {  
	        super(res, bitmap);  
	        bitmapLoaderTaskReference =  
	             new WeakReference<BitmapLoaderTask>(bitmapLoaderTask);  
	 }

	public BitmapLoaderTask getBitmapWorkerTask() {  
	         return bitmapLoaderTaskReference.get();  
	} 
}
