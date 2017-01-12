package com.url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import com.r.HttpResult;
import com.s.Data;
import com.util.HttpUtil;

public class LoginUrl extends BaseUrl {

	private String account;
	private String pwd;
	private String checkcode;
	private boolean isPhone;
	
	private LoginUrl(){
	}
	public LoginUrl(String account,String pwd,String checkcode,boolean isPhone){
		this();
		this.account = account;
		this.pwd = pwd;
		this.checkcode = checkcode;
		this.isPhone = isPhone;
		this.url = "https://login.taobao.com/member/login.jhtml";
		System.out.println("this:"+getPostStr());
	}
	public String getPostStr(){
		String acc = null;
		try {
			acc = URLEncoder.encode(account,"gbk");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "TPL_username="+acc+"&TPL_password="+pwd+(checkcode==null||checkcode.length()==0?"":"&TPL_checkcode="+checkcode)+"&action=Authenticator&event_submit_do_login=anything&loginType=4";
	}
	
	public HttpResult post(HttpClient httpclient){
		return HttpUtil.httpPost(getDefaultUrl(), httpclient, getPostStr(),"gb2312");
	}
}
