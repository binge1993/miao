package com.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FileUtil {
	public static void writeFile(String text){
		writeFile(text,"C:/a.html");
	}
	public static void writeFile(String text,String fileName){
		try{
			File file = new File(fileName);
			FileWriter fw = new FileWriter(file);
			fw.write(text);
			fw.flush();
			fw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static boolean addStr(String str,String fileName){
		File file = new File(fileName);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			try {
				FileWriter fw = new FileWriter(file,true);
				fw.write(str);
				fw.flush();
				fw.close();
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return false;
	}
/*	public static String readFile(String fileName){
		File file =new File(fileName);
		try {
			if(!file.exists()){
				file.createNewFile();
			}
			BufferedReader br = new BufferedReader(new FileReader(file));
			br.readLine();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	*/
/*	public static String readFile(String str){
		try{
			
		  DocumentBuilder db=DocumentBuilderFactory.newInstance().newDocumentBuilder();
	        Document doc=db.parse(new File(str));//把文件解析成DOCUMENT类型
	        Element users = doc.getDocumentElement();
	        NodeList childs = users.getElementsByTagName("user");
	        
	      for(int i=0;i<childs.getLength();i++){
	        	Node node = childs.item(i);
	        }
	        
		}catch(Exception e){
			e.printStackTrace();
		}
	        return null;
		
	}*/
	public static void main(String[] args) {
		//readFile("C:/miao/users.xml");
	}
	
	public static boolean addLogStr(String str,String fileName){
		File file = new File(fileName);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			try {
				FileWriter fw = new FileWriter(file,true);
				SimpleDateFormat sm = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss:S");
				long localTime = System.currentTimeMillis();
				String timeStr = sm.format(localTime);
				fw.write(timeStr+"\r\n-----------------\r\n");
				str = str.replace("\n", "\r\n");
				fw.write(str);
				fw.write("\r\n----------------------------------------------------------------------------\r\n");
				fw.flush();
				fw.close();
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return false;
	}

}
