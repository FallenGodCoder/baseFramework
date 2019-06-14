package com.tc.common.utils;

/**
 * Created with IntelliJ IDEA.
 * User: New
 * Date: 13-5-20
 * Time: 下午1:36
 * To change this template use File | Settings | File Templates.
 */

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * get words' pinyin.
 * <p/>
 * Author: nzhou (nzhou@wiscom.com.cn) Date: Aug 24, 2009 Version: V1.0 History:
 */
public class PinyinUtils {

    public static String getPinyin(String src) {
        return generate(getPinyinSet(src, null, false, false));
    }

    public static String getSinglePinyin(String src) {
        return generate(getPinyinSet(src, null, false, true));
    }

    public static String getPinyin(String src, String separator) {
        return generate(getPinyinSet(src, separator, false, false));
    }

    public static String getSinglePinyin(String src, String separator) {
        return generate(getPinyinSet(src, separator, false, true));
    }

    public static String getShortPinyin(String src) {
        return generate(getPinyinSet(src, null, true, false));
    }

    public static String getSingleShortPinyin(String src) {
        if (StringUtils.isBlank(src)) {
            return src;
        }
        return generate(getPinyinSet(src, null, true, true));
    }

    /**
     * 字符串集合转换字符串(逗号分隔)
     *
     * @param stringSet
     * @param stringSet TODO
     * @return
     */
    private static String generate(List<StringBuffer> stringSet) {
        if (stringSet == null || stringSet.isEmpty()) {
            return StringUtils.EMPTY;
        }
        return StringUtils.join(stringSet, ',');
    }

    /**
     * 获取拼音集合
     *
     * @param src
     * @return Set
     */
    private static List<StringBuffer> getPinyinSet(String src, String separator, boolean getFristLetter, boolean isSingle) {

        if (src != null && !src.trim().equalsIgnoreCase("")) {
            char[] srcChar;
            srcChar = src.toCharArray();
            // 汉语拼音格式输出类
            HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();

            // 输出设置，大小写，音标方式等
            hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

            String[][] temp = new String[src.length()][];
            for (int i = 0; i < srcChar.length; i++) {
                char c = srcChar[i];
                // 是中文或者a-z或者A-Z转换拼音(我的需求，是保留中文或者a-z或者A-Z)
                if (String.valueOf(c).matches("[\\u4E00-\\u9FA5]+")) {
                    try {

                        String[] parray = PinyinHelper.toHanyuPinyinStringArray(srcChar[i], hanYuPinOutputFormat);

                        //如果不用多音字
                        if (isSingle && parray.length > 0) {
                            parray = new String[]{parray[0]};
                        } else {
                            //去重
                            parray = removeRepeat(parray);
                        }
                        if (getFristLetter) {
                            temp[i] = getFristLetter(parray);
                        } else {
                            temp[i] = parray;
                        }
                    } catch (BadHanyuPinyinOutputFormatCombination e) {
                        e.printStackTrace();
                    }
                } else {
                    temp[i] = new String[]{String.valueOf(srcChar[i])};
                }
            }

            int position = 0;
            List<StringBuffer> resultList = new ArrayList<StringBuffer>();
            while (position < temp.length) {
                position = remakeResultList(temp, position, resultList, separator);
            }

            return resultList;
        }
        return null;
    }


    private static int remakeResultList(String[][] temp, int position, List<StringBuffer> resultList, String separator) {
        if (position == 0) {
            for (String py : temp[0]) {
                resultList.add(new StringBuffer(py));
            }
        } else {
            int oldSize = resultList.size();
            for (int i = 0; i < oldSize; i++) {
                StringBuffer oldPy = resultList.get(0);
                String[] pyList2 = temp[position];
                for (int j = 0, jsize = pyList2.length; j < jsize; j++) {
                    StringBuffer coppiedBuffer = new StringBuffer(oldPy);
                    if (separator == null) {
                        resultList.add(coppiedBuffer.append(pyList2[j]));
                    } else {
                        resultList.add(coppiedBuffer.append(separator).append(pyList2[j]));
                    }
                }
                resultList.remove(0);
            }
        }
        return position + 1;
    }

    private static String[] removeRepeat(String[] parray) {
        Set<String> set = new HashSet<String>();
        for (String py : parray) {
            if (!set.contains(py)) {
                set.add(py);
            }
        }
        return set.toArray(new String[set.size()]);
    }

    private static String[] getFristLetter(String[] wholePinyin) {
        for (int i = 0; i < wholePinyin.length; i++) {
            wholePinyin[i] = ((String) wholePinyin[i]).substring(0, 1);
        }
        return removeRepeat(wholePinyin);
    }
}