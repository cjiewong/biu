package com.abc.webDev;

import com.abc.util.DateTimeUtil;
import com.abc.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;


/**
 * 报告出错的类
 * @author Administrator
 *
 */
@Slf4j
public class Error {
	public static void print(Exception e){
		if (null == e) {
			return;
		}
		System.out.println("\n\n ###### 出错"+ DateTimeUtil.getDateTimeNow()+" ###### \n");
		e.printStackTrace();
		System.out.println("\n\n");
		LogUtil.fatalStackTrace(log, e);
	}
	public static void print(String s){
		if (StringUtils.isBlank(s)) {
			return;
		}
		System.out.println("\n\n ###### 出错"+DateTimeUtil.getDateTimeNow()+" ###### \n");
		System.out.println(s);
		System.out.println("\n\n");
		LogUtil.fatalStackTrace(log, new Exception(s));
	}
	public static void print(String s, Exception e){
		if (StringUtils.isNotBlank(s)) {
			print(s);
		}
		if (null != e) {
			print(e);
		}
	}

}
