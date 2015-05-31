package com.example.didaren.task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;

import com.example.didaren.GetDataFromWeb;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class BitmapLoaderTask extends AsyncTask<String, Void, Bitmap> {

	/**
	 * �ڴ�ͼƬ�����û���
	 */
	private HashMap<String, SoftReference<Bitmap>> imageCache = null;
	/**
	 * ��ImageViewʹ�� WeakReference�����õ�Ŀ����ȷ�� AsyncTask�������ϵͳ��ImageView��Ҫʱ�����������
	 */
	private WeakReference<ImageView> imageViewReference;
	
	private Context mContext;
	
	public BitmapLoaderTask(ImageView imageView, Context context) {
		imageCache = new HashMap<String, SoftReference<Bitmap>>();
		imageViewReference = new WeakReference<ImageView>(imageView);
		mContext = context;
	}
	
	@Override
	protected Bitmap doInBackground(String... params) {
		// TODO Auto-generated method stub
		String strImageURL = params[0];
		//���ڴ滺���У��򷵻�Bitmap���� 
		if(imageCache.containsKey(strImageURL)) {
			Log.d("Image", "ContainsCache");
			SoftReference<Bitmap> reference = imageCache.get(strImageURL);
			Bitmap bitmap = reference.get();
			if(bitmap != null) {
				return bitmap;
			}
		} else {
			//�ڱ��ػ����в���
			Log.d("Image", "LocalFile");
			String strBitmapName = strImageURL.substring(strImageURL.lastIndexOf("/") + 1);
			Log.d("Image", "LocalFile: " + "cacheName:" + strBitmapName);
			File cacheDir = mContext.getCacheDir();
			Log.d("Image", "LocalFile: " + "cacheDir: " + cacheDir.getPath());
			File[] cacheFiles = cacheDir.listFiles();
			Log.d("Image", "LocalFile: " + "cacheFiles: " + cacheFiles.toString());
			int i=0;
			if (cacheFiles != null) {
				for( ;i<cacheFiles.length; i++) {
					if(strBitmapName.equals(cacheFiles[i].getName())) {
						break;
					}
				}
				if (i < cacheFiles.length) {
					String strCachePath = cacheDir.getPath() + "/";
					return BitmapFactory.decodeFile(strCachePath + strBitmapName);
				}
			}	
		}
		//�������л�ȡͼƬ
		InputStream bitmapInputStream = GetDataFromWeb.getStreamFromURL(strImageURL);
		Bitmap bitmap = BitmapFactory.decodeStream(bitmapInputStream);
		//Log.d("Image", bitmap.toString());
		if(bitmap != null) {
			imageCache.put(strImageURL, new SoftReference<Bitmap>(bitmap));
		
			File cacheDir = mContext.getCacheDir();
			if (!cacheDir.exists()) {
				cacheDir.mkdirs();
			}
			File bitmapFile = new File(cacheDir.getPath() + "/" + strImageURL.substring(strImageURL.lastIndexOf("/") + 1));
			if (!bitmapFile.exists()) {
				try {
					bitmapFile.createNewFile();
				} catch (IOException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			FileOutputStream fileOutputStream;
			try {
				fileOutputStream = new FileOutputStream(bitmapFile);
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
				fileOutputStream.close();
			} catch (FileNotFoundException e) {
				// TODO: handle exception
				e.printStackTrace();
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}	
		return bitmap;	
	}
	
	@Override
	protected void onPostExecute(Bitmap result) {
		if (imageViewReference != null && result != null) {
			ImageView imageView = imageViewReference.get();
			if(imageView != null) {
				imageView.setImageBitmap(result);
			}
		}
	}


}
