package com.pactera.springbasic.controller;

import java.util.function.Supplier;

import com.pactera.web.BaseController;
import com.pactera.web.JsonResp;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "swaggerDemo-接口")
@RestController
@RequestMapping("/swaggerdemo")
public class swaggerDemoController extends BaseController {
   
	@ApiOperation(value = "swaggerdemo", notes = "欢迎语测试")
	@RequestMapping(value = "/demo", method = RequestMethod.GET)
    public JsonResp swggerDemo() {
        Supplier<String> businessHandler = () -> {
            return "Hello swagger";
        };∏
        return handleRequest(businessHandler);
    }
}