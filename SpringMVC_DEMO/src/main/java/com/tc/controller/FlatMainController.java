package com.tc.controller;

import com.tc.common.form.WebInput;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created with IntelliJ IDEA.
 * Project name:IntimateManager
 * module name:
 * User: tangcheng
 * Create Time: 2015/10/18 20:21
 */
@Controller
@RequestMapping("/im")
public class FlatMainController {

    @RequestMapping("/flatyMain.do")
    public ModelAndView flatyMain(WebInput input){
        return new ModelAndView("main.index.flaty.houtai");
    }
}