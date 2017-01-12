package com.frame;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONObject;
import com.gui.MyImgPanel;
import com.r.HttpResult;
import com.r.Prop;
import com.s.Static;
import com.url.GetAnswerUrl;
import com.url.InitDataUrl;
import com.url.RefreshUrl;
import com.url.SecKillBigBuyUrl;
import com.url.SubmitUrl;
import com.util.BrowerUtil;
import com.util.FileUtil;
import com.util.HttpUtil;
import com.util.MyUtil;

import javax.swing.JToggleButton;

public class Miao {
	private DefaultHttpClient httpClient;
	private HttpContext localContext;
	private JFrame frame;
	private JTextField txtUrl;
	private MyImgPanel myImg_photo;
	private JLabel lblNewLabel ;
	private JTextField txtCheckcode;
	private JLabel labelTime;
	private JTextArea txtShow;
	private AbstractButton btnEnter;
	private MyImgPanel myImg_show;
	private JRadioButton photoRadioBtn;
	private JPanel panel_porp;
	
	private String sign;
	private Double  pri = null;
	private String miaoUrl;
	private Long id = null;
	private JSONObject refreshBackObj;
	private int countQuantity = 0;//总数
	private int curQuantity = 0;//当前数量
	private JLabel label_sl;
	private String token;
	private Long uid;
	private Document itemDoc;//秒杀界面的dom对象
	
	private String current_price="1.00";
	private JToggleButton tbn_shouji;
	
	/**
	 * Create the application.
	 */
	public void show(){
		frame.setVisible(true);
	}
	public Miao(DefaultHttpClient httpclient,HttpContext localContext) {
		this.httpClient = httpclient;
		this.localContext = localContext;
		initialize();
	}
	
