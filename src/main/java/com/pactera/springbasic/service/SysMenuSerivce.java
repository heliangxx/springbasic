package com.pactera.springbasic.service;

import javax.annotation.Resource;

import com.pactera.common.database.TargetDataSource;
import com.pactera.springbasic.Entity.SysMenu;
import com.pactera.springbasic.repository.SysMenuRepository;

import org.springframework.stereotype.Service;

@Service
public class SysMenuSerivce {
    @Resource
    private SysMenuRepository menuRepo;
    
    public Iterable<SysMenu> findAll() {
		return menuRepo.findAll();
    }
    @TargetDataSource(value = "ds1")
    public Iterable<SysMenu> findAll2() {
		return menuRepo.findAll();
	}
}