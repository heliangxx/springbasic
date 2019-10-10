package com.pactera.springbasic.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.pactera.common.database.JdbcTemplateHandler;
import com.pactera.common.database.TargetDataSource;
import com.pactera.springbasic.Entity.SysMenu;
import com.pactera.springbasic.mapper.SysMenuMapper;
import com.pactera.springbasic.repository.SysMenuRepository;

import org.springframework.stereotype.Service;

@Service
public class SysMenuSerivce {
    @Resource
    private SysMenuRepository menuRepo;
    @Resource
    private JdbcTemplateHandler jdbcTemplateHandler;
    @Resource
    private SysMenuMapper sysMenuMapper;

    public Iterable<SysMenu> findAll() {
        return menuRepo.findAll();
    }

    @TargetDataSource(value = "ds1")
    public Iterable<SysMenu> findAll2() {
        return menuRepo.findAll();
    }

    public List<Map<String, Object>> findAllBySql() throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select id,menu_name from sys_menu");
        return jdbcTemplateHandler.queryForMapList(sql.toString());
    }

    @TargetDataSource(value = "ds1")
    public List<Map<String, Object>> findDs1AllBySql() throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select id,menu_name from sys_menu");
        return jdbcTemplateHandler.queryForMapList(sql.toString());
    }

    public List<SysMenu> findAllByMybatis() {
        return sysMenuMapper.selectList(null);
    }
    @TargetDataSource(value = "ds1")
    public List<SysMenu> findAllByMybatis2() {
        return sysMenuMapper.selectList(null);
    }
}