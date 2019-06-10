package com.pactera.common.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StrUtils {
	
    private static Logger logger = LoggerFactory.getLogger(StrUtils.class);
    
    /**
     * 方法说明 判断字符串是否为null或者空串,如果为 null则返回空串,否则返回String.trim 
     * @return
     * @author houyunhe	
     * @date 2016年8月3日
     */
    public static String objToStr(String str){
    	if(str == null || "".equals(str.trim())){
    		return "";
    	}else{
    		return str.trim();
    	}
    }
    
	/**
	 * 判断是否为null或空串
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(str==null){
			return true;
		}
		if("".equals(str.trim())){
			return true;
		}
		if("null".equals(str.trim())) {
			return true;
		}
		return false;
	}
	/**
	 * 判断是否不为null或空串
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		return !isEmpty(str);
	}
	/**
	 * 按分隔符将字符串组装为字符串数组
	 * 如果传入参数为null或空串，直接放回空数组
	 * @param toSplit
	 * @param delimiter
	 * @return
	 */
	public static String[] split(String toSplit,String delimiter){
		if(isNotEmpty(toSplit)){
			return toSplit.split(delimiter);
		}
		return new String[]{};
	}
	/**
	 * 按分隔符将字符串组装为字符串List集合
	 * 如果传入参数为null或空串，直接返回空List
	 * @param toSplit
	 * @param delimiter
	 * @return List<Long>
	 */
	public static List<Long> splitToListLong(String toSplit,String delimiter){
	    List<Long> list = new ArrayList<Long>();
	    String[] strs = split(toSplit, delimiter);
	    for(int i=0; i<strs.length; i++){
	        if(StrUtils.isEmpty(strs[i])){
	            continue;
	        }
	        list.add(Long.parseLong(strs[i],10));
	    }
	    return list;
	}
	/**
	 * 按分隔符将字符串组装为字符串List集合
	 * 如果传入参数为null或空串，直接放回空List
	 * @param toSplit
	 * @param delimiter
	 * @return
	 */
	public static List<String> splitToList(String toSplit,String delimiter){
		List<String> list = new ArrayList<String>();
		String[] strs = split(toSplit, delimiter);
		for(int i=0; i<strs.length; i++){
			list.add(strs[i]);
		}
		return list;
	}
	/**
	 * 按分隔符将字符串组装为字符串List集合,同时去除前后缀
	 * 如果传入参数为null或空串，直接放回空List
	 * @param toSplit
	 * @param delimiter
	 * @param prefix
	 * @param suffix
	 * @return
	 */
	public static List<String> splitToList(String toSplit,String delimiter, String prefix, String suffix){
		String[] split = split(toSplit, delimiter);
		List<String> list = new ArrayList<String>();
		for(int i=0;i<split.length;i++){
			String t = split[i].substring(prefix.length(), split[i].length()-suffix.length());
			list.add(t);
		}
		return list;
	}
	/**
	 * 将html转为存文本
	 * @param htmlStr
	 * @return
	 */
	public static String html2Text(String htmlStr) {
		if (isEmpty(htmlStr)) {
			return "";
		}
		String textStr = "";
		Pattern pScript;
		Matcher mScript;
		Pattern pStyle;
		Matcher mStyle;
		Pattern pHtml;
		Matcher mHtml;

		try {
			String regExScript = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
																										// }
			String regExStyle = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
																									// }
			String regExHtml = "<[^>]+>"; // 定义HTML标签的正则表达式
			
			
			pScript = Pattern.compile(regExScript, Pattern.CASE_INSENSITIVE);
			mScript = pScript.matcher(htmlStr);
			htmlStr = mScript.replaceAll(""); // 过滤script标签

			pStyle = Pattern.compile(regExStyle, Pattern.CASE_INSENSITIVE);
			mStyle = pStyle.matcher(htmlStr);
			htmlStr = mStyle.replaceAll(""); // 过滤style标签

			pHtml = Pattern.compile(regExHtml, Pattern.CASE_INSENSITIVE);
			mHtml = pHtml.matcher(htmlStr);
			htmlStr = mHtml.replaceAll(""); // 过滤html标签
			

			textStr = htmlStr.replaceAll("\r\n", "").replaceAll("\n", "").replaceAll("&nbsp;", "").replaceAll("\t", "").replaceAll("\\[b\\]", "").replaceAll("\\[/b\\]", "").replaceAll("\\[/img\\]", "").replaceAll("\\[img\\]", "").trim();
			textStr = textStr.replaceAll("&amp;ldquo;", "").replaceAll("&amp;rdquo;", "").trim();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return textStr;// 返回文本字符串
	}
	/**
	 * 将html文本转为指定字符数量的文本
	 * @param htmlStr
	 * @param limitNum
	 * @return
	 */
	public static String html2Text(String htmlStr, int limitNum) {
		String textStr = html2Text(htmlStr);
		if (textStr.length() > limitNum) {
			textStr = textStr.trim();
			textStr = textStr.substring(0, limitNum) + "...";
		}
		return textStr;// 返回文本字符串
	}
	/**
	 * 连接返回字符串
	 * @param coll
	 * @param separator
	 * @return
	 */
	public static String join(Collection<?> coll, String separator) {
		return join(coll, separator, "", "");
	}
	/**
	 * 连接返回字符串并添加前缀和后缀
	 * @param coll
	 * @param separator
	 * @param prefix
	 * @param suffix
	 * @return
	 */
	public static String join(Collection<?> coll, String separator, String prefix, String suffix) {
		if (coll==null||coll.isEmpty()) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		Iterator<?> it = coll.iterator();
		while (it.hasNext()) {
			sb.append(prefix).append(it.next()).append(suffix);
			if (it.hasNext()) {
				sb.append(separator);
			}
		}
		return sb.toString();
	}
	
	public static String replace(String inString, String oldPattern, String newPattern) {
		if (isEmpty(inString) || isEmpty(oldPattern) || newPattern == null) {
			return inString;
		}
		StringBuilder sb = new StringBuilder();
		int pos = 0; 
		int index = inString.indexOf(oldPattern);
		int patLen = oldPattern.length();
		while (index >= 0) {
			sb.append(inString.substring(pos, index));
			sb.append(newPattern);
			pos = index + patLen;
			index = inString.indexOf(oldPattern, pos);
		}
		sb.append(inString.substring(pos));
		return sb.toString();
	}
	/**
	 * 为当前匹配的关键词增加高亮标签   
	 * @param content
	 * @param keyword(多关键字用"|"分隔   如 张三|李四)
	 * @return
	 */
	public static String highLightHtmlForSearch(String content, String keyword ){
		Pattern p = Pattern.compile(keyword);  
        Matcher m = p.matcher(content);  
  
        StringBuffer sb = new StringBuffer();  
        while (m.find()) {  
            m.appendReplacement(sb, "<em>$0</em>");  
        }  
        m.appendTail(sb);  
        return sb.toString();  
	}
	/**
	 * 是否包含
	 * @param roles
	 * @param string
	 * @return
	 */
	public static boolean contain(String content, String keyword) {
		if(isEmpty(content)){
			return false;
		}
		return content.indexOf(keyword)>-1;
	}
	   /**
     * @method_name contain
     * @author chenyjv
     * @date 2013年12月12日 下午2:20:55
     * @description  验证用delimiter分隔的str1中是否包含str2
     * @param str1
     * @param str2
     * @param delimiter
     * @return boolean
     * @reviewed_by
    */
    public static boolean contain(String str1, String str2,String delimiter) {
        if (isEmpty(str2) || isEmpty(str1)) {
            return false;
        }
        boolean bol = false;
        String[] splitStr = split(str1, delimiter);
        if (splitStr != null && splitStr.length > 0) {
            for (int i = 0; i < splitStr.length; i++) {
                if (str2.equals(splitStr[i])) {
                    bol = true;
                    break;
                }
            }
        }
        return bol;
    }

    /**
     * 
      * 方法说明：替换疑似xss攻击危险字符
      * @param value
      * @return 
      * @author wangchaojie　2015年10月23日
      *
     */
    private static String cleanXSS(String value) {
        value = value.replaceAll("#", "&#35;");
//        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
//        value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
//        value = value.replaceAll("\"", "&quot;");
//        value = value.replaceAll("\'", "&apos;");
//        value = value.replaceAll("'", "&#39;");
//        value = value.replaceAll("%", "&#37;");
        //影响邮箱地址，导致发送失败，暂时去掉
        //value = value.replaceAll("_", "&#95;");
        value = value.replaceAll("©", "&#169;");
        value = value.replaceAll("®", "&#174;");
        value = value.replaceAll("™", "&#8482;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("<script>", "");
        return value;
    }
    
    /**
     * 
      * 方法说明：替换疑似Sql注入危险字符
      * @param value
      * @return 
      * @author wangchaojie　2015年10月23日
      *
     */
    private static String cleanSQL(String value) {
        String badStr = "'|and|insert|select|delete|update|count|drop|"+
                "sitename|net user|xp_cmdshell|exec|execute|create|"+
                "table|from|grant|use|group_concat|column_name|"+
                "information_schema.columns|table_schema|union|where|order|by|"+
                "chr|mid|master|truncate|char|declare|or|--|like|%|#";
        String[] badStrs = badStr.split("\\|");
        String lowerValue = value.toLowerCase().trim();
        String badSQLStr = "";
        //遍历敏感字符，每个敏感字符中间都要加上空格
        for (int i = 0; i < badStrs.length; i++) {
            //如果字符串中存在敏感字符，并且字符串中存在空格
            if (lowerValue.indexOf(badStrs[i]) >= 0 && lowerValue.indexOf(" ") >= 0) {
                badSQLStr = badStrs[i].substring(0,1)+" "+badStrs[i].substring(1);
                value = value.replaceAll(badStrs[i], badSQLStr);
                logger.warn(lowerValue+"中，包含敏感字符:"+badStrs[i]);
            }
        }
        return value;
    }
    
    /**
     * 
      * 方法说明：过滤替换非法字符
      * @param value
      * @return 
      * @author wangchaojie　2015年10月24日
      *
     */
    public static String cleanIllegalKeys(String value){
        return cleanSQL(cleanXSS(value));
    }
    /**
     * 
      * 判断数据是否为空，返回字符串
      * 
      * @param obj
      * @return 
      * @author  liuxiaoyu　2015年11月11日
      *
     */
    public static String transToString(Object obj){
        if(obj==null){
            return "";
        }else{
            return obj.toString();  
        } 
    }
    
    public static String getSqlInStr(List<Map<String, Object>> list) {
		 if(list.isEmpty()) {
			 return "";
		 }
       StringBuilder sb = new StringBuilder();
       String sep="";
       for(Map<String, Object> map : list) {
           sb.append(sep).append("'").append(map.get("id")).append("'");
           sep =",";
       }
       return sb.toString();
   }
    
    public static String getSqlInStr(Set<String> set) {
		 if(set.size()<0) {
			 return "";
		 }
	      StringBuilder sb = new StringBuilder();
	      String sep="";
	      for(String s : set) {
	          sb.append(sep).append("'").append(s).append("'");
	          sep =",";
	      }
	      return sb.toString();
    }
	
    public static String jointStr(Set<String> set) {
		 if(set.size()<0) {
			 return "";
		 }
	      StringBuilder sb = new StringBuilder();
	      String sep="";
	      for(String s : set) {
	          sb.append(sep).append(s);
	          sep =",";
	      }
	      return sb.toString();
   }
    
	public static String getSqlInStr(String str) {
	   StringBuilder sb = new StringBuilder();
	   String sep="";
	   if(StrUtils.isEmpty(str)) {
		 return "";
	   }
	   if(str.indexOf(",") == -1) {
		   sb.append("'").append(str).append("'");
	   }else {
		   for(String s : str.split(",")) {
		          sb.append(sep).append("'").append(s).append("'");
		          sep =",";
		   }
	   }
	   
       return sb.toString();
  }
	
	/**
	 * 判断数组里面的值是否都相等
	 * @param shus
	 * @return
	 */
	public static boolean determiningWhetherArrayEqual(String [] shus) {
		boolean flag=true;
		for(int i=0;i<shus.length-1;i++)
		{
			for(int j=1;j<shus.length;j++)
			{
				if(!shus[i].equals(shus[j])){
					flag=false;
					break;
				}
			}
			if(flag==false){
				break;
			}
		}
		return flag;
	}	
	
	public static double div(String v1, String v2, int scale) {
		if(isEmpty(v1)) {
			v1="0";
		}
		if (scale < 0) {
			throw new IllegalArgumentException("The   scale   must   be   a   positive   integer   or   zero");
		}
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	} 
	
	/** 采用正则表达式提取子字符串 */
	public static String extractSubstring(String s, String regEx) {
		List<String> subStrs = extractSubstrings(s, regEx);
		if (subStrs.size() >= 1)
			return subStrs.get(0);
		return "";
	}

	/** 采用正则表达式提取子字符串,可能有多个 */
	public static List<String> extractSubstrings(String s, String regEx) {
		List<String> subStrs = new ArrayList<String>();

		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(s);
		while (matcher.find()) {
			// 删除多余的字段分隔符（中英文冒号及空格）
			subStrs.add(matcher.group().trim());
		}
		return subStrs;
	}
}
