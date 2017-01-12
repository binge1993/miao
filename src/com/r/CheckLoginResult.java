package com.r;

public class CheckLoginResult {
	public static final int ERROR = -1;
	public static final int SUCCESS = 1;
	public static final int NEEDCODE = 2;//需要验证码
	public static final int CODEERROR = 3;//验证码错误
	public static final int ACCOUNTNOTEXIST = 4;//账号有误
	public static final int PWDERROR = 5;//密码错误
	private int flag;
	private String msg;
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
