package com.t;

import java.beans.Encoder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.http.client.utils.URLEncodedUtils;

import com.alibaba.fastjson.JSONObject;
import com.util.FileUtil;
import com.util.MyUtil;

public class Test {
	
/*	public static void main(String[] args) throws Exception {
		
		StringBuffer buff = new StringBuffer();
		StringBuffer buff2 = new StringBuffer();
		File file = new File("C:/a.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = 	br.readLine();
		while(line!=null){
			if(line.length()!=0){
				buff.append(line+"\r\n");
			}
		}
		FileUtil.writeFile(buff.toString(), "C:/buff.txt");
	}*/
	
	
	public static void main(String[] args) throws UnsupportedEncodingException {
	}

}
