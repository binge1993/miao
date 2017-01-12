package com.url;

import java.beans.Encoder;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpRequest;
import org.apache.http.client.HttpClient;

import com.r.HttpResult;
import com.s.Static;
import com.util.HttpUtil;

public class SubmitUrl extends BaseUrl {
	public static void main(String[] args) throws UnsupportedEncodingException {
		
//		System.out.println("\u78A7\u4E58\u70AD\u9006\u733E\u9798\u4E2D\u6B47\u4E14\u643A\u610F\u9001\u9760\u8015\u96EA\u4EF0");
		
		
	}
	
	private boolean isPhone;
	private SubmitUrl(){
	}
	public SubmitUrl(Long id,Long uid,String checkcode,boolean isPhone){
		this.isPhone = isPhone;
		if(this.isPhone){
			this.url = "http://ax.m.taobao.com/stock2.htm?f=w&id="+id+"&uid="+uid+"&an="+checkcode+"&skuId=&r="+(Static.currentTimeMillis());
		}else{
			this.url = "http://m.ajax.taobao.com/stock2.htm?_ksTS="+Static.currentTimeMillis()+"_222&cb=jsonp1&id="+id+"&an="+checkcode+"&skuId="+Static.currentTimeMillis();
		}
	}
	
	public HttpResult submit(HttpClient httpClient){
		return HttpUtil.httpGet(this.getDefaultUrl(), httpClient,"gb2312");
	}
	
}
