package com.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.r.CheckLoginResult;
import static com.r.CheckLoginResult.*;

public class LoginUtil {
	public static CheckLoginResult check(String str){
		CheckLoginResult clr = new CheckLoginResult();
		Document doc = Jsoup.parse(str);
		Element jMessage = doc.getElementById("J_Message");
		if(jMessage==null){
			clr.setFlag(SUCCESS);
			return clr;
		}
		Elements errors = jMessage.getElementsByClass("error");
		Element error = errors.get(0);
		String msg = error.html();
		clr.setFlag(ERROR);
		clr.setMsg(msg);
		if(msg.indexOf("��������֤��")!=-1){
			clr.setFlag(NEEDCODE);
		}
		else if(msg.indexOf("��֤�����")!=-1){
			clr.setFlag(CODEERROR);
		}
		else if(msg.indexOf("���˺Ų�����")!=-1){
			clr.setFlag(ACCOUNTNOTEXIST);
		}
		else if(msg.indexOf("�������������˻�����ƥ��")!=-1){
			clr.setFlag(PWDERROR);
		}
		return clr;
	}
}
