package com.util;

import java.awt.EventQueue;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;

import com.frame.Miao;

public class MiaoFactory {
	
	public static boolean createMiao(DefaultHttpClient httpclient,HttpContext localContext){
		final DefaultHttpClient client = httpclient;
		final HttpContext context = localContext;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Miao window = new Miao(client,context);
					window.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return true;
	}

}
