package com.tc.baidu.constant;

/**
 * 所支持的api
 * Created by tangcheng on 2016/3/15.
 */
public enum BaiduApiEnum {
    API_MINGRENMINGYAN("API_MINGRENMINGYAN",0);

    private String name;
    private Integer index;
    private BaiduApiEnum(String name, Integer index){
        this.name = name;
        this.index = index;
    }

    public static String getName(Integer index){
        for(BaiduApiEnum apiEnum:BaiduApiEnum.values()){
            if(apiEnum.getIndex() == index) return apiEnum.getName();
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public static BaiduApiEnum getEnum(String apiName) {
        for(BaiduApiEnum apiEnum:BaiduApiEnum.values())
            if (apiEnum.getName().equalsIgnoreCase(apiName))
                return apiEnum;
        return null;
    }
}
