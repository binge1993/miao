package com.util;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Date;

public class MyUtil {
	public static String getSystemClipboard(){//��ȡϵͳ���а���ı�����[���ϵͳ���а帴�Ƶ��������ı�]
		Clipboard sysClb=null;
		sysClb = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable t = sysClb.getContents(null);
		//Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);  //���������д���һ��
		try { 
			if (null != t && t.isDataFlavorSupported(DataFlavor.stringFlavor)) { 
			String text = (String)t.getTransferData(DataFlavor.stringFlavor); 
			return text; 
			} 
		} catch (UnsupportedFlavorException e) {
			//System.out.println("Error tip: "+e.getMessage());
		} catch (IOException e) { 
		} 	//System.out.println("Error tip: "+e.getMessage());
		return null; 
	}
	
	public static String substring(String str,String startStr,String endStr){
		System.out.println(str.indexOf(startStr));
		System.out.println(str.indexOf(endStr));
		return str.substring(str.indexOf(startStr)+startStr.length(), str.indexOf(endStr));
	}
	
	public static Date getServiceDate(){
		return null;
	}
	
}
