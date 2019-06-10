/**
 * 项目名：DispatchSystem
 * 包名：com.pactera.web
 * 文件名：BaseController.java
 * 版本信息：1.0.0
 * 日期：2019年5月21日
 * Copyright (c) 2019 Pactera 版权所有
 */

package com.pactera.web;

import java.io.File;
import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.multipart.MultipartFile;

import com.pactera.common.utils.MiscUtils;

/**
 * @ClassName：BaseController
 * @Description：所有Controller的基类
 * @author zfh
 * @date 2019年5月21日
 * @version 1.0.0
 */
public class BaseController {
    private static final Logger log = LogManager.getLogger(BaseController.class);

    private JsonResp jsonResp = new JsonResp();

    /**
     * @Description: 控制器标准响应模板<br>
     *               采用高阶函数Supplier实现<br>
     * @param <T>
     * @param businessHandler:业务处理函数
     * @return JsonResp
     * @author zfh
     * @date 2019年5月30日
     */
    protected <T> JsonResp handleRequest(Supplier<T> businessHandler) {
        try {
            T obj = businessHandler.get();
            jsonResp.setSuccess();
            jsonResp.setData(obj);
        } catch (Exception e) {
            handleException(e);
        }

        return jsonResp;
    }

    protected <T> JsonResp importFromExcel(MultipartFile file, File destFile, Supplier<T> importService) {
        if (destFile.exists()) {
            destFile.delete();
        }

        try {
            file.transferTo(destFile);
            T obj = importService.get();
            jsonResp.setSuccess();
            jsonResp.setData(obj);
        } catch (Exception e) {
            handleException(e);
        }
        return jsonResp;
    }

    protected int limit(int value, int min, int max) {
        return Math.min(Math.max(value, min), max);
    }

    /** 获取应用的根路径 */
    protected String getWebBasePath(HttpServletRequest request) {
        return request.getSession().getServletContext().getRealPath("");
    }

    /**
     * 
     * @Description: 编写一个泛型方法对异常进行包装,以解决lambda表达式无法抛出Checked异常的问题(但可以抛出Unchecked
     *               异常)<br>
     *               其原理是利用泛型把要抛出异常的类型隐藏起来<br>
     * @param <E>
     * @param e
     * @throws E void
     * @author zfh
     * @date 2019年5月30日
     */
    @SuppressWarnings("unchecked")
    protected static <E extends Exception> void throwException(Exception e) throws E {
        throw (E) e;
    }

    private void handleException(Exception e) {
        if (e instanceof DuplicateKeyException) {
            jsonResp.setError("数据重复！");
        } else if (e instanceof DataIntegrityViolationException) {
            jsonResp.setError("请检查主键是否重复！");
        } else {
            jsonResp.setError(e.getMessage());
        }

        jsonResp.setData(null);
        log.error(MiscUtils.getErrorInfoFromException(e));
    }
}
