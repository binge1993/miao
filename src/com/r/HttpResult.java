package com.r;

import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSONObject;

public class HttpResult {
	private int code = -1;
	private String str;
	private Header[] headers;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	
	public boolean isSuccess(){
		return code==200;
	}
	public void setHeaders(Header[] headers) {
		this.headers = headers;
	}
	public Header[] getHeaders(){
		return headers;
	}
	public JSONObject parseObject(){
		String cache = this.getStr();
		cache = cache.substring(cache.indexOf("{"), cache.lastIndexOf("}")+1);
		JSONObject jsonObj = JSONObject.parseObject(cache);
		return jsonObj;
	}
	public Document parseDoc(){
		Document doc = Jsoup.parse(this.getStr());
		return doc;
	}
}
