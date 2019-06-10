package com.pactera.common.file;

import java.util.Collection;
import java.util.Map;

/**
 * 
 * 数据集工具类
 * @author solutionNC 2018年1月5日
 */
public class CollectionUtils {

	/**
	 * 是否为空集合.
	 * @param collection 集合
	 * @return true 为空；false 非空
	 */
	public static <T> boolean isEmpty(Collection<T> collection){
		return (collection == null || collection.size() == 0);
	}
	
	/**
	 * 是否为非空集合.
	 * @param collection 集合
	 * @return true 非空；false 为空
	 */
	public static <T> boolean isNotEmpty(Collection<T> collection){
		return !isEmpty(collection);
	}
	
	/**
	 * 是否为空map.
	 * @return true 为空；false 非空
	 */
	public static <K, V> boolean isEmpty(Map<K,V> map){
		return (map == null || map.size() == 0);
	}
	
	/**
	 * 是否为非空map.
	 * @return true 非空；false 空
	 */
	public static <K,V> boolean isNotEmpty(Map<K,V> map){
		return !isEmpty(map);
	}
}
