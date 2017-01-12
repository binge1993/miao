package com.url;

import org.apache.http.client.HttpClient;

import com.r.HttpResult;
import com.util.HttpUtil;

public class CheckAccountUrl extends BaseUrl {
	private String account;
	
	private CheckAccountUrl(){
		this.url = "https://unit.login.taobao.com/member/request_nick_check.do?_input_charset=utf-8";
	}
	public CheckAccountUrl(String account){
		this();
		this.account = account;
	}
	
	public HttpResult postStr(HttpClient httpClient){
		return HttpUtil.httpPost(getDefaultUrl(), httpClient, getPostStr(),"gb2312");
	}
	
	public String getPostStr(){
		return "username="+account;
	}
	
	@Override
	public String getDefaultUrl(){
		return url;
	}
}
