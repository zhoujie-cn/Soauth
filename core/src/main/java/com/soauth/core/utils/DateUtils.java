package com.soauth.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils extends org.apache.commons.lang.time.DateUtils {
   private  static  final Logger log = LoggerFactory.getLogger(DateUtils.class);

	public static String getStringDate(Date currentTime ) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	public static Date date() {
		return new Date();
	}
	
	
}
