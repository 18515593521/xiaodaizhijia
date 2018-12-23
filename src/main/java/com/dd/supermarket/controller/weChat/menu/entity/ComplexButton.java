package com.dd.supermarket.controller.weChat.menu.entity;

/**
 * 	一級菜單
 * */
public class ComplexButton extends Button{
	private String name;			//菜单名称
	private String type;		//菜单类型
	private String url;	
	private Button[] sub_button;	//子级菜单
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Button[] getSub_button() {
		return sub_button;
	}
	public void setSub_button(Button[] sub_button) {
		this.sub_button = sub_button;
	}
}
