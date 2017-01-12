package com.gui;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

public class MyCanvas extends Canvas{

	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}
	
	
	public void drawImage(Image img){
		Graphics g = this.getGraphics();
		g.drawImage(img,1,1,this.getWidth(),this.getHeight(),null);
	}
	
	public void d(){
		Graphics g = this.getGraphics();
		g.fillRect(0, 0, 20, 20);
	}
	
	
}
