package com.dd.supermarket.utils;
/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年5月29日 上午12:56:26 <br/>
* 类说明：
*/
public class IsEmpty {
	
	/**
	 * 判断一个对象是否为空<br/>
	 * 如果为空，当遇到null或""时，返回true 否则返回false
	 * @param value
	 * @return boolean.If the parameter is NULL or Empty, return true 
	 * 			
	 */
	public static boolean isEmpty(Object value){
		return (null==value||"".equals(value));
	}
	
	
	/**
	 * 判断一组对象是否为空<br/>
	 * 如果为空，当遇到null或""时，返回true 否则返回false
	 * @param value
	 * @return boolean.If the parameter is NULL or Empty, return true 
	 * 			
	 */
	public static boolean isEmpty(Object...value){
		boolean b = false;
		for (Object v : value) {
			if(null==v||"".equals(v)){
				b = true;
				break;
			}
		}
		return b;
	}
}
