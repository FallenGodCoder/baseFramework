package com.tc.controller;

import com.tc.baidu.BaiduApiStoreService;
import com.tc.baidu.BaiduConfig;
import com.tc.baidu.constant.BaiduApiEnum;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 百度服务获取
 * Created by tangc on 2016/3/15.
 */
@Controller
@RequestMapping("/badu/api")
public class BaiduApiController {
    Logger logger = Logger.getLogger(BaiduApiController.class);

    @RequestMapping(value = "/getService.do")
    @ResponseBody
    public String getService(String apiName,String args){
        BaiduApiEnum apiEnum = BaiduApiEnum.getEnum(apiName);
        if (null == apiEnum) return "Not found:"+apiName+" api";
        String httpUrl = BaiduConfig.get(apiEnum.getName());
//        String httpArg = "dtype=JSON&keyword=%E5%A4%A9%E6%89%8D&page=1&rows=20";
        String jsonResult = "";
        try {
            jsonResult = BaiduApiStoreService.request(httpUrl, args);
            logger.debug(jsonResult);
        } catch (Exception e) {
            jsonResult = "server error:api error!";
            e.printStackTrace();
        }
        return jsonResult;
    }
}
