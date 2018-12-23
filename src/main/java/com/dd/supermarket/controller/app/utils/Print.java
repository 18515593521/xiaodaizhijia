package com.dd.supermarket.controller.app.utils;
/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年6月23日 下午2:47:27 <br/>
* 类说明：
*/
public class Print {
	
	public static void println(String str){
		System.out.println(str);
	}
	public static void println(String...str){
		for (String s : str) {
			System.out.println(s);
		}
	}
	public static void print(String str){
		System.out.print(str);
	}
	public static void print(String...str){
		for (String s : str) {
			System.out.print(s+"  \t  ");
		}
	}
	public static void main(String[] args) {
		Print.print("123","abc");
	}
}
