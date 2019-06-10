package com.pactera.springbasic.repository;

import com.pactera.springbasic.Entity.SysMenu;

import org.springframework.data.repository.CrudRepository;

public interface SysMenuRepository extends CrudRepository<SysMenu, String> {}