package com.dd.supermarket.controller.weChat.accessToken;

import com.dd.supermarket.controller.weChat.util.AccessTokenUtil;
import com.dd.supermarket.fixed.WeChat;
import com.dd.supermarket.utils.ComputerInfo;

public class TokenThread implements Runnable{

		public static AccessToken access_token=null;
		
		@Override
		public void run() {
//			if(!this.thisIsService()){
//				try {
//					Thread.sleep(60*1000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				return;
//			}
			while(true){
				try {
					//调用工具类获取access_token(每日最多获取2000次，每次获取的有效期为7200秒)
//					access_token=AccessTokenUtil.getAccessToken(Const.WX_APPID, Const.WX_APPSECRET);	
					TokenThread.getAccessToken();
					if(null!=access_token){
//						System.out.println("accessToken获取成功："+access_token.getExpires_in());
						//7000秒之后重新进行获取
						Thread.sleep((access_token.getExpires_in()-200)*1000);
					}else{
						//60秒之后尝试重新获取
						Thread.sleep(60*1000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		public static void getAccessToken(){
			access_token=AccessTokenUtil.getAccessToken(WeChat.WX_APPID, WeChat.WX_APPSECRET);	
			if(null!=access_token){
				System.out.println("accessToken获取成功："+access_token.getExpires_in());
			}
		}
		//检查当前是不是服务器
//		private boolean thisIsService(){
//			String mac = ComputerInfo.getComputerName()+"&"+ComputerInfo.getMacAddress();
//			if(mac.equals(weChat.getweChat().getweChat().getThisMac()))return true;
//			return false;
//		}
	
}
