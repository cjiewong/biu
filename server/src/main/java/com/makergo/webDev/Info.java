package com.makergo.webDev;
import com.makergo.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 报告出错的类
 * @author Administrator
 *
 */
@Slf4j
public class Info {
	public static void print(String s){
		//System.out.println("\n###### 信息 "+DateTime.getDateTimeNow()+" ###### \n"+s+"\n");
		if(log.isInfoEnabled()) {
			log.info(DateTimeUtil.getDateTimeNow() + "    " + s);
		}
	}

}
