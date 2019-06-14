package com.tc.baidu;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * 百度配置文件
 * Created by tangcheng on 2016/3/15.
 */
public class BaiduConfig {
    public static Properties properties;
    private BaiduConfig(){}
    static {
        properties = new Properties();
        try {
            properties.load(new ClassPathXmlApplicationContext().getResource("classpath:conf/baidu_config.properties").getInputStream());
        } catch (IOException e) {
            System.out.println("没有找到该配置文件!");
            e.printStackTrace();
        }
    }
    public static String get(String key){
        return (String) properties.get(key);
    }
}
