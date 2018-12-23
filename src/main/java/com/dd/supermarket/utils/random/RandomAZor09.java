package com.dd.supermarket.utils.random;

import java.util.Random;

/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年5月29日 上午1:05:49 <br/>
* 类说明：
*/
public class RandomAZor09 {
	/**
	 * 产生一个随机数
	 * @param Max 最大数
	 * @param Min 最小数
	 * @return
	 */
	public static int getNum(int Min,int Max){
		if(Min>Max){
			int i = Max;
			Max = Min;
			Min = i;
		}
		return new Random().nextInt(Max-Min+1)+Min;
	}
	
	/**
	 * 产生一个随机小写字母
	 * @return
	 */
	public static String getaz(){
		return String.valueOf((char) (new Random().nextInt(122-97+1)+97));
	}
	
	/**
	 * 产生一个随机大写写字母
	 * @return
	 */
	public static String getAZ(){
		return String.valueOf((char) (new Random().nextInt(90-65+1)+65));
	}
	
	/**
	 * 返回一个指定长度的数字大写字母混合的字符串
	 * @param length 指定的长度
	 * @return
	 */
	public static String getBlend0_9AndA_Z(int length){
		String s = "";
		for(int i=0;i<length;i++){
			if(0==getNum(1, 0)){ 
				s+=getNum(9, 0);
			}else{
				s+=getAZ();
			}
		}
		return s;
	}
	
	/**
	 * 返回一个指定长度的数字小写字母混合的字符串
	 * @param length 指定的长度
	 * @return
	 */
	public static String getBlend0_9Anda_z(int length){
		String s = "";
		for(int i=0;i<length;i++){
			if(0==getNum(1, 0)){ 
				s+=getNum(9, 0);
			}else{
				s+=getaz();
			}
		}
		return s;
	}
	
	/**
	 * 返回一个指定长度的大小写字母混合的字符串
	 * @param length 指定的长度
	 * @return
	 */
	public static String getBlendA_ZAnda_z(int length){
		String s = "";
		for(int i=0;i<length;i++){
			if(0==getNum(1, 0)){ 
				s+=getAZ();
			}else{
				s+=getaz();
			}
		}
		return s;
	}
	
	
	
	public static void main(String[] args) {
		System.out.println(RandomAZor09.getNum(10000, 5500));
	}
	
}
