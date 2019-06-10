package com.pactera.springbasic.controller;

import java.util.function.Supplier;

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
    public JsonResp defaultDsTest(){
        Supplier<Iterable<SysMenu>> businessHandler = () -> {
            return menuService.findAll();
        };
        return handleRequest(businessHandler); 
    }
    @ApiOperation(value = "获取数据源ds1数据", notes = "获取数据源ds1数据")
	@RequestMapping(value = "/ds1SysMenu", method = RequestMethod.GET)
    public JsonResp ds1DsTest(){
        Supplier<Iterable<SysMenu>> businessHandler = () -> {
            return menuService.findAll2();
        };
        return handleRequest(businessHandler); 
    }
}