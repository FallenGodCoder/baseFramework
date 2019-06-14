package com.tc.common;

import java.util.HashMap;

/**
 * 返回状态码类
 * Created by tangcheng on 2016/4/15.
 */
public class ResultStatusEntity extends HashMap {
    //状态代码
    private String code;
    //状态附带消息(一般情况不使用,特别是在知晓消息内容的时候)
    private String msg;

    private ResultStatusEntity() {
    }

    private ResultStatusEntity(String code, String msg) {
        this.code = code;
        this.msg = msg;
        put("code",code);
        put("msg", msg);
    }

    public static ResultStatusEntity createInstance(){
        return new ResultStatusEntity();
    }

    public static ResultStatusEntity createInstance(String code){
        ResultStatusEntity resultStatusEntity = new ResultStatusEntity();
        resultStatusEntity.setCode(code);
        return resultStatusEntity;
    }

    public static ResultStatusEntity createInstance(String code, String msg){
        return new ResultStatusEntity(code, msg);
    }

    public ResultStatusEntity put(String key, Object value){
        super.put(key, value);
        return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
        put("code",code);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
        put("msg", msg);
    }
}
