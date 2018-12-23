/**
 * 
 */
package com.dd.supermarket.controller.weChat.util;

/**
 * @author 2593707755  
 *
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
}
