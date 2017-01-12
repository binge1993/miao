package com.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.d.AccountData;
import com.s.Data;

public class AccountUtil {
	/*public static boolean addAccountData(AccountData ad) throws Exception{
	        try {
	        	DocumentBuilder db=DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document doc=db.parse(new File(Data.USERFILE));
				Element user = doc.createElement("user");
				
				Element account = doc.createElement("account");
				account.setTextContent(ad.getAccount());
				user.appendChild(account);
				
				Element pwd = doc.createElement("pwd");
				pwd.setTextContent(ad.getPwd());
				user.appendChild(pwd);
				
				doc.getElementsByTagName("users").item(0).appendChild(user);
				
				 TransformerFactory tFactory = TransformerFactory.newInstance();
				   Transformer transformer = tFactory.newTransformer();
				   DOMSource source = new DOMSource(doc);
				   StreamResult result = new StreamResult(
				     new java.io.File(Data.USERFILE));
				   transformer.transform(source, result);
				
	        } catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//���ļ�������DOCUMENT����
 catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		return false;
	}*/
	
	/*public static List<AccountData> readAccountData(){
		List<AccountData> list = new ArrayList<AccountData>();
		try{
			
			  DocumentBuilder db=DocumentBuilderFactory.newInstance().newDocumentBuilder();
		        Document doc=db.parse(new File(Data.USERFILE));//���ļ�������DOCUMENT����
		        Element users = doc.getDocumentElement();
		        NodeList childs = users.getElementsByTagName("user");
		        for(int i=0;i<childs.getLength();i++){
		        	String account = doc.getElementsByTagName("account").item(i).getTextContent();
		        	String pwd = doc.getElementsByTagName("pwd").item(i).getTextContent();
		        	AccountData ad = new AccountData();
		        	ad.setAccount(account);
		        	ad.setPwd(pwd);
		        	list.add(ad);
		       }
		        
			}catch(Exception e){
				e.printStackTrace();
			}
		
		return list;
	}*/
	
	public static boolean addAccountData(AccountData ad){
		List<AccountData> list = readAccountData();
		list.add(ad);
		StringBuffer buff = new StringBuffer();
		for(AccountData d:list){
			buff.append(d.getAccount()+"-"+d.getPwd()+"\r\n");
		}
		FileUtil.writeFile(buff.toString(), Data.USERFILE);
		return true;
	}
	public static List<AccountData> readAccountData(){
		List<AccountData> list = new ArrayList<AccountData>();
		File file = new File(Data.USERFILE);
			try {
				if(!file.exists()){
					file.createNewFile();
				}
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				String line = "";
				String str = "";
				while(line!=null){
					line = line.trim();
					if(line.matches(".{1,32}-.{1,20}")){
						String[] sps = line.split("-");
						AccountData ad = new AccountData();
						ad.setAccount(sps[0]);
						ad.setPwd(sps[1]);
						list.add(ad);
					}
					line = br.readLine();
				}
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return list;
		
	}
}
