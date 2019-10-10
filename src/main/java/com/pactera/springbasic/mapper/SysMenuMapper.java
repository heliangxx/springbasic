package com.pactera.springbasic.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pactera.springbasic.Entity.SysMenu;

import org.apache.ibatis.annotations.Select;

public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 为了区别plus原来的查询select*的字段 ，自定义为dy
     * @param 分页查询
     * @return 多表关联查询
     */
    @Select("SELECT a.role_name,c.menu_name,c.menu_url FROM sys_role a JOIN sys_role_menu b ON a.id = b.role_id  JOIN sys_menu c ON b.menu_id = c.id where a.id=#{id}")
    List<Map<String, Object>> dyGetUserList(Page<Map<String,Object>> page,Integer id);
}