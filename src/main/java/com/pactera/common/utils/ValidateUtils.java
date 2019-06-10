/**
 * 文件名：ValidateUtil.java
 * 版本信息：1.0.0
 * 日期：2016年11月21日-下午12:31:19
 * Copyright (c) 2016Pactera-版权所有
 */
 
package com.pactera.common.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 类名称：ValidateUtil
 * 类描述：正则校验工具类
 * 创建人：lee
 * 创建时间：2016年11月21日 下午12:31:19
 * @version 1.0.0
 */
public class ValidateUtils {

	/**
	 * 判断一个字符串是否为数字
	 */
	private static final Pattern NUM_PATTERN = Pattern.compile("[0-9]*");
	public static boolean isNumeric(String str) {
		if(str==null || str.trim().length() <= 0){
			return false;
		}
		
		Matcher isNum = NUM_PATTERN.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	
	/**
	  * 判断是否为合法URL
	  * @param url 请求地址字符串
	  * @return 是否合法
	 */
	private static final Pattern URL_PATTERN = Pattern.compile("^(http|www|ftp|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$",Pattern.CASE_INSENSITIVE );
	public static boolean isValidUrl(String url){
		if(url==null || url.trim().length() <= 0){
			 return false;
		 }
		 Matcher m = URL_PATTERN.matcher(url);    
		 return m.find();
	}
	
	
    /**
      * 检查 html中img src
      * @param html html字符串
      * @return img src list
      * @author  lijing　2015年5月18日
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static final Pattern IMG_PATTERN   =   Pattern.compile("<img\\s+(?:[^>]*)src\\s*=\\s*([^>]+)",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    public static List checkHtmlImg(String html){   
    	  
        Matcher   matcher   =   IMG_PATTERN.matcher(html);   
        List list = new ArrayList();   
        while(matcher.find()){   
            String group = matcher.group(1);   
            if(group == null){   
                continue;   
            }   
            // 这里可能还需要更复杂的判断,用以处理src="...."内的一些转义符   
            if(group.startsWith("'"))   {   
                list.add(group.substring(1,group.indexOf("'",1)));   
            }else if(group.startsWith("\""))   {   
                list.add(group.substring(1,group.indexOf("\"",1)));   
            }else{   
                list.add(group.split("\\s")[0]);  
            }   
        }   
        return list;   
    }
    
    /**
      * 检查html中第一个图片链接
      * @param html html字符串
      * @return 图片链接
      *
     */
	@SuppressWarnings("rawtypes")
    public static String checkHtmlImgUrl(String html) {
		String imgUrl = "";
		if (html != null) {
			List list = ValidateUtils.checkHtmlImg(html);
			if(list!=null && list.size() > 0){
				String tempImg = list.get(0).toString();
				if(tempImg!=null && tempImg.trim().length() > 0){
					if(tempImg.endsWith(".jpg") || tempImg.endsWith(".jpeg")){
						imgUrl  = tempImg;
					}
				}
			}
		}
		return imgUrl;
	}
	
	// \b 是单词边界(连着的两个(字母字符 与 非字母字符) 之间的逻辑上的间隔),  
		// 字符串在编译时会被转码一次,所以是 "\\b"  
		// \B 是单词内部逻辑间隔(连着的两个字母字符之间的逻辑上的间隔)  
		static String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i"  
		        +"|windows (phone|ce)|blackberry"  
		        +"|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp"  
		        +"|laystation portable)|nokia|fennec|htc[-_]"  
		        +"|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";  
		static String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser"  
		        +"|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";  
		
		//移动设备正则匹配：手机端、平板
		static Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);  
		static Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);  
		  
		/**
		 * 检测是否是移动设备访问
		 * 
		 * @Title: check
		 * @Date : 2014-7-7 下午01:29:07
		 * @param userAgent 浏览器标识
		 * @return true:移动设备接入，false:pc端接入
		 */
		public static boolean check(String userAgent){  
		    if(null == userAgent){  
		        userAgent = "";  
		    }  
		    // 匹配  
		    Matcher matcherPhone = phonePat.matcher(userAgent);  
		    Matcher matcherTable = tablePat.matcher(userAgent);  
		    if(matcherPhone.find() || matcherTable.find()){  
		        return true;  
		    } else {  
		        return false;  
		    }  
		}
		
		/**
		 * 检查访问方式是否为移动端
		 */
		public static boolean check(HttpServletRequest request) throws IOException{
			boolean isFromMobile = false;
			HttpSession session = request.getSession();
			//检查是否已经记录访问方式（移动端或pc端）
			if(null==session.getAttribute("ua")){
				try{
					//获取ua，用来判断是否为移动端访问
					String userAgent = request.getHeader("USER-AGENT").toLowerCase();  
					if(null == userAgent){  
					    userAgent = "";  
					}
					
					isFromMobile = ValidateUtils.check(userAgent);
					//判断是否为移动端访问
					if(isFromMobile){
						System.out.println("移动端访问");
						session.setAttribute("ua","mobile");
					} else {
						System.out.println("pc端访问");
						session.setAttribute("ua","pc");
					}
				}catch(Exception e){}
			}else{
				isFromMobile = session.getAttribute("ua").equals("mobile");
			}
			return isFromMobile;
		}
}
