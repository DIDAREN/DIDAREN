package com.example.didaren;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.didaren.model.Items;
import com.example.didaren.model.News;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class GetDataFromWeb {
	
	public static final int RETRY_TIMES = 3;

	public static final HttpClient getHttpClient(){
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 6000);
		HttpConnectionParams.setSoTimeout(httpParams, 30000);
		return new DefaultHttpClient(httpParams);
	}
	
	private static final String getHtmlString(String url){
		
		String strHtml = "";
		HttpClient httpClient = null;
		HttpGet httpGet = null;
		
		int times = 0;
		do {
			try {
				httpClient = getHttpClient();
				httpGet = new HttpGet(url);
				HttpResponse response = httpClient.execute(httpGet);
				if (response.getStatusLine().getStatusCode() == 200) {
					byte[] bResult = EntityUtils.toByteArray(response.getEntity());
					if(bResult != null){
						strHtml = new String(bResult, "utf-8");
					}
				}
				break;
			 } catch (Exception e) {
				// TODO: handle exception
				 times++;
				// Toast.makeText(context, "TimeOut", Toast.LENGTH_SHORT).show();
				 
				 if (times < RETRY_TIMES) {
					 try {
						 Thread.sleep(1000);
					 } catch (InterruptedException e1){
						 
					 }
					 continue;
				 }
				 e.printStackTrace();
				
			 } finally {
				httpClient = null;
			 }
		} while(times < RETRY_TIMES);
		
		return strHtml;	
	}
	
	//显示新闻列表
	public static final ArrayList<News> moImportantNews(String url){
		
		ArrayList<News> newsList = new ArrayList<News>();
		String strParseHtml = getHtmlString(url);
		//parse
		Document doc = Jsoup.parse(strParseHtml);
		Elements arclistDivs = doc.getElementsByClass("arclist");
		for (Element arclistDiv : arclistDivs) {
			Elements links = arclistDiv.getElementsByTag("a");
			Element  link = links.first();
			Element  author = links.last();
			Elements eTime = arclistDiv.getElementsByClass("arcListInfo");
			String time = eTime.text();
			time = time.substring(time.length()-10, time.length());
			News news = new News(link.text(), link.attr("href"),author.text(),time);
			newsList.add(news);				
		}		
		return newsList;
	}
	
	//显示新闻内容
	public static final ArrayList<Items> moImportantNewsContent(String url){
		
		String strParseHtml = getHtmlString(url);
		ArrayList<Items> list = new ArrayList<Items>();
		
		Document doc = Jsoup.parse(strParseHtml);
		Elements pconDivs = doc.getElementsByClass("pcon");
		Element pconDiv = pconDivs.first();
		Elements ps = pconDiv.getElementsByTag("p");
		
		for (Element p : ps) {
			
			Items item1 = new Items(Items.TYPE_TEXT);
			String text = p.text();
			text = "        " + text;
			
			Elements strongElements = p.select("strong");
			if(!strongElements.isEmpty()) {
				item1.setMode(0);
				for(Element s : strongElements) {	
					Integer intStart  = text.indexOf(s.text());
					item1.setLocation(intStart);
					Integer intEnd = s.text().length() + intStart;
					item1.setLocation(intEnd);
				}
			}
			
			Elements bElements = p.select("b");
			if(!bElements.isEmpty()) {
				item1.setMode(0);
				for(Element b : bElements) {
					Integer intStart  = text.indexOf(b.text());
					item1.setLocation(intStart);
					Integer intEnd = b.text().length() + intStart;
					item1.setLocation(intEnd);
				}
			}
			
			item1.setContent(text);
			list.add(item1);
			
			Elements imgElements = p.select("img");
			if(!imgElements.isEmpty()) {
				for (Element e:imgElements) {
					Items item2 = new Items(e.attr("src"), Items.TYPE_IMAGE);
					list.add(item2);
				}
			}	
		}	
		return list;		
	}
	
	/**
	 * 从网站获取图片数据流
	 */
	public static InputStream getStreamFromURL(String imageURL) {
		InputStream in = null;
		try {
			Pattern pattern = Pattern.compile("(^[\u4e00-\u9fa5]*$)");
			Matcher matcher = pattern.matcher(imageURL);
			if (matcher.find()) {
				MatchResult matchResult = matcher.toMatchResult();
				String string = matchResult.group(0);
				imageURL = imageURL.replace(string, java.net.URLEncoder.encode(string));
			}
			
			
			
			URL url = new URL(imageURL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			in = connection.getInputStream();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return in;
	}
		
    /**
     * 判断网络是否可用
     */
    public static final boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectionManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()){
        	return true;
        } else {
        	return false;
        }
    }
}

