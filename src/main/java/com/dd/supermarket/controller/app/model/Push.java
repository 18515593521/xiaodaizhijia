/**
 * 
 */
package com.dd.supermarket.controller.app.model;

/**
 * 推送所需参数
 *
 */
public class Push {
	
	private String appkey;
	private String master_secret;
	
	
	private String deviceToken;
	private String ticker;
	private String title;
	private String context;
	
	public String getAppkey() {
		return appkey;
	}
	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}
	public String getMaster_secret() {
		return master_secret;
	}
	public void setMaster_secret(String master_Secret) {
		this.master_secret = master_Secret;
	}
	public String getDeviceToken() {
		return deviceToken;
	}
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	
	
}