	private void initData(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				InitDataUrl myUrl = new InitDataUrl();
				HttpResult result = myUrl.doGet(httpClient);
				if(result.isSuccess()){
					JSONObject jsonObj = result.parseObject();
					JSONObject viewer = jsonObj.getJSONObject("viewer");
					token = viewer.getString("token");
					String tnik = viewer.getString("tnik");
					uid = viewer.getLong("id");
					log("uid:"+uid);
					frame.setTitle("秒杀界面-"+tnik);
				}else{
					log("initData失败");
				}
			}
		}).start();
		
	}
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("秒杀界面");
		frame.setBounds(400, 100, 672, 471);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            	tbtn_refresh.setSelected(false);
            	frame.setVisible(false);
            }
        });
		
		frame.getContentPane().setLayout(null);
		lblNewLabel = new JLabel("\u79D2\u6740\u5730\u5740:");
		lblNewLabel.setBounds(14, 22, 66, 21);
		frame.getContentPane().add(lblNewLabel);
		
		txtUrl = new JTextField();
		txtUrl.setBounds(72, 22, 387, 21);
		frame.getContentPane().add(txtUrl);
		txtUrl.setColumns(10);
		
		btnEnter = new JButton("enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doEnter();
			}
		});
		btnEnter.setBounds(518, 21, 66, 23);
		frame.getContentPane().add(btnEnter);
		
		myImg_photo = new MyImgPanel();
		myImg_photo.setBounds(24, 53, 125, 99);
		myImg_photo.setBackground(Color.GREEN);
		frame.getContentPane().add(myImg_photo);
		
		txtCheckcode = new JTextField();
		txtCheckcode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				submit();
			}
		});
		txtCheckcode.setBounds(518, 203, 118, 26);
		frame.getContentPane().add(txtCheckcode);
		txtCheckcode.setColumns(10);
		
		JLabel label = new JLabel("\u9A8C\u8BC1\u7801:");
		label.setBounds(462, 208, 54, 15);
		frame.getContentPane().add(label);
		
		txtShow = new JTextArea();
		txtShow.setLineWrap(true);
		JScrollPane jp = new JScrollPane(txtShow,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jp.setBounds(10, 263, frame.getWidth()-40, 130);
		frame.getContentPane().add(jp);
		
		labelTime = new JLabel("00:00:000");
		labelTime.setBounds(203, 53, 93, 15);
		frame.getContentPane().add(labelTime);
		
		JButton btnNewButton_1 = new JButton("\u2190");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = MyUtil.getSystemClipboard();
				txtUrl.setText(str);
				btnEnter.doClick();
			}
		});
		btnNewButton_1.setBounds(462, 21, 54, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		myImg_show = new MyImgPanel();
		myImg_show.setBackground(Color.ORANGE);
		myImg_show.setBounds(203, 78, 433, 119);
		frame.getContentPane().add(myImg_show);
		
		btnNewButton = new JButton("提交");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				submit();
			}
		});
		btnNewButton.setBounds(542, 230, 93, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton button_2 = new JButton("\u4FDD\u5B58\u65E5\u5FD7");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveLog();
			}
		});
		button_2.setBounds(14, 389, 93, 34);
		frame.getContentPane().add(button_2);
		
		photoRadioBtn = new JRadioButton("\u624B\u673A\u79D2\u6740");
		photoRadioBtn.setSelected(true);
		photoRadioBtn.setBounds(456, 234, 80, 23);
		frame.getContentPane().add(photoRadioBtn);
		
		ti = new JTextField();
		ti.setText("100");
		ti.setBounds(370, 53, 44, 21);
		frame.getContentPane().add(ti);
		ti.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("/ms");
		lblNewLabel_1.setBounds(420, 55, 30, 19);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("\u5237\u65B0\u95F4\u9694:");
		lblNewLabel_2.setBounds(317, 53, 66, 21);
		frame.getContentPane().add(lblNewLabel_2);
		
		panel_porp = new JPanel();
		panel_porp.setBackground(Color.GRAY);
	
		panel_porp.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		panel_porp.setBounds(317, 53, 66, 21);
		
		JScrollPane sp2 = new JScrollPane(panel_porp,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp2.setBounds(14, 162, 178, 91);
		frame.getContentPane().add(sp2);
		
		JLabel label_1 = new JLabel("\u6570\u91CF:");
		label_1.setBounds(203, 208, 30, 15);
		frame.getContentPane().add(label_1);
		
		label_sl = new JLabel("-/-");
		label_sl.setBounds(242, 208, 72, 15);
		frame.getContentPane().add(label_sl);
		
		JButton btnNewButton_2 = new JButton("\u2191");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BrowerUtil.openURL(txtUrl.getText());
			}
		});
		btnNewButton_2.setBounds(594, 21, 54, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		tbtn_refresh = new JToggleButton("\u5237\u65B0");
		tbtn_refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startRefresh();
			}
		});
		tbtn_refresh.setBounds(542, 52, 93, 23);
		frame.getContentPane().add(tbtn_refresh);
		
		label_2 = new JLabel("New label");
		label_2.setBounds(221, 99, 54, 15);
		frame.getContentPane().add(label_2);
		
		tbn_shouji = new JToggleButton("词库收集");
		tbn_shouji.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!tbn_shouji.isSelected()){
					return;
				}
				new Thread(new Runnable() {
					@Override
					public void run() {
						while(tbn_shouji.isSelected()){
							RefreshUrl ru = new RefreshUrl(id,uid,photoRadioBtn.isSelected());
							HttpResult result = ru.doGet(httpClient);
							JSONObject obj = result.parseObject();
							String tip = obj.getString("tip");
							String qst = obj.getString("qst");
							int len = obj.getInteger("len");
							String str = tip+"-"+len+"-"+qst;
							log(str);
							FileUtil.addStr(str+"\r\n", "C:/miao/photo.txt");
						}
					}
				}).start();
			}
		});
		tbn_shouji.setBounds(420, 400, 135, 23);
		frame.getContentPane().add(tbn_shouji);
		
		iniTime();
		initData();
	}
	
	private void submit() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String checkcode = txtCheckcode.getText().trim();
				if(checkcode.length()<1){
					return;
				}
				log("第一次提交答案:"+checkcode);
				
				SubmitUrl su = new SubmitUrl(id,uid, checkcode, photoRadioBtn.isSelected());
				
				HttpResult result = su.submit(httpClient);
				
				if(!result.isSuccess()){
					log("提交第一步错误,代码:"+result.getCode());
					return;
				}
				
				log("秒杀成功第一步:"+result.getCode()+","+result.getStr());
				JSONObject js = JSONObject.parseObject(result.getStr());
				String secKillEncryptStr = sign;
				String tb_token = token;
				String timkn = js.getString("timkn");
				String timk = js.getString("timk");
				int quantity = 1;
				SecKillBigBuyUrl sbbu = new SecKillBigBuyUrl(tb_token, miaoUrl, id, pri, timkn, timk, secKillEncryptStr, checkcode,current_price);
				log("提交订单:");
				log("postStr:"+sbbu.getPostStr());
				HttpResult re = sbbu.doPost(httpClient);
				if(!re.isSuccess()){
					log("提交订单bug");
				}
				log("错误信息:");
				try{
					Document doc = re.parseDoc();
					Element content = doc.getElementById("content");
					Elements es1 = content.getElementsByClass("section-content");
					String msg = es1.get(0).getElementsByTag("h2").html();
					log(msg);
				}catch(Exception e){
					log("buySuccess?");
					log(re.getStr());
				}
				
				FileUtil.writeFile(re.getStr());
			}
		}).start();
	}
	/*
	 * 初始化时钟,开启新线程,显示时间(本地时间+时差)
	 * 100ms
	 */
	private void iniTime(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					SimpleDateFormat sm = new SimpleDateFormat("mm:ss:S");
					long t = Static.currentTimeMillis();
					String timeStr = sm.format(t);
					labelTime.setText(timeStr);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	private JButton btnNewButton;
	private JTextField ti;
	private JToggleButton tbtn_refresh;
	private JLabel label_2;
	private void startRefresh(){
		if(!tbtn_refresh.isSelected()){
			return;
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(tbtn_refresh.isSelected()){
					RefreshUrl ru = new RefreshUrl(id,uid,photoRadioBtn.isSelected());
					HttpResult result = ru.doGet(httpClient);
					log("刷新结果--"+result.getStr());
					if(result.isSuccess()){
						JSONObject jsonObj = result.parseObject();
						refreshBackObj = jsonObj;
						String img = jsonObj.getString("qst");
						if(img!=null&&img.length()>0){
							//刷新到了
							tbtn_refresh.setSelected(false);
							myImg_show.drawImage(img);
							sign = jsonObj.getString("sign");
							if(refreshBackObj.getString("tip")!=null){
								//获取答案
								new Thread(new Runnable(){
									final String tip = refreshBackObj.getString("tip");
									final Integer len = refreshBackObj.getInteger("len");
									final String url = refreshBackObj.getString("qst");
									@Override
									public void run() {
										System.out.println("获取答案中...");
										GetAnswerUrl aau = new GetAnswerUrl(tip,len,url.substring(url.lastIndexOf("/")+1,url.length()));
										HttpResult ahr = aau.doGet(httpClient);
										if(ahr.isSuccess()){
											JSONObject jso = ahr.parseObject();
											if(jso.getBoolean("success")){
												txtCheckcode.setText(jso.getString("answer"));
												//submit();
											}else{
												log("获取答案失败");
											}
										}else{
											log("获取答案是服务器报错");
										}
									}
								}).start();
							}
							//终止刷新
							return;
						}
						
						//没有刷新到
						try{
							String tiStr = ti.getText();
							int ti = Integer.parseInt(tiStr);
							Thread.sleep(ti);
						}catch(Exception e){
							log("刷新时间有误");
						}
					}else{
						log("自动刷新失败,代码:"+result.getCode()+",地址:"+ru.getDefaultUrl()+",暂停1000ms");
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
	}
	private void log(String str){
		txtShow.append(str+"\n");
		txtShow.setCaretPosition(txtShow.getText().length());
	}
	
	private void saveLog(){
		
		String txt = txtShow.getText();
		boolean flag = FileUtil.addLogStr(txt, "C:/log.txt");
		if(flag){
			txtShow.setText("");
		}else{
			JOptionPane.showMessageDialog(null, "保存日志失败");
		}
		
	}
	
	private void resetMiao(){
		sign = null;
		pri = null;
		miaoUrl = null;
		id = null;
		refreshBackObj = null;//
		curQuantity = 0;
		countQuantity = 0;
		txtShow.setText("");
		txtCheckcode.setText("");
		myImg_photo.setText("");
		myImg_show.setText("");
		panel_porp.removeAll();
		int w =panel_porp.getWidth();
		int h = panel_porp.getHeight();
		panel_porp.setSize(0, 0);
		panel_porp.setSize(w, h);
	}
	private void doEnter(){
		resetMiao();
		new Thread(new Runnable() {
			@Override
			public void run() {
				String text = txtUrl.getText();
				try{
					String idStr =MyUtil.substring(text, "taobao.com/", ".htm");
					id = Long.parseLong(idStr);
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, "地址有误");
					return;
				}
				miaoUrl = text;
				HttpResult result = HttpUtil.httpGet(miaoUrl, httpClient,"gb2312");
				if(result.getCode()!=200){
					JOptionPane.showMessageDialog(null, "该链接已失效");
					return;
				}
				Document doc = result.parseDoc();
				itemDoc = doc;
				//获取商品当前价格:
				Element ec = doc.getElementById("J_FrmBid");
				current_price = ec.getElementsByAttributeValue("name", "current_price").get(0).attr("value");
				log("一口价:"+current_price);
				Element detailEle = doc.getElementById("detail");
				//搞到商品图片
				Element img = detailEle.getElementById("J_ImgBooth");
				String src = img.attr("data-src");
				myImg_photo.drawImage(src);
				//myImg_photo.setText("<html><img width="+myImg_photo.getWidth()+" height="+myImg_photo.getHeight()+" src='"+src+"'/></html>");
				
				
				//搞到商品描述
				Elements ds = detailEle.getElementsByClass("tb-detail-hd");
				Element detail = ds.get(0);
				String str =detail.getElementsByTag("h3").get(0).html();
				log(str);
				
				//商品秒杀价格
				Element priceEle = detailEle.getElementById("J_tkaPrice");
				String priceStr = priceEle.attr("data-val");
				log("秒杀价格:"+priceStr);
				pri = Double.parseDouble(priceStr);
				
				//数量
				Element countEle = detailEle.getElementById("J_SpanStock");
				String countStr = countEle.html();
				log("数量:"+countStr);
				curQuantity = countQuantity = Integer.parseInt(countStr);
				
				List<Prop> list = new ArrayList<Prop>();
				//参数
				Element iskuEle = detailEle.getElementById("J_isku");
				if(iskuEle!=null){
					Element skinEle = iskuEle.getElementsByClass("tb-skin").get(0);
					Elements props = skinEle.getElementsByClass("J_Prop");
					for(Element e:props){
						Element eSaleProp =  e.getElementsByClass("J_TSaleProp").get(0);
						String sname = eSaleProp.attr("data-property");
						panel_porp.add(new JLabel(sname+":"));
						String key = e.attr("class");
						List<String> value = new ArrayList<String>();
						ButtonGroup bg =new ButtonGroup();
						Elements lis = eSaleProp.getElementsByTag("li");
						for(int i=0;i<lis.size();i++){
							Element li = lis.get(i);
							value.add(li.attr("data-value"));
							JRadioButton rb =new JRadioButton(li.val());
							rb.setName(key);
							rb.setSelected(i==0?true:false);
							panel_porp.add(rb);
							bg.add(rb);
						}
						panel_porp.add(new JLabel("<html>------------------------------------</html>"));
					}
				}					
				label_sl.setText(curQuantity+"/"+countQuantity);
			/*	//秒杀时间
				Element timeEle = detailEle.getElementById("J_SecKill");
				String date = timeEle.getElementsByClass("date").get(0).html();
				String time = timeEle.getElementsByClass("time").get(0).html();
				log("秒杀时间:"+date+"-"+time);
				*/
			}
		}).start();
	}
}
