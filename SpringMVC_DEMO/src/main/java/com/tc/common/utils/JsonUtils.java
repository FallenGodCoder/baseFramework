package com.tc.common.utils;


import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JsonUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        objectMapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS,true);

    }

    public static String toString(Object obj) {
        if (obj == null) {
            return StringUtils.EMPTY;
        }
        try {
            return toString(null, obj);
        } catch (IOException io) {

        }
        return StringUtils.EMPTY;
    }

    public static String toString(String rootName, Object obj) throws IOException {
        Object _obj = obj;
        if (StringUtils.isNotBlank(rootName)) {
            _obj = Collections.singletonMap(rootName, obj);
        }
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        return objectMapper.writeValueAsString(_obj);
    }


    public static <T> List<T> arrayStringToList(Class<T[]> cls, String json) {
        List<T> answer = new ArrayList<T>();
        if (StringUtils.isEmpty(json) || cls == null) {
            return answer;    //null
        }
        try {
            T[] arr = objectMapper.readValue(json, cls);
            return Arrays.asList(arr);
        } catch (Exception e) {
            //e.printStackTrace();
            //不用处理
        }
        return answer;    //null
    }

    /**
     * json字符串转化成一个对象
     *
     * @param cls
     * @param json
     * @param <T>
     * @return
     */
    public static <T> T stringToObject(Class<T> cls, String json) {
        T answer = null;
        if (StringUtils.isEmpty(json) || cls == null) {
            return answer;    //null
        }
        try {
            answer = objectMapper.readValue(json, cls);
        } catch (Exception e) {
            e.printStackTrace();
            //不用处理
        }
        return answer;
    }

    /**
     * json字符串流转化成一个对象
     *
     * @param cls
     * @param json
     * @param <T>
     * @return
     */
    public static <T> T stringToObject(Class<T> cls,InputStream json) {
        T answer = null;

        try {
            answer = objectMapper.readValue(json, cls);
        } catch (Exception e) {
            e.printStackTrace();
            //不用处理
        }
        return answer;
    }



    public static <T> T[] StringToArray(Class<T[]> cls, String json) {
        try {
            T[] arr = objectMapper.readValue(json, cls);
            return arr;
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String jsonCharFilter(String sourceStr) {
        sourceStr = sourceStr.replace("\\", "\\\\");
        sourceStr = sourceStr.replace("\b", "\\\b");
        sourceStr = sourceStr.replace("\t", "\\\t");
        sourceStr = sourceStr.replace("\n", "\\\n");
        sourceStr = sourceStr.replace("\n", "\\\n");
        sourceStr = sourceStr.replace("\f", "\\\f");
        sourceStr = sourceStr.replace("\r", "\\\r");
        return sourceStr.replace("\"", "\\\"");
    }

    public static void main(String[] args) {
//        TreeMap<Integer, Integer> aaaa = new TreeMap<Integer, Integer>();
//        Integer[] aaa = {1, 3, 1, 5, 3,4,2,5,2,1,6,8,9};
//        for(Integer _aa : aaa){
//            Integer ccc = aaaa.get(_aa);
//            if(ccc == null){
//                ccc = 0;
//            }
//            aaaa.put(_aa, ++ccc);
//        }
//        for(Map.Entry<Integer, Integer> cc : aaaa.entrySet()){
//            System.out.print(cc.getKey()+":");
//            System.out.print(cc.getValue());
//        }
//        Boolean aaa = (Boolean)null;
//        System.out.print("bbbb");
//        if(aaa){
//            System.out.print("aaaa");
//        }
//        Map<String, Object> bbb = new HashMap<String, Object>();
//         bbb.put("aaaa", null);

//        bbb.put("vbbbb", Arrays.asList(1, 2, 3, 4));
//
//        Map sss = JsonUtils.stringToObject(Map.class, JsonUtils.toString(bbb));
//        for (Object aaa: sss.keySet()) {
//            System.out.println(aaa);
//        }
    }


    /**
     * 对象转化成Json字符串
     *
     * @param o java对象
     * @return 空串或者json字符串
     */
    public static String objectToJsonString(Object o) {
        String answer = "";
        if (o == null) {
            return answer;    //null
        }
        return toString(o);
    }

}
