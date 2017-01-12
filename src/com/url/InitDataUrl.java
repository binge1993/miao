package com.url;

import org.apache.http.client.HttpClient;

import com.r.HttpResult;
import com.util.HttpUtil;

public class InitDataUrl extends BaseUrl {
	private HttpResult httpResult;
	/*
	 * HttpResult result = HttpUtil.httpGet("http://m.ajax.taobao.com/mdata.htm", httpClient,"gb2312");
				if(result.isSuccess()){
					String str = result.getStr();
					JSONObject jsonObj = result.parseObject();
					JSONObject viewer = jsonObj.getJSONObject("viewer");
					token = viewer.getString("token");
					String tnik = viewer.getString("tnik");
					frame.setTitle("√Î…±ΩÁ√Ê-"+tnik);
				}else{
					log("initToken ß∞‹");
				}
	 */
	public InitDataUrl(){
		this.url = "http://m.ajax.taobao.com/mdata.htm";
	}
	public HttpResult doGet(HttpClient httpClient){
		httpResult = HttpUtil.httpGet(this.getDefaultUrl(), httpClient, "gb2312");
		return httpResult;
	}
}
