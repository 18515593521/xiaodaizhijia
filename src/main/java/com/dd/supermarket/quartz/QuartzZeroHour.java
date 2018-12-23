package com.dd.supermarket.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年5月29日 下午11:12:38 <br/>
* 类说明：
*/
public class QuartzZeroHour {
	
	public void execute(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("第一个Quartz的任务调度！！！"+format.format(new Date()));
    }
}
