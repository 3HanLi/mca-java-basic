package com.wy.mca.concurrent.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {

	private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String getFormatDate(Date date){
		return format.format(date);
	}
}
