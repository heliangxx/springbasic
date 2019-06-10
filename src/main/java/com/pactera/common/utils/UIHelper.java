
package com.pactera.common.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 类名称：UIHelper
 * 类描述：前端辅助工具类
 * 创建人：lee
 * 创建时间：2016年11月20日 下午11:04:55
 * @version 1.0.0
 */
public class UIHelper {
	
	/**
	  * 获取域名
	 */
	public static String getWebRoot(HttpServletRequest request){
		StringBuffer url = request.getRequestURL();  
		String webUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append(request.getServletContext().getContextPath()).toString(); 
		return webUrl;
	}
	
	
	public static String getWebDomain(HttpServletRequest request){
		StringBuffer url = request.getRequestURL();  
		String webUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).toString(); 
		return webUrl;
	}
	
	
	/**
	  * 方法说明: 获取ip
	 */
	public static String getRemoteHost(HttpServletRequest request){
	    String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
            }
        return "0:0:0:0:0:0:0:1".equals(ip)?"127.0.0.1":ip;
    }
	
	/**
     * 获取项目根路径
     */
    public static String getWebContext(HttpServletRequest request){
        String webContext = request.getContextPath();
        return webContext;
    }
    
	/**
     * 获取项目部署物理路径
     */
    public static String getWebRealPath(HttpServletRequest request){
        String webRealPath = request.getSession().getServletContext().getRealPath("");
        return webRealPath;
    }
}
