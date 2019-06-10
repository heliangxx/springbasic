package com.pactera.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @ClassName：MiscUtils
 * @Description：杂项功能
 * @author zfh 
 * @date 2019年5月27日 
 * @version 1.0.0 
 */
public class MiscUtils {
	
	public static String getErrorInfoFromException(Exception e) {  
        try {  
            StringWriter sw = new StringWriter();  
            PrintWriter pw = new PrintWriter(sw);  
            e.printStackTrace(pw);  
            return "\r\n" + sw.toString() + "\r\n";  
        } catch (Exception e2) {  
            return "bad getErrorInfoFromException";  
        }  
    } 
	
}