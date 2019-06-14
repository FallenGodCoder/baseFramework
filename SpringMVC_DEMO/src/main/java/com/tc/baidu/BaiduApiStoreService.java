package com.tc.baidu;

import com.tc.baidu.constant.BaiduConstant;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by tangcheng on 2016/3/15.
 */
public class BaiduApiStoreService {
    /**
     * @param httpUrl
     *            :请求接口
     * @param httpArg
     *            :参数
     * @return 返回结果
     */
    public static String request(String httpUrl, String httpArg) throws Exception {
        String apiKey = BaiduConfig.get(BaiduConstant.apiKey);
        if(apiKey == null || apiKey == ""){
            throw new Exception("apikey配置不能为空！");
        }
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + BaiduConstant.apiKey + "=" + apiKey
                + "&" + httpArg;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
//            connection.setRequestProperty(BaiduConstant.apiKey, apiKey);
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
            if(result.matches("^[\"|']{1}\\S{0,}")){
                sbf.deleteCharAt(0);
            }
            if(result.matches("\\S{0,}[\"|']{1}$")){
                sbf.deleteCharAt(sbf.length());
            }
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}