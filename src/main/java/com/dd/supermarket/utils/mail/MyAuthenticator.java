package com.dd.supermarket.utils.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年6月9日 下午11:57:52 <br/>
* 类说明：发送邮件需要使用的基本信息 
*/
public class MyAuthenticator extends Authenticator {
	String userName=null;   
    String password=null;   
        
    public MyAuthenticator(){   
    }   
    public MyAuthenticator(String username, String password) {    
        this.userName = username;    
        this.password = password;    
    }    
    protected PasswordAuthentication getPasswordAuthentication(){   
        return new PasswordAuthentication(userName, password);   
    }   
}
