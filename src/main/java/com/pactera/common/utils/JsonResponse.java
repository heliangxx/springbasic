/**
 * 包名：com.pactera.common.utils
 * 文件名：JsonResult.java
 * 版本信息：1.0.0
 * 日期：2016年11月21日-下午12:27:21
 * Copyright (c) 2016Pactera-版权所有
 */

package com.pactera.common.utils;


import java.util.HashMap;

/**
 * Json响应实体类
 * @ClassName：JsonResponse
 * @Description：用于api返回json
 * @author pactera 
 * @date 2018年12月3日 下午4:52:14 
 * @version 1.0.0
 */
public class JsonResponse extends HashMap<String, Object> {

	private static final long serialVersionUID = -7484781490745076286L;

	/**
	 * 成功返回默认消息
	 * 
	 * @return JsonResponse
	 * @author pactera
	 * @date 2018年12月3日 下午4:51:40
	 */
	public static JsonResponse success() {
		JsonResponse jsonResponse = new JsonResponse();
		jsonResponse.put("status", 0);
		jsonResponse.put("message", "成功");
		return jsonResponse;
	}

	/**
	 * 成功返回自定义消息
	 * 
	 * @param successMsg  成功消息
	 * @return JsonResponse
	 * @author pactera
	 * @date 2018年12月3日 下午4:51:08
	 */
	public static JsonResponse success(String successMsg) {
		JsonResponse jsonResponse = new JsonResponse();
		jsonResponse.put("status", 0);
		jsonResponse.put("message", successMsg);
		return jsonResponse;
	}

	/**
	 * 成功并返回数据&默认消息
	 * 
	 * @param obj    返回数据对象
	 * @return JsonResponse
	 * @author pactera
	 * @date 2018年12月3日 下午4:50:28
	 */
	public static JsonResponse successData(Object obj) {
		JsonResponse jsonResponse = new JsonResponse();
		jsonResponse.put("status", 0);
		jsonResponse.put("message", "成功");
		jsonResponse.put("data", obj);
		return jsonResponse;
	}

	/**
	 * 成功并返回数据&自定义消息
	 * 
	 * @param successMsg 成功消息
	 * @param obj 返回数据
	 * @return JsonResponse
	 * @author pactera
	 * @date 2018年12月3日 下午4:48:00
	 */
	public static JsonResponse successData(String successMsg, Object obj) {
		JsonResponse jsonResponse = new JsonResponse();
		jsonResponse.put("status", 0);
		jsonResponse.put("message", "成功");
		jsonResponse.put("data", obj);
		return jsonResponse;
	}
	
	/**
	 * 成功并返回分页数据
	 * 
	 * @param successMsg
	 * @param page 分页对象
	 * @return JsonResponse
	 * @author pactera
	 * @date 2018年12月22日 上午11:56:27
	 */
	public static JsonResponse successPageData(Page<Object> page) {
		JsonResponse jsonResponse = new JsonResponse();
		jsonResponse.put("status", 0);
		jsonResponse.put("message", "成功");
		jsonResponse.put("data", page);
		return jsonResponse;
	}
	
	
	/**
	 * 失败返回默认异常消息
	 * 
	 * @return JsonResponse
	 * @author pactera
	 * @date 2018年12月5日 上午11:05:46
	 */
	public static JsonResponse error() {
		JsonResponse jsonResponse = new JsonResponse();
		jsonResponse.put("status", 1000);
		jsonResponse.put("message", "系统异常");
		return jsonResponse;
	}
	
	/**
	 * 失败返回自定义错误消息
	 * 
	 * @param errorMsg 错误消息
	 * @return JsonResponse
	 * @author pactera
	 * @date 2018年12月22日 下午12:06:59
	 */
	public static JsonResponse error(String errorMsg) {
		JsonResponse jsonResponse = new JsonResponse();
		jsonResponse.put("status", 1000);
		jsonResponse.put("message", errorMsg);
		return jsonResponse;
	}
	
	/**
	 * 失败并返回自定义错误code&消息
	 * 
	 * @param errorCode 错误编码
	 * @param errorMsg 错误消息
	 * @return JsonResponse
	 * @author pactera
	 * @date 2018年12月3日 下午4:49:41
	 */
	public static JsonResponse error(String errorCode, String errorMsg) {
		JsonResponse jsonResponse = new JsonResponse();
		jsonResponse.put("status", errorCode);
		jsonResponse.put("message", errorMsg);
		return jsonResponse;
	}
}
