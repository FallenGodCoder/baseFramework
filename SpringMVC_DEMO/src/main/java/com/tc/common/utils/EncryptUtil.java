package com.tc.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 密码加密工具
 * @author xiaobo.
 *         on 2015/4/20.
 */
public class EncryptUtil {

    private String input;
    private Encode encode;

    /**
     * 加密类型
     *
     */
    public  enum  EncryptType{
        ENCRYPT_TYPE_SHA1;
    }

    public  static  class  EncryptBuilder{
       private EncryptType type;
        private String input;
        /**
         * 输入值
         * @param input
         * @return
         */
        public EncryptBuilder input(String input){
            this.input = input;
            return this;
        }
        /**
         * 编码类型
         * @param type
         * @return
         */
        public EncryptBuilder encryptType(EncryptType  type){
           this.type = type;
           return  this;
        }

        public EncryptUtil build(){
            if(this.type == null){
                throw new RuntimeException(
                        "EncryptType must be set a value,you can call \"encryptType()\" before build");
            }

            EncryptUtil util = new EncryptUtil();
            util.input = this.input;
            switch (this.type){
                case ENCRYPT_TYPE_SHA1:
                    util.encode = new SHA1Encode();
                    break;
                default:
                    util.encode = new SHA1Encode();
            }


            return  util;
        }

    }

    /**
     * 输出加密后结果，如果input 为null或为空，输出空字符串
     * @return 加密后结果
     */
   public String result(){
       if(!this.needEncode()){
           return "";
       }
       return  encode.encode(this.input);
   }

    /**
     * 是否需要编码
     * @return
     */
    private boolean needEncode(){
        if(this.input == null || this.input.isEmpty() ||this.encode == null ){
            return false;
        }
        return true;
    }

   public static EncryptBuilder newBuilder(){
       return new EncryptBuilder();
   }

    public static void main(String[] args) {
        long timestamp = System.currentTimeMillis() / 1000;
        timestamp = 1448159408340L;
        EncryptUtil result= EncryptUtil.newBuilder().encryptType(EncryptUtil.EncryptType.ENCRYPT_TYPE_SHA1).
                input("" + timestamp + "elearning" + "杨玉宝").build();
        System.err.println(result.result());
        System.err.println("DAE5BD0C6D42E120D2D31CB36E0D1F4178B1F963".equals(result.result()));
    }
}

/**
 * 编码
 */
interface  Encode{
    /**
     * 编码
     * @param string
     * @return
     */
    String encode(String string);
}

/**
 * SHA1 编码
 */
class  SHA1Encode implements  Encode{
    MessageDigest messageDigest;

    public SHA1Encode()  {

        try {
            messageDigest =  MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
           throw new RuntimeException(e);
        }


    }
    /**
     * 编码成16进制显示
     *
     * @param string
     * @return
     */
    public String encode(String string) {
        messageDigest.reset();
        byte[]  result = new byte[0];
        try {
            result = messageDigest.digest(string.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return byte2hex(result);
    }

    public String byte2hex(byte[] b)
    {
        StringBuffer hexStringBuffer = new StringBuffer();
        String stmp="";
        for (int n=0;n<b.length;n++)
        {
            stmp=(Integer.toHexString(b[n] & 0XFF));
            if (stmp.length()==1) {
                hexStringBuffer.append("0");
            }

            hexStringBuffer.append(stmp);

 //            if (n<b.length-1)
//                hexStringBuffer.append("-");
        }
        return hexStringBuffer.toString().toUpperCase();
    }
}
