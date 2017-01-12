package com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import com.r.HttpResult;

public class HttpUtil {
	
	
	public static synchronized HttpResult httpPost(String url,HttpClient httpClient,String postStr,String enc){
		HttpResult result = new HttpResult();
		HttpPost httpPost = new HttpPost(url);
		StringEntity reqEntity;
		try {
			reqEntity = new StringEntity(postStr);
			reqEntity.setContentType("application/x-www-form-urlencoded");
			httpPost.setEntity(reqEntity);
			HttpResponse response = httpClient.execute(httpPost);
			result.setHeaders(response.getAllHeaders());
			result.setCode(response.getStatusLine().getStatusCode());
			if(result.getCode()!=200){
				return result;
			}
			HttpEntity entity = response.getEntity();
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), enc));
			String str = "";
			String line = null;
			while ((line = reader.readLine()) != null) {
				str += line;
			}
			result.setStr(str);
			reader.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			System.out.println("客户端协议异常");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static synchronized HttpResult httpGet(String url,HttpClient httpClient,String enc){
		HttpResult hgr = new HttpResult();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
			hgr.setCode(response.getStatusLine().getStatusCode());
			hgr.setHeaders(response.getAllHeaders());
			if(hgr.getCode()!=200){
				return hgr;
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), enc));
			String str = "";
			String line = null;
			while ((line = reader.readLine()) != null) {
				str += line;
			}
			reader.close();
			hgr.setStr(str);
		} catch (ClientProtocolException e1) {
			System.out.println("客户端协议异常");
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally{
		}
		return hgr;
	}
}
