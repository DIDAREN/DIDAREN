package com.example.didaren.model;

public class News {
	
	private String title;
	private String href;
	private String author;
	private String time;
	
	private String content = null;
	
	public News(String title, String href,String author,String time){
		this.title = title;
		this.href = href;
		this.author = author;
		this.time = time;		
	}
	
	public void SetNewsContent(String content) {
		this.content = content;
	}
	
	public String getContemt() {
		return content;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getHref(){
		return href;
	}
	
	public String getAuthor(){
		return author;
	}
	
	public String getTime(){
		return time;
	}

}
