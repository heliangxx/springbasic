/**
 * 包名：com.pactera.common.utils
 * 文件名：JsonResult.java
 * 版本信息：1.0.0
 * 日期：2016年11月21日-下午12:27:21
 * Copyright (c) 2016Pactera-版权所有
 */

package com.pactera.web;

import org.apache.http.HttpStatus;

import com.alibaba.fastjson.JSON;

/**
 * Json响应实体类
 * 
 * @ClassName：JsonResp
 * @Description：用于api返回json
 * @author zfh
 * @date 2018年12月3日
 * @version 1.0.0
 */
public class JsonResp {
    private Integer status;
    private String msg;
    private Object data;

    public void setSuccess() {
        status = HttpStatus.SC_OK;
        msg = "";
    }

    public void setError(String msg) {
        status = HttpStatus.SC_NOT_FOUND;
        this.msg = msg;
    }

    public void needAuthorization() {
        status = HttpStatus.SC_UNAUTHORIZED;
        this.msg = "没有访问权限";
    }

    public void setData(Object obj) {
        data = obj;
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public Integer getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }
}
