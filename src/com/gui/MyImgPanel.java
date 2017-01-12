package com.gui;

import javax.swing.JLabel;

public class MyImgPanel extends JLabel {
	public MyImgPanel(){
		super();
		this.setOpaque(true);
	}
	public void drawImage(String url){
		final String u = url;
		new Thread(new Runnable() {
			@Override
			public void run() {
				setText("<html><img width="+getWidth()+" height="+getHeight()+" src='"+u+"'/></html>");
			}
		}).start();
	}
}
