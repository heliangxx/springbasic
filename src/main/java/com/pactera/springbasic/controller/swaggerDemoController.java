package com.pactera.springbasic.controller;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pactera.springbasic.Entity.SysMenu;
import com.pactera.springbasic.service.SysMenuSerivce;
import com.pactera.web.BaseController;
import com.pactera.web.JsonResp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "swaggerDemo-接口")
@RestController
@RequestMapping("/swaggerdemo")
public class swaggerDemoController extends BaseController {
    @Autowired
    private SysMenuSerivce menuService;

    @ApiOperation(value = "swaggerdemo", notes = "欢迎语测试")
    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    public JsonResp swggerDemo() {
        Supplier<String> businessHandler = () -> {
            return "Hello swagger";
        };
        return handleRequest(businessHandler);
    }

    @ApiOperation(value = "获取默认数据源数据", notes = "获取默认数据源数据")
    @RequestMapping(value = "/firstSysMenu", method = RequestMethod.GET)
    public JsonResp defaultDsTest() {
        Supplier<Iterable<SysMenu>> businessHandler = () -> {
            return menuService.findAll();
        };
        return handleRequest(businessHandler);
    }

    @ApiOperation(value = "获取默认数据源数据MybatisPlus", notes = "获取默认数据源数据MybatisPlus")
    @RequestMapping(value = "/firstSysMenuMybatis", method = RequestMethod.GET)
    public JsonResp defaultDsMyBatisTest() {
        Supplier<Iterable<SysMenu>> businessHandler = () -> {
            return menuService.findAllByMybatis();
        };
        return handleRequest(businessHandler);
    }

    @ApiOperation(value = "获取数据源ds1数据", notes = "获取数据源ds1数据")
    @RequestMapping(value = "/ds1SysMenu", method = RequestMethod.GET)
    public JsonResp ds1DsTest() {
        Supplier<Iterable<SysMenu>> businessHandler = () -> {
            return menuService.findAll2();
        };
        return handleRequest(businessHandler);
    }

    @ApiOperation(value = "获取数据源ds1数据Mysbatis", notes = "获取数据源ds1数据Mybatis")
    @RequestMapping(value = "/ds1SysMenuMybatis", method = RequestMethod.GET)
    public JsonResp ds1DsMybatisTest() {
        Supplier<Iterable<SysMenu>> businessHandler = () -> {
            return menuService.findAllByMybatis2();
        };
        return handleRequest(businessHandler);
    }

    @ApiOperation(value = "获取默认数据源数据Sql", notes = "获取默认数据源数据Sql")
    @RequestMapping(value = "/firstMenuSql", method = RequestMethod.GET)
    public JsonResp defaultDsSql() {
        Supplier<List<Map<String, Object>>> businessHandler = () -> {
            try {
                return menuService.findAllBySql();
            } catch (Exception e) {
                throwException(e);
            }
            return null;
        };
        return handleRequest(businessHandler);
    }

    @ApiOperation(value = "获取数据源ds1数据", notes = "获取数据源ds1数据Sql")
    @RequestMapping(value = "/ds1SysMenuSql", method = RequestMethod.GET)
    public JsonResp ds1DsSqlResp() {
        Supplier<List<Map<String, Object>>> businessHandler = () -> {
            try {
                return menuService.findDs1AllBySql();
            } catch (Exception e) {
                throwException(e);
            }
            return null;
        };
        return handleRequest(businessHandler);
    }

    @ApiOperation(value = "获取默认数据源数据Mybatis Sql", notes = "获取默认数据源数据Mybatis Sql")
    @RequestMapping(value = "/firstMenuMybatisSql", method = RequestMethod.GET)
    public JsonResp defaultDsMybatisSql() {
        Supplier<Page<Map<String, Object>>> businessHandler = () -> {
            try {
                return menuService.selectListPage(1, 2);
            } catch (Exception e) {
                throwException(e);
            }
            return null;
        };
        return handleRequest(businessHandler);
    }
}