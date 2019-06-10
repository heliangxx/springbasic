package com.pactera.springbasic.Entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "sys_menu")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = -8174426348958474487L;
    /**
     * CREATE TABLE `sys_menu` ( `id` bigint(20) NOT NULL AUTO_INCREMENT,
     * `menu_name` varchar(50) DEFAULT NULL COMMENT '目录名称', `menu_url` varchar(255)
     * DEFAULT NULL COMMENT '菜单URL', `perms` varchar(255) DEFAULT NULL COMMENT
     * '授权(多个用逗号分隔，如：user:list,user:create)', `menu_icon` varchar(50) DEFAULT NULL
     * COMMENT '菜单icon', `parent_id` bigint(20) DEFAULT NULL COMMENT '父级目录，0是顶级',
     * `type` int(11) DEFAULT NULL COMMENT '菜单类型： 0：目录 1：菜单 2：按钮', PRIMARY KEY
     * (`id`) USING BTREE ) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "menu_icon")
    private String menuIcon;
    @Column(name = "menu_name")
    private String menuName;
    @Column(name = "menu_url")
    private String menuUrl;
    @Column(name = "parent_id")
    private String parentId;
    @Column(name = "perms")
    private String perms;
    @Column(name = "type")
    private int type;

}