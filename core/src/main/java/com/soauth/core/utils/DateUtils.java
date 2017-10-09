package com.soauth.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils extends org.apache.commons.lang.time.DateUtils {
   private  static  final Logger log = LoggerFactory.getLogger(DateUtils.class);
	private static final String date_fomat= "HH:mm:ss";
	private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>();
	
	public static DateFormat getDateFormat()
	{
		DateFormat df=threadLocal.get();
		
		if(df==null){
			df = new SimpleDateFormat(date_fomat);
			threadLocal.set(df);
		}
		
		return df;
	}
	
	public static String formatDate(Date date ) throws ParseException 
	{
		 return getDateFormat().format(date);
	}

	public static Date date() {
		return new Date();
	}
	
	
}
