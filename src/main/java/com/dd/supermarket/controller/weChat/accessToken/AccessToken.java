package com.dd.supermarket.controller.weChat.accessToken;


public class AccessToken {
	private String access_token;	//access_token
	private int	expires_in;			//有效时间
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public int getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
}
