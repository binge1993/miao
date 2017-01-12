package com.frame;

import static com.r.CheckLoginResult.NEEDCODE;
import static com.r.CheckLoginResult.SUCCESS;

import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.alibaba.fastjson.JSONObject;
import com.d.AccountData;
import com.gui.MyImgPanel;
import com.r.CheckLoginResult;
import com.r.HttpResult;
import com.s.Data;
import com.s.Static;
import com.t.TimeThread;
import com.url.CheckAccountUrl;
import com.url.LoginUrl;
import com.util.AccountUtil;
import com.util.LoginUtil;
import com.util.MiaoFactory;
import com.util.FileUtil;
import com.util.Msg;

import java.awt.List;

public class Login {
	
	private JFrame frame;
	private JTextField txtAccount;
	private JPasswordField txtPwd;
	private JLabel label_msg;
	private JLabel label_checkCode;
	private JTextField txtCheckCode;
	private JPanel checkCodePanel;
	private DefaultHttpClient httpclient = new DefaultHttpClient();
	private HttpContext localContext = new BasicHttpContext();
	private String yzmSrc = "";
	private MyImgPanel myImg_checkCode;
	private JButton btn_login;
	private List userList;
	private java.util.List<AccountData> accList;
	private JButton btn_add;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
		//自动校正时间
		TimeThread tt = new TimeThread();
		tt.start();
	}
	private void initUserList(){
		userList.clear();
		accList = AccountUtil.readAccountData();
		for(int i=0;i<accList.size();i++){
			AccountData ad = accList.get(i);
			userList.add(ad.getAccount(), i);
		}
		
		userList.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				int index = userList.getSelectedIndex();
				AccountData ad = accList.get(index);
				txtAccount.setText(ad.getAccount());
				txtPwd.setText(ad.getPwd());
			}
		});
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//loginUrl = "http://localhost:8080";
		frame = new JFrame("登录");
		frame.setBounds(100, 100, 579, 453);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("账号:");
		label.setBounds(74, 71, 54, 15);
		frame.getContentPane().add(label);
		
		txtAccount = new JTextField();
		txtAccount.setText("");
		txtAccount.setBounds(119, 68, 139, 21);
		frame.getContentPane().add(txtAccount);
		txtAccount.setColumns(10);
		
		JLabel label_1 = new JLabel("密码:");
		label_1.setBounds(74, 117, 54, 15);
		frame.getContentPane().add(label_1);
		
		btn_login = new JButton("登录");
		btn_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		btn_login.setBounds(54, 208, 93, 23);
		frame.getContentPane().add(btn_login);
		
		ActionListener loginAction = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				btn_login.doClick();
			}
		};
		
		txtPwd = new JPasswordField();
		txtPwd.addActionListener(loginAction);
		txtPwd.setBounds(119, 114, 139, 21);
		txtPwd.setText("");
		frame.getContentPane().add(txtPwd);
		
		label_msg = new JLabel("\u632F\u6587\u4E66\u9662");
		label_msg.setVerticalAlignment(SwingConstants.TOP);
		label_msg.setForeground(Color.RED);
		label_msg.setBounds(10, 237, 376, 75);
		label_msg.setVisible(false);
		frame.getContentPane().add(label_msg);
		
		JButton btnJc = new JButton("检测");
		btnJc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkcode();
			}
		});
		btnJc.setBounds(268, 67, 93, 23);
		frame.getContentPane().add(btnJc);
		
		checkCodePanel = new JPanel();
		checkCodePanel.setVisible(false);
		checkCodePanel.setBounds(10, 142, 376, 56);
		frame.getContentPane().add(checkCodePanel);
		checkCodePanel.setLayout(null);
		
		Button button_1 = new Button("\u5207\u6362");
		button_1.setBounds(322, 33, 54, 23);
		checkCodePanel.add(button_1);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeYzm();
			}
		});
		
		label_checkCode = new JLabel("\u9A8C\u8BC1\u7801:");
		label_checkCode.setBounds(11, 21, 42, 15);
		checkCodePanel.add(label_checkCode);
		
		txtCheckCode = new JTextField();
		txtCheckCode.addActionListener(loginAction);
		txtCheckCode.setBounds(63, 18, 76, 21);
		checkCodePanel.add(txtCheckCode);
		txtCheckCode.setColumns(10);
		
		myImg_checkCode = new MyImgPanel();
		myImg_checkCode.setBounds(144, 14, 172, 29);
		checkCodePanel.add(myImg_checkCode);
		myImg_checkCode.setBackground(Color.GREEN);
		
		
		JButton btn_123 = new JButton("游客进入");
		btn_123.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MiaoFactory.createMiao(httpclient, localContext);
			}
		});
		btn_123.setBounds(308, 278, 93, 23);
		frame.getContentPane().add(btn_123);
		
		userList = new List();
		userList.setBounds(399, 10, 154, 275);
		frame.getContentPane().add(userList);
		
		List list_1 = new List();
		list_1.setBounds(10, 306, 538, 99);
		frame.getContentPane().add(list_1);
		
		btn_add = new JButton("添加");
		btn_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String account = txtAccount.getText();
				String pwd = txtPwd.getText();
				AccountData ad = new AccountData();
				ad.setAccount(account);
				ad.setPwd(pwd);
				try {
					AccountUtil.addAccountData(ad);
					txtAccount.setText("");
					txtPwd.setText("");
					initUserList();
				} catch (Exception e1) {
					Msg.alert("添加失败"+e1.getMessage());
				}
			}
		});
		btn_add.setBounds(187, 208, 93, 23);
		frame.getContentPane().add(btn_add);
		initUserList();
	}
	
	private void changeYzm(){
		txtCheckCode.setText("");
		myImg_checkCode.drawImage(yzmSrc+"&_r_="+Static.currentTimeMillis());
	}
	private void checkcode(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				String account = txtAccount.getText();
				CheckAccountUrl url =  new CheckAccountUrl(account);
				HttpResult result = url.postStr(httpclient);
				if(result.isSuccess()){
					JSONObject jsonObj = result.parseObject();
					boolean needcode = jsonObj.getBoolean("needcode");
					if(needcode){
						checkCodePanel.setVisible(true);
						yzmSrc = jsonObj.getString("url");
						changeYzm();
					}
					else{
						checkCodePanel.setVisible(false);
					}
				}else{
					Msg.alert("获取验证码错误,代码:"+result.getCode());
				}
			}
		}).start();
	
	}
	private void loginSuccess(){
		MiaoFactory.createMiao(httpclient, localContext);
		txtAccount.setText("");
		txtPwd.setText("");
		txtCheckCode.setText("");
		httpclient = new DefaultHttpClient();
		localContext = new BasicHttpContext();
		hideCheckCodePanel();
	}
	
	private void showCheckCodePanel(){
		checkCodePanel.setVisible(true);
	}
	private void hideCheckCodePanel(){
		checkCodePanel.setVisible(false);
	}
	private void login(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				String account = txtAccount.getText();
				String pwd = new String(txtPwd.getPassword());
				String checkcode = txtCheckCode.getText();
				LoginUrl loginUrl =new LoginUrl(account,pwd,checkcode,false);
				HttpResult result = loginUrl.post(httpclient);
				if(result.getCode()!=200){
					return;
				}
				String str = result.getStr();
				FileUtil.writeFile(str);
					CheckLoginResult clresult = LoginUtil.check(str);
					switch(clresult.getFlag()){
						case SUCCESS:
							loginSuccess();
						break;
						case NEEDCODE:
							label_msg.setText("请输入验证码");
							showCheckCodePanel();
							checkcode();
						break;
						default:
							label_msg.setText("<html>"+clresult.getMsg()+"</html>");
							label_msg.setVisible(true);
							break;
					}
			}
		}).start();
	}
	private boolean loginSuccess(HttpResponse response){
		Header[] headers = response.getAllHeaders();
		int SetCookieCount = 0;
		for(Header h:headers){
			if(h.getName().equals("Set-Cookie")){
				SetCookieCount++;
			}
		}
		if(SetCookieCount>4){
			return true;
		}
		return false;
	}
}
