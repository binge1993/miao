package com.url;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.client.HttpClient;
import org.apache.http.client.utils.URIBuilder;

import com.alibaba.fastjson.JSONObject;
import com.r.HttpResult;
import com.util.HttpUtil;

public class SecKillBigBuyUrl extends BaseUrl {
	
	
	private String postStr;
	private SecKillBigBuyUrl(){
	}
	public SecKillBigBuyUrl(String tb_token,String miaoUrl,Long id,Double price,String timkn,String timk,String secKillEncryptStr,String checkcode,String current_price){
		this();
		this.url = "http://buy.taobao.com/auction/secKillBigBuy.htm";
		int quantity = 1;
		try {
			miaoUrl =  URLEncoder.encode(miaoUrl,"gbk");
			checkcode = URLEncoder.encode(checkcode, "gbk");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		postStr = "onekey="
				+"&gmtCreate="
		+"&checkCodeIds="
		+"&secStrNoCCode="
		+"&tb_token="+tb_token
		+"&item_url_refer=http://miao.item.taobao.com/"+id+".htm"
		+"&item_id="+id
		+"&item_id_num="+id
		+"&auction_type=b"
		+"&from=item_detail"
		+"&frm="
		+"&current_price="+current_price
		+"&activity="
		+"&auto_post1="
		+"&quantity="+quantity
		+"&skuId="
		+"&skuInfo="
		+"&buyer_from"
		+"&chrgeTypeId"
		+"&"+timkn+"="+timk
		+"&answer=aaa"
		+"&secKillEncryptStr="+secKillEncryptStr
		+"&event_submit_do_buy="+1
		+"&action=buynow%2FsecKillBuyNowAction"
		;
	}
	public String getPostStr(){
		return postStr;
	}
	
	public HttpResult doPost(HttpClient httpClient){
		return HttpUtil.httpPost(getDefaultUrl(), httpClient, getPostStr(),"gb2312");
	}
}
