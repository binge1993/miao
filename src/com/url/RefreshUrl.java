package com.url;

import org.apache.http.client.HttpClient;

import com.r.HttpResult;
import com.s.Static;
import com.util.HttpUtil;

public class RefreshUrl extends BaseUrl {
	private boolean isPhone;
	private RefreshUrl(){
		
	}
	public RefreshUrl(Long id,Long uid,boolean isPhone){
		this();
		this.isPhone = isPhone;
		if(isPhone){
			this.url = "http://ax.m.taobao.com/qst.htm?f=w&id="+id+"&uid=&"+uid+"&r="+Static.currentTimeMillis();
			//this.url = "http://localhost:8080/ttt.do";
			//1031299495
			//1031299495������vs�ǻ�
		}else{
			this.url = "http://m.ajax.taobao.com/qst.htm?f=2&_ksTS="+System.currentTimeMillis()+"_886&cb=jsonp888&id="+id;
		}
	}
	public HttpResult doGet(HttpClient httpClient){
		return HttpUtil.httpGet(getDefaultUrl(), httpClient,"utf-8");
	}
}
