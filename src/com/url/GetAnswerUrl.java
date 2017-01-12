package com.url;

import org.apache.http.client.HttpClient;

import com.r.HttpResult;
import com.util.HttpUtil;

public class GetAnswerUrl extends BaseUrl {
	private String tip;
	private int len;
	private String url2;
	private GetAnswerUrl(){
		this.url = "http://localhost:8080/miao";
	}
	public GetAnswerUrl(String tip,int len,String url2){
		this();
		this.tip = tip;
		this.len = len;
		this.url2 = url2;
	}
	public HttpResult doGet(HttpClient httpClient){
		String u = this.getDefaultUrl()+"?len="+len+"&tip="+tip+"&url="+url2;
		System.out.println(u);
		return HttpUtil.httpGet(u, httpClient,"utf-8");
	}
}
