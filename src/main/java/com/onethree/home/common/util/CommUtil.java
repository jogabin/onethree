package com.onethree.home.common.util;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class CommUtil {
	
	//오늘 날짜 가져오기 format형식으로 형식 지정
	public static String getCurrentDate(String format){
		long todayTime;
		String today;
		SimpleDateFormat simpleDate;
		
		todayTime = System.currentTimeMillis(); 
		simpleDate = new SimpleDateFormat(format);
		
		today =  simpleDate.format(new Date(todayTime));
		
		return today;
	}
	
	//String 받아서 원하는 문자열로 변환 format형식으로 형식 지정
	public static String getStrFormat(String strDate,String strFormat,String format){
		String formatDate = "";
		SimpleDateFormat simp = new SimpleDateFormat(strFormat,Locale.ENGLISH);
		Date date = null;
		if(strDate != null && !strDate.equals("")){
			try {
				date = simp.parse(strDate);
			} catch (ParseException e) {			
				e.printStackTrace();
			}
			SimpleDateFormat simp2 = new SimpleDateFormat(format);
			formatDate = simp2.format(date);	
		}
		
		return formatDate;
	}
	
	//Date 받아서 원하는 문자열로 변환 format형식으로 형식 지정
	public static String getDateDtFormat(Date date,String format){
		String formatDate;
		SimpleDateFormat simpleDate;
		 
		simpleDate = new SimpleDateFormat(format);
		
		formatDate =  simpleDate.format(date);
		
		return formatDate;
	}
	
	//null인값을 공백으로 변환후 반환
	public static String getConvertNull(String nullStr){
		String str = nullStr;
		if(str == null || str.equals(null) || str.equals("null"))
			str = "";
		return str;
	}
	
	//원단위로 콤마찍기
	public static String getMoneyComma(int money){
	   String fmMoney = "";	   
	   NumberFormat nf = NumberFormat.getInstance();
	   nf.setMaximumIntegerDigits(8); //최대수 지정
	   fmMoney = nf.format(money);	
	   return fmMoney;
	}
	
	//원단위로 콤마찍기
	public static String getMoneyComma(long money){
	   String fmMoney = "";	   
	   NumberFormat nf = NumberFormat.getInstance();
	   nf.setMaximumIntegerDigits(8); //최대수 지정
	   fmMoney = nf.format(money);	
	   return fmMoney;
	}
	  		
	/**
	 * 문자열을 SHA-256 방식으로 암호화
	 * @param txt 암호화 하려하는 문자열
	 * @return String
	 * @throws Exception
	 */
	public static String getEncSHA256(String txt) throws Exception{
	    StringBuffer sbuf = new StringBuffer();
	     
	    MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
	    mDigest.update(txt.getBytes());
	     
	    byte[] msgStr = mDigest.digest() ;
	     
	    for(int i=0; i < msgStr.length; i++){
	        byte tmpStrByte = msgStr[i];
	        String tmpEncTxt = Integer.toString((tmpStrByte & 0xff) + 0x100, 16).substring(1);
	         
	        sbuf.append(tmpEncTxt) ;
	    }
	     
	    return sbuf.toString();
	}
}
