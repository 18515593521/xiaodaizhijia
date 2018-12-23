package com.dd.supermarket.push;

import com.dd.supermarket.controller.app.model.Push;
import com.dd.supermarket.push.android.AndroidBroadcast;
import com.dd.supermarket.push.android.AndroidUnicast;
import com.dd.supermarket.push.ios.IOSBroadcast;
import com.dd.supermarket.push.ios.IOSUnicast;

public class PushStart {
	
	private PushClient client = new PushClient();
	/**
	 * 推送给所有用户
	 * @param u
	 * @throws Exception
	 */
	public void push_AllUser(Push u) throws Exception{
		this.sendIOSBroadcast(u);
		this.sendAndroidBroadcast(u);
	}
	
	/**
	 * 消息推送给个人
	 * @param u
	 * @throws Exception 
	 */
	public void push_User(Push up) throws Exception{
		this.sendAndroidUnicast(up);
		this.sendIOSUnicast(up);
	}
	
	/**
	 * 只推送给所有IOS(苹果)用户
	 * @param u
	 * @throws Exception
	 */
	public void push_IOSUser(Push u) throws Exception{
		this.sendIOSBroadcast(u);
	}
	/**
	 * 只推送给所有Android(安卓)用户
	 * @param u
	 * @throws Exception
	 */
	public void push_AndroidUser(Push u) throws Exception{
		this.sendAndroidBroadcast(u);
	}	
	
	/**
	 * 安卓个推
	 * @param u
	 * @throws Exception
	 */
	private void sendAndroidUnicast(Push u) throws Exception {
		String DeviceToken = "Aks5AmLVwKLww7IT7rone4ogUfSQpadTzK24NmSIJ1Kz";
		if(null==DeviceToken)throw new Exception("deviceToken is null");
		AndroidUnicast unicast = new AndroidUnicast("5b91000df43e483f8a0001e4","i0wj6tts4sayr43es1qev9m68jzf7jiu");
		String ticker = u.getTicker();
		unicast.setDeviceToken(DeviceToken);
		unicast.setTitle(  u.getTitle());
		unicast.setText(   u.getContext());
		if(null==ticker||"".equals(ticker))ticker = "Android unicast ticker";
		unicast.setTicker( ticker);
		unicast.goAppAfterOpen();
		
		unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		client.send(unicast);
	}
	
	/**
	 * 苹果个推
	 * @param u
	 * @throws Exception
	 */
	private void sendIOSUnicast(Push u) throws Exception {
		String DeviceToken = "fa286b3ba42add557265fb0698f2966ebad33f929740794ae133f8b42f4c327e";
		IOSUnicast unicast = new IOSUnicast("5b90bdb7b27b0a3b210000b1","0wbljkqaj5obtzycqwdfy7aqztccfpkf");
		unicast.setDeviceToken( DeviceToken);
		System.out.println(u.getTitle());
		unicast.setAlert(u.getTitle()+"\n"+u.getContext());
		unicast.setBadge(0);
		unicast.setSound("default");
		unicast.setTestMode();
		unicast.setCustomizedField("test", "helloworld");
		client.send(unicast);
	}
	
	
	/**
	 * 安卓群推
	 * @param u
	 * @throws Exception
	 */
	private void sendAndroidBroadcast(Push u) throws Exception {	
		AndroidBroadcast broadcast = new AndroidBroadcast("5b91000df43e483f8a0001e4","i0wj6tts4sayr43es1qev9m68jzf7jiu");
		broadcast.setTicker( "ticker");
		broadcast.setTitle(u.getTitle());
		broadcast.setText(u.getContext());
		broadcast.goAppAfterOpen();
		broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		client.send(broadcast);
	}
	
	/**
	 * IOS群推
	 * @throws Exception
	 */
	private void sendIOSBroadcast(Push u) throws Exception {		
		IOSBroadcast broadcast = new IOSBroadcast("5b90bdb7b27b0a3b210000b1","0wbljkqaj5obtzycqwdfy7aqztccfpkf");
		broadcast.setAlert(u.getTitle()+"\n"+u.getContext());
		broadcast.setBadge( 0);
		broadcast.setSound( "default");
//		broadcast.setProductionMode(false);	//默认为生产环境
		broadcast.setTestMode();
		// Set customized fields
		broadcast.setCustomizedField("test", "helloworld");
		client.send(broadcast);
	}

	
	
	
	
	
	
	public static void main(String[] args) {
		PushStart demo = new PushStart();
		PushStart IOS = new PushStart();
		try {
			Push push = new Push();
			push.setTitle("信用账单");
			push.setContext("提前祝中秋快乐，阖家团圆，健康平安");
			demo.sendAndroidUnicast(push);
//			IOS.sendIOSBroadcast();
//			demo.sendAndroidBroadcast();			
//			IOS.sendIOSUnicast();
//			String iosToken = "d5bb82bb14cfc96c721b6814b08890bc8f10ce1bd47abccdf3a3326c9694dda3";
//			String title = "title";
//			String context = "context";
//			IOS.sendIOSUnicast(iosToken,title,context);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	

}
