/*
 * File name: SeparateJdbcTemplateHandler.java
 * Product:lionking
 * Version: 1.0
 * Copyright 2019 Pactera Technology. Co. Ltd. All Rights Reserved.
 */
package com.pactera.common.database;

import java.util.List;
import java.util.Map;

import com.pactera.common.utils.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;



/**
 * Jdbctemplate辅助类
 * @ClassName：JdbcTemplateHandler
 * @Description：Jdbctemplate辅助类
 * @author pactera 
 * @date 2018年10月19日 下午5:25:08 
 * @version 1.0.0
 */

/**
 * @ClassName：SeparateJdbcTemplateHandler
 * @Description：SeparateJdbcTemplateHandler
 * @author pactera 
 * @date 2019年5月13日 下午7:04:27 
 * @version 1.0.0 
 */
@PropertySource({"classpath:/config.properties"})
@Component
public class SeparateJdbcTemplateHandler {
     
	@Value(value = "${page.size}")
	private Integer pageSize ;
	
	/**
	 * @return the jdbcTemplate
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 自定义通用分页方法
	 */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Transactional(readOnly = true)
	public Page getPageData(String sql, int pageNo, int count) throws Exception {
        //查询总数sql
        String countSql = "select count(*) from (" + sql + ") cnt";
        Long totalCount = jdbcTemplate.queryForObject(countSql, Long.class);
        
        //检验pageNo count 是否为-1 如果是 返回查询到的所有信息
        	if(pageNo==1 && count==1) {
        		 List<Map<String, Object>> resultAllMapList  = jdbcTemplate.queryForList(sql);
                 //分页对象
                 PageRequest pageAllRequest = buildPageRequest(pageNo, this.pageSize);
                 Page page = new Page(resultAllMapList, pageAllRequest, totalCount);
                 
                 System.out.println(sql);
                 
                 return page;
        	}else
        	{
                //查询分页sql
                String pageSql = sql + " limit " + (pageNo - 1) * this.pageSize + "," + count;
                List<Map<String, Object>> resultMapList  = jdbcTemplate.queryForList(pageSql);
                
                //检查数据从首页查询
                if(pageNo!=1 && resultMapList.isEmpty()){
                    pageNo =1;
                    String tmpPageSql = sql + " limit " + (pageNo - 1) * this.pageSize + "," + count;
                    resultMapList  = jdbcTemplate.queryForList(tmpPageSql);
                }
                
                //分页对象
                PageRequest pageRequest = buildPageRequest(pageNo, this.pageSize);
                Page page = new Page(resultMapList, pageRequest, totalCount);
                
                System.out.println(sql);
                
                return page;
        	}
        

	}
    
	/**
	 * 创建分页请求.
	 */
	@Transactional(readOnly = true)
	public PageRequest buildPageRequest(int pageNumber, int pagzSize) {
		return  PageRequest.of(pageNumber - 1, pagzSize, null);
	}

	/**
	 * 
	  * 执行sql语句
	  * @param sql
	  * @throws Exception 
	  * @author   wangting　2016年5月25日
	  *
	 */
	public void execute(String sql) throws Exception{
	    jdbcTemplate.execute(sql);
	}
}
