/*
 * Author: liuhai
 *
 * Workfile: ValidatorUtils.java
 */

package com.tc.common.utils;

import com.tc.common.BaseConstant;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtils {
    public static final org.slf4j.Logger logger = LoggerFactory.getLogger(ValidatorUtils.class);

    // 邮件正则表达式
    public static final String REGXPFOREMAIL = "^$|^[a-z_A-Z0-9\\-]+@[a-z1-9A-Z\\-]+(.[a-zA-Z]+){1,2}$";

    // 电话正则表达式
    public final static String REGXPFORPHONE = "^$|\\d{3}-\\d{8}|\\d{4}-\\d{7}";

    // 手机正则表达式
    public final static String REGXPFORMOBILE = "^$|^0?[1][3587][0-9]{9}$";

    // URL正则表达式
    public final static String REGXPFORURL = "^((https|http|ftp|rtsp|mms)://)?[A-Za-z0-9]+\\.[A-Za-z0-9]+[\\/=\\?%\\-&_~`@\\':+!]*([^<>\\\"\\\"])*$";

    // 身份证正则表达式
    public final static String REGXPFORIDCARD = "\\d{15}|\\d{18}";

    // 图片尺寸
    public final static String REGXPFORIMAGESIZE = "^\\d+x\\d+$";

    // 货币正则表达式
    public final static String REGXPFORCURRENCY = "\\d+(\\.\\d+)?";

    // 数字正则表达式
    public final static String REGXPFORNUMBER = "\\d+";

    // 正整数或.0正则表达式
    public final static String REGXPFORNUMBEREXT = "^\\d+(.0)?0*$";

    // 数字和字母组合表达式
    public final static String REGXPFORNUMORENGLISH = "^[a-zA-Z0-9]+$";

    //数字，字母和"-"组合表达式
    public final static String REGXPFORNUMORENGLISHORI = "^[a-zA-Z0-9-]+$";

    // 邮编正则表达式
    public final static String REGXPFORZIP = "^[1-9]\\d{5}$";

    // QQ正则表达式
    public final static String REGXPFORQQ = "[1-9]\\d{4,13}";

    // 整数正则表达式
    public final static String REGXPFORINTEGER = "[-\\+]?\\d+";

    // 小数正则表达式
    public final static String REGXPFORDOUBLE = "[-\\+]?\\d+(\\.\\d+)?";

    // 英文正则表达式
    public final static String REGXPFORENGLISH = "^[A-Za-z]+$";

    // 中文正则表达式
    public final static String REGXPFORCHINESE = "^[\\u4e00-\\u9fa5]+$";

    //是否是图片属性单位
    public final static String REGXPFORIMAGENUM = "^[0-9]+(em|px|%)$";

    // 登录名正则表达式,帐号(字母开头，允许5-16字节，允许字母数字下划线)
    public final static String REGXPFORUSERNAME = "^[a-zA-Z][a-zA-Z0-9_]{4,15}$";

    //验证是否hi斜杠
    public final static String REGXPFORPATH = "^[\\/\\s]*$";

    // IP正则表达式
    public final static String REGXPFORIP = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
    //正则表达式空字符串
    public final static String REGXPF_EMPTY_STRING = "^$";

    public static void main(String[] args) {
        System.out.println("邮箱：" + isEmail("liuhai_cdtc@tlearning.cn"));
        System.out.println("电话：" + isPhone("025-86684518"));
        System.out.println("手机：" + isMobile("15176668793"));
        System.out.println("网址：" + isUrl("https://www.baidu.com"));
        System.out.println("身份证：" + isIdCard("320124198311092238"));
        System.out.println("货币：" + isCurrenCy("23.152"));
        System.out.println("纯数字：" + isNumber("23"));
        System.out.println("邮编：" + isZip("210018"));
        System.out.println("QQ：" + isQq("497014281"));
        System.out.println("整数：" + isInteger("-123"));
        System.out.println("小数：" + isDouble("523"));
        System.out.println("英文：" + isEnglish("liuhai"));
        System.out.println("中文：" + isChinese("刘海"));
        System.out.println("登录名：" + isUserName("liuhai"));
        System.out.println("IP：" + isIp("202.112.235.235"));

    }

    /**
     * 判断是否合法邮箱.
     *
     * @param strValue 邮箱
     * @return true/false
     */
    public static boolean isEmail(final String strValue) {
        if (StringUtils.isBlank(strValue)) {
            return false;
        }
        //不区分大小写
        Pattern pattern = Pattern.compile(REGXPFOREMAIL, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(strValue);

        /**
         * 注意find与matches()的区别
         * 调用find方法，只执行尽量匹配
         * 而调用matches方法，则执行严格的匹配
         */
        return matcher.matches();
    }

    /**
     * 是否是数字加字母的形式
     *
     * @param strValue
     * @return
     */
    public static boolean isNumOrEnglish(final String strValue) {
        if (StringUtils.isBlank(strValue)) {
            return false;
        }
        Pattern pattern = Pattern.compile(REGXPFORNUMBER, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(strValue);
        if (matcher.matches()) {
            return false;
        } else {
            pattern = Pattern.compile(REGXPFORNUMORENGLISH, Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(strValue);
        }
        return matcher.matches();
    }

    /**
     * 是否是数字加字母加-的形式
     */
    public static boolean isNumOrEnglishOrI(final String strValue){
        if (StringUtils.isBlank(strValue)) {
            return false;
        }
        Pattern pattern = Pattern.compile(REGXPFORNUMBER, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(strValue);
        if (matcher.matches()) {
            return false;
        } else {
            pattern = Pattern.compile(REGXPFORNUMORENGLISHORI, Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(strValue);
        }
        return matcher.matches();
    }

    /**
     * 判断电话是否合法.
     *
     * @param strValue 电话
     * @return true/false
     */
    public static boolean isPhone(final String strValue) {
        if (StringUtils.isBlank(strValue)) {
            return false;
        }
        Pattern pattern = Pattern.compile(REGXPFORPHONE, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(strValue);

        return matcher.matches();
    }

    /**
     * 判断手机号是否合法.
     *
     * @param strValue 手机号
     * @return true/false
     */
    public static boolean isMobile(final String strValue) {
        if (StringUtils.isBlank(strValue)) {
            return false;
        }
        Pattern pattern = Pattern.compile(REGXPFORMOBILE, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(strValue);

        return matcher.matches();
    }

    /**
     * 判断网址是否合法.
     *
     * @param strValue 网址
     * @return true/false
     */
    public static boolean isUrl(final String strValue) {
        if (StringUtils.isBlank(strValue)) {
            return false;
        }
        Pattern pattern = Pattern.compile(REGXPFORURL, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(strValue);

        return matcher.matches();
    }

    /**
     * 判断身份证号是否合法.
     *
     * @param strValue 身份证号
     * @return true/false
     */
    public static boolean isIdCard(final String strValue) {
        if (StringUtils.isBlank(strValue)) {
            return false;
        }
        Pattern pattern = Pattern.compile(REGXPFORIDCARD, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(strValue);

        return matcher.matches();
    }

    public static boolean isImageSize(final String strValue) {
        if (StringUtils.isBlank(strValue)) {
            return false;
        }
        Pattern pattern = Pattern.compile(REGXPFORIMAGESIZE, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(strValue);

        return matcher.matches();
    }

    /**
     * 判断货币值是否合法.
     *
     * @param strValue 货币
     * @return true/false
     */
    public static boolean isCurrenCy(final String strValue) {
        if (StringUtils.isBlank(strValue)) {
            return false;
        }
        Pattern pattern = Pattern.compile(REGXPFORCURRENCY, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(strValue);

        return matcher.matches();
    }

    /**
     * 判断是否纯数字.
     *
     * @param strValue 值
     * @return true/false
     */
    public static boolean isNumber(final String strValue) {
        if (StringUtils.isBlank(strValue)) {
            return false;
        }
        Pattern pattern = Pattern.compile(REGXPFORNUMBER, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(strValue);

        return matcher.matches();
    }

    /**
     * 判断是否纯数字.
     *
     * @param strValue 值
     * @return true/false
     */
    public static boolean isDateNumber(final String strValue) {
        if (StringUtils.isBlank(strValue)) {
            return false;
        }
        Pattern pattern = Pattern.compile(REGXPFORNUMBER, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(strValue);

        return matcher.matches();
    }

    /**
     * 判断邮编号是否合法.
     *
     * @param strValue 邮编号
     * @return true/false
     */
    public static boolean isZip(final String strValue) {
        if (StringUtils.isBlank(strValue)) {
            return false;
        }
        Pattern pattern = Pattern.compile(REGXPFORZIP, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(strValue);

        return matcher.matches();
    }

    /**
     * 判断QQ号是否合法.
     *
     * @param strValue qq号
     * @return true/false
     */
    public static boolean isQq(final String strValue) {
        if (StringUtils.isBlank(strValue)) {
            return false;
        }
        Pattern pattern = Pattern.compile(REGXPFORQQ, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(strValue);

        return matcher.matches();
    }

    /**
     * 判断是否整数.
     *
     * @param strValue 整数值
     * @return true/false
     */
    public static boolean isInteger(final String strValue) {
        if (StringUtils.isBlank(strValue)) {
            return false;
        }
        Pattern pattern = Pattern.compile(REGXPFORINTEGER, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(strValue);

        return matcher.matches();
    }

    /**
     * 判断是否小数.
     *
     * @param strValue 小数值
     * @return true/false
     */
    public static boolean isDouble(final String strValue) {
        if (StringUtils.isBlank(strValue)) {
            return false;
        }
        Pattern pattern = Pattern.compile(REGXPFORDOUBLE, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(strValue);

        return matcher.matches();
    }

    /**
     * 判断是否英文.
     *
     * @param strValue 值
     * @return true/false
     */
    public static boolean isEnglish(final String strValue) {
        Pattern pattern = Pattern.compile(REGXPFORENGLISH, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(strValue);

        return StringUtils.isNotBlank(strValue) && matcher.matches();
    }

    /**
     * 判断是否中文.
     *
     * @param strValue 值
     * @return true/false
     */
    public static boolean isChinese(final String strValue) {
        if (StringUtils.isBlank(strValue)) {
            return false;
        }
        Pattern pattern = Pattern.compile(REGXPFORCHINESE, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(strValue);

        return matcher.matches();
    }

    /**
     * 判断是否合法登录名.
     *
     * @param strValue 登录名
     * @return true/false
     */
    public static boolean isUserName(final String strValue) {
        if (StringUtils.isBlank(strValue)) {
            return false;
        }
        Pattern pattern = Pattern.compile(REGXPFORUSERNAME, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(strValue);

        return matcher.matches();
    }

    /**
     * 判断是否合法IP.
     *
     * @param strValue IP值
     * @return true/false
     */
    public static boolean isIp(final String strValue) {
        if (StringUtils.isBlank(strValue)) {
            return false;
        }
        Pattern pattern = Pattern.compile(REGXPFORIP, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(strValue);

        return matcher.matches();
    }

    public static boolean isNumeric(String strValue) {
        if (StringUtils.isBlank(strValue)) {
            return false;
        }
        Pattern pattern = Pattern.compile(REGXPFORNUMBEREXT);
        return pattern.matcher(strValue).matches();
    }

    public static boolean isArrayNotEmpty(Collection array) {
        return !isArrayEmpty(array);
    }

    public static boolean isArrayEmpty(Collection array) {
        return array == null || array.isEmpty();
    }

    public static boolean isEmpty(Integer id) {
        return id == null || id == 0;
    }

    public static boolean isNotEmpty(Integer id) {
        return !isEmpty(id);
    }

    /**
     * 验证bean的合法性
     *
     * @param bean
     * @return
     */
    public static List<String> checkBean(Object bean) {
        if (bean == null) {
            throw new NullPointerException();
        }
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = vf.getValidator();
        Set<ConstraintViolation<Object>> set = validator.validate(bean);
        List<String> statusList = new ArrayList<String>(set.size());
        for (ConstraintViolation constraintViolation : set) {
            statusList.add(constraintViolation.getMessage());
        }
        return statusList.isEmpty()
                ? Collections.singletonList(BaseConstant.SUCCESS_STRING)
                : statusList;
    }
    public static boolean checkBean(Object bean, Map<String, String> i18, Operate<List<String>>... op) {
        List<String> status = checkBean(bean);
        if (status.contains(BaseConstant.SUCCESS_STRING) || i18 == null || i18.isEmpty()) {
            return true;
        }
        if (op == null || op.length == 0) {
            return false;
        }
        List<String> result = new ArrayList<String>();
        for (String st : status) {
            String ss = i18.get(st);
            if (StringUtils.isNotBlank(ss)) {
                result.add(ss);
            }
        }
        if (result.isEmpty()) {
            return true;
        }
        op[0].operate(result);
        return false;
    }

    public static <T, E> boolean contains(Collection<T> collections, E obj, Compare<T, E> compare) {
        if (isArrayEmpty(collections)) {
            return false;
        }
        for (T _obj : collections) {
            if (compare.compare(_obj, obj)) {
                return true;
            }
        }
        return false;
    }

    public static <T, E> int indexOf(List<T> collections, E obj, Compare<T, E> compare) {
        if (isArrayEmpty(collections)) {
            return -1;
        }
        for (int i = 0, len = collections.size(); i < len; i++) {
            if (compare.compare(collections.get(i), obj)) {
                return i;
            }
        }
        return -1;
    }

    public static boolean bigDecimalSize(BigDecimal bigDecimal, int en, boolean ignalSig) {
        boolean sig = bigDecimal.signum() == -1;
        int size = new BigDecimal(bigDecimal.intValue()).setScale(0, BigDecimal.ROUND_DOWN).toString().length();
        if (ignalSig && sig) {
            size -= 1;
        }
        return size <= en;
    }

    public static boolean isClassProperty(Class t, String property){
        if(StringUtils.isBlank(property)){
            return false;
        }
        Set<String> ss = ConvertUtils.getFiledName(t);
        String _ss = property.trim();
        return ss.contains(_ss);
    }
}