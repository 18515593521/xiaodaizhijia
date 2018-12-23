package com.dd.supermarket.controller.app.secretly;

import java.util.Optional;

import com.dd.supermarket.utils.secretly.DESUtil;

/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年6月22日 下午4:16:36 <br/>
* 类说明：
*/
public class SecretKey {
	private SecretKey(){}
	
	/**
	 * 接口所需真实密钥 
	 */
	private final String PARENT_SECRET_KEY = "DD+dq/2593707D08fhuQRidf+ETgq/KBzLpf9f1aMe5m38f7e2cyb=f1a0_lcjX9LcjfvUaT2A/DgwuNtfBFlcjTH755?So/LCJ7X+269138Eof764652200f7lIxVKB=zfSbc_Lyka57bff3b7da";
	
	/**
	 * 加密后密文拼接字符串
	 */
	private final String PREFIX = "+LCJ_yb_BC2q/K!B=My+lhh";
	
	/**
	 * 被加密的密钥
	 */
	private String ciphertext = "";
	
	private static SecretKey SK = null;
	
	
	public static SecretKey getSecretKeyClass(){
		if(null==SK)SK = new SecretKey();
		return SK;
	}
	
	/**
	 * 获取被加密后的密钥(密文)
	 * @return
	 */
	public String getCiphertext(){
		if(Optional.ofNullable(this.ciphertext).map(String::length).orElse(-1)>0)return this.ciphertext;
		this.ciphertext = encryptionKey();
        return this.ciphertext;
	}
	
	/**
	 * 获取真实密钥
	 * 为了保证其它接口内获得的密钥与后台获得的密钥统一性，故采用同样的方式获得密钥
	 * @return
	 */
	public String getSecret_key(){
		String ciphertext = this.getCiphertext(); //获得密钥加密密文
		try {
			ciphertext = DESUtil.decryption(ciphertext, DESUtil.SECRET_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ciphertext.replace(this.PREFIX, "");
	}
	
	/**
	 * 加密密钥
	 * @return
	 */
	private String encryptionKey(){
		String s = "";
		try {
			s = DESUtil.encryption(this.PREFIX+this.PARENT_SECRET_KEY, DESUtil.SECRET_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
	
	
	
    public static void main(String[] args) throws Exception {
    	SecretKey ped = SecretKey.getSecretKeyClass();
    	String secretKey = ped.getSecret_key();
    	System.out.println(secretKey);
    	
    	System.out.println(SecretKey.getSecretKeyClass().getSecret_key());
//    	String odata = "{\"uu_id\":\"5177977b841248fe9c7feeaf8dc209bf\",\"ld_id\":\"9732e2ede6574ed795ef7e5975a7605b\",\"bc_id\":\"c688028d0054462abc1b1a654b839a06\"}";
//    	odata=ped.encryption(odata, secretKey);
//    	System.out.println("加密后  "+ odata);
//    	odata = "D2uDLQmGqw0c+PUfyLHo1B9EE8dW2UIXbniyFFvNuD48/ZAvseUZvQbQDlriLK5Fnd9dNMMK8n1IO1KllDD5DZtDuFIjZqNpHCNvIwUR8xvJxgYFUvDaQrw8+wwHCJDtaaaAc650GM6yFaQ+5beLrl+IQCtzzv5Z86WlsOk0qa8=";
//    	odata = ped.decryption(odata, secretKey);
//    	System.out.println("解密后 "+ odata);
    	
    	
	}
	
	
}
