package com.mits.filenet.ce.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class DateUTC {
	public static Date convertToDate(String strdate){
		System.out.println("strdate*******************"+strdate);
		Date date=null;
		try {
		date= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(strdate);
		System.out.println("date*******************"+date);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return date;
	}
public static String dateToUTC(Date date){
	  SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
	   sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
	  
	   System.out.println("sdf*******************"+sdf);
		String UTCformat=sdf.format(date);
		System.out.println("sdf***********"+UTCformat);
	  return UTCformat;
	}
	
}
