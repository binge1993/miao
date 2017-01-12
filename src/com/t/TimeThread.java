package com.t;

import java.text.SimpleDateFormat;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import com.alibaba.fastjson.JSONObject;
import com.r.HttpResult;
import com.s.Static;
import com.util.FileUtil;
import com.util.HttpUtil;

public class TimeThread extends Thread {
	@Override
	public void run() {
		super.run();
		HttpClient httpClient = new DefaultHttpClient();
		while(true){
			HttpResult hResult = HttpUtil.httpGet("http://api.m.taobao.com/rest/api3.do?api=mtop.common.getTimestamp", httpClient,"gb2312");
			if(hResult.isSuccess()){
				JSONObject jsonObj = JSONObject.parseObject(hResult.getStr());
				long t = jsonObj.getJSONObject("data").getLong("t");
				long shicha = t-System.currentTimeMillis();
				long oldShicha = Static.shicha;
				Static.shicha = shicha;
				if(shicha-oldShicha>500||shicha-oldShicha<-500){
					FileUtil.addLogStr("时差:"+(shicha-oldShicha)+"\r\n","C:/log.txt");
				}
			}else{
				System.out.println("获取系统时间有误");
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
