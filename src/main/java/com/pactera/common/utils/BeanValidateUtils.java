/**
 * 项目名：tahoe
 * 文件名：ValidateUtil.java
 * 版本信息：1.0.0
 * 日期：2016年11月21日-下午12:31:19
 * Copyright (c) 2016Pactera-版权所有
 */
 
package com.pactera.common.utils;

import java.util.Collection;
import java.util.Map;

/**
 * 类名称：DataUtil
 * 类描述：数据集工具类
 * 创建人：lee
 * @version 1.0.0
 */
public class BeanValidateUtils {

	/**
	 * 方法描述：是否为空集合
	 * @param collection 集合
	 * @return boolean true：为空,false:非空
	 * 创建人：lee
	 * 创建时间：2016年11月22日 下午9:54:02
	 */
	public static boolean isEmpty(Collection<Object> collection){
		if(collection!=null && collection.size() > 0){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 方法描述：是否为非空集合
	 * @param collection 集合
	 * @return boolean true:非空,false:为空
	 * 创建人：lee
	 * 创建时间：2016年11月22日 下午9:54:42
	 */
	public static boolean isNotEmpty(Collection<Object> collection){
		if(!isEmpty(collection)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 方法描述：是否为空map
	 * @return boolean true:为空,false:非空
	 * 创建人：lee
	 * 创建时间：2016年11月22日 下午9:55:21
	 */
	public static boolean isEmpty(Map<String,Object> map){
		if(map!=null && map.size() > 0){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 方法描述：是否为非空map
	 * @return boolean true:非空,false:空
	 * 创建人：lee
	 * 创建时间：2016年11月22日 下午9:56:00
	 */
	public static boolean isNotEmpty(Map<String,Object> map){
		if(!isEmpty(map)){
			return true;
		}else{
			return false;
		}
	}
}
