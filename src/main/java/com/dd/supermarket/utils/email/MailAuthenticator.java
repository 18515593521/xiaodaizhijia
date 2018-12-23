package com.dd.supermarket.utils.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年6月11日 上午10:07:24 <br/>
* 类说明：
*/
public class MailAuthenticator extends Authenticator{

    public static String USERNAME = "";
    public static String PASSWORD = "";

    public MailAuthenticator() {
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(USERNAME, PASSWORD);
    }

}