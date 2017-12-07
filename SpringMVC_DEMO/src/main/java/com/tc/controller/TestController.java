package com.tc.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by tangcheng on 2017/12/7.
 */
// 注解标注此类为springmvc的controller，url映射为"/home"
@Controller
@RequestMapping("/test")
public class TestController {
    //添加一个日志器
    private static final Logger logger = Logger.getLogger(TestController.class);

    @RequestMapping("/index")
    public String test(){
        //输出日志文件
        logger.info("the first jsp pages");
        //返回一个index.jsp这个视图
        return "index";
    }
}
