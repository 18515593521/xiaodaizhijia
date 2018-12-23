package com.dd.supermarket.fixed;



public class WeChat {
	private WeChat(){}
	private static WeChat weChat = null;
	public static WeChat getweChat(){
		if(null==weChat)weChat=new WeChat();
		return weChat; 
	}

	private String thisMac="iZ9pluh6kc50d6Z&00-16-3E-00-3D-87";//服务器mac地址
	public String getThisMac() {
		return thisMac;
	}
	
	public void setThisMac(String thisMac) {
		this.thisMac = thisMac;
	}
	public static final String WX_APPID = "wx8f40fcd4a4a89333";							
	public static final String WX_APPSECRET = "a21f9aa7f674fe8e7f4e01d8b6ae752f";		
}
