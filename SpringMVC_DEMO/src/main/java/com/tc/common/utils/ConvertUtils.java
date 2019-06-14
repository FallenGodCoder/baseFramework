package com.tc.common.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConvertUtils {

    private static Logger logger = LoggerFactory.getLogger(ConvertUtils.class);

    /**
     * 获取list对象的某个属性或对对象进行处理构成新的集合
     *
     * @param source    zy
     * @param attribute at
     * @return e
     */
    public static <E, T> List<T> loadListValue(Collection<E> source, Attribute<T, E> attribute) {
        if (ValidatorUtils.isArrayEmpty(source)) {
            return Collections.emptyList();
        }
        List<T> data = new ArrayList<T>(source.size());
        for (E object : source) {
            T e = attribute.attribute(object);
            if (e != null) {
                data.add(e);
            }
        }
        return data;
    }

    /**
     * 获取list对象的某个属性或对对象进行处理构成新的集合
     *
     * @param source    zy
     * @param attribute at
     * @return e
     */
    public static <E, T> Set<T> loadSetValue(Collection<E> source, Attribute<T, E> attribute) {
        if (!ValidatorUtils.isArrayNotEmpty(source)) {
            return new HashSet<T>();
        }
        Set<T> data = new HashSet<T>(source.size());
        for (E object : source) {
            T e = attribute.attribute(object);
            if (e != null) {
                data.add(e);
            }
        }
        return data;
    }

    /**
     * 分割字符串生成list
     *
     * @param ids 字符串
     * @param spr 分割符号
     * @return 资源
     */
    public static List<String> loadStringList(String ids, char spr) {
        if (StringUtils.isBlank(ids)) {
            return new LinkedList<String>();
        }
        String[] idArray = StringUtils.split(ids, spr);
        return arrayToList(idArray);
    }

    /**
     * 分割字符串生成list
     *
     * @param ids 字符串
     * @param spr 分割符号
     * @return 资源
     */
    public static List<String> loadStringList(String ids, String spr) {
        if (StringUtils.isBlank(ids)) {
            return new LinkedList<String>();
        }
        String[] idArray = StringUtils.split(ids, spr);
        return arrayToList(idArray);
    }

    /**
     * @param array 数组
     * @return 集合
     */
    private static List<String> arrayToList(String[] array) {
        return Arrays.asList(array);
    }


    public static List<Integer> loadIntegerList(String ids, char spr) {
        if (StringUtils.isBlank(ids)) {
            return new ArrayList<Integer>();
        }
        String[] idArray = StringUtils.split(ids, spr);
        List<Integer> integerIds = new ArrayList<Integer>(idArray.length);
        for (String id : idArray) {
            integerIds.add(Integer.parseInt(id));
        }
        return integerIds;
    }

    public static Set<String> loadStringSet(String ids, char spr) {
        if (StringUtils.isBlank(ids)) {
            return new HashSet<String>();
        }
        return new HashSet<String>(loadStringList(ids, spr));
    }

    public static Set<Integer> loadIntegerSet(String ids, char spr) {
        if (StringUtils.isBlank(ids)) {
            return new HashSet<Integer>();
        }
        return new HashSet<Integer>(loadIntegerList(ids, spr));
    }

    /**
     * 获取like的字符串
     *
     * @param value        lyz
     * @param defaultValue morenzhi
     * @return %lyz%
     */
    public static String makeLikeString(String value, String defaultValue) {
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return "%" + value + "%";
    }

    /**
     * 获取pinyin分割的字符串
     *
     * @param value        lyz
     * @param defaultValue 默认值
     * @return l%y%z%
     */
    public static String makeLikeNameString(String value, String defaultValue) {
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        StringBuilder stringBuilder = new StringBuilder(200);
        for (int i = 0; i < value.length(); i++) {
            stringBuilder.append(value.charAt(i)).append("%");
        }
        return stringBuilder.toString();
    }

    /**
     * 分割字符串并组装成对象
     *
     * @param list      结合
     * @param setMapper 集合
     * @return jihe
     */
    public static <T> Set<T> setIdList(Collection<String> list, SetMapper<T, String> setMapper) {
        if (list.isEmpty()) {
            return Collections.emptySet();
        }
        Set<T> set = new HashSet<T>(list.size());
        for (String id : list) {
            set.add(setMapper.setValue(id));
        }
        return set;
    }

    /**
     * 将集合拆分并按字符生成字符串
     *
     * @param set 集合
     * @param spr 分隔符
     * @return 字符串你
     */
    public static String toString(Set<String> set, char spr) {
        if (!ValidatorUtils.isArrayNotEmpty(set)) {
            return StringUtils.EMPTY;
        }
        StringBuilder stringBuilder = new StringBuilder(20 * set.size() + 10);
        for (String _str : set) {
            stringBuilder.append(_str).append(spr);
        }
        int length = stringBuilder.length();
        stringBuilder.delete(length - 1, length);
        return stringBuilder.toString();
    }

    public static <T> Set<T> setIdList(String ids, SetMapper<T, String> setMapper) {
        Set<String> set = loadStringSet(ids, ',');
        if (set.isEmpty()) {
            return Collections.emptySet();
        }
        return setIdList(set, setMapper);
    }

    public static <T, E> Set<T> setValue(Collection<E> a, SetMapper<T, E> setMapper) {
        Set<T> set = new HashSet<T>();
        for (E obj : a) {
            T t = setMapper.setValue(obj);
            if (t != null) {
                set.add(t);
            }
        }
        return set;
    }

    public static <T> T loadData(Collection<T> a, Attribute<Boolean, T> attribute) {
        if (ValidatorUtils.isArrayEmpty(a)) {
            return null;
        }
        for (T t : a) {
            if (attribute.attribute(t)) {
                return t;
            }
        }
        return null;
    }

    /**
     * 根据条件删除集合中的一个对象
     */
    public static <T> Set<T> removeSetNode(final Set<T> set, Attribute<Boolean, T> attribute) {
        if (!ValidatorUtils.isArrayNotEmpty(set)) {
            logger.debug("set is empty");
            return set;
        }
        Set<T> _set = new HashSet<T>(set);
        for (T object : set) {
            if (attribute.attribute(object)) {
                _set.remove(object);
                break;
            }
        }
        return _set;
    }

    public static <T> List<T> removeListNode(final List<T> list, Attribute<Boolean, T> attribute) {
        if (ValidatorUtils.isArrayEmpty(list)) {
            logger.debug("set is empty");
            return list;
        }
        List<T> answer = new ArrayList<T>(list);
        for (T object : list) {
            if (attribute.attribute(object)) {
                answer.remove(object);
                break;
            }
        }
        return answer;
    }

    public static <T> String toSplitString(Collection<T> collection, Attribute<String, T> attribute, String split, boolean ignoreNull) {
        if (ValidatorUtils.isArrayEmpty(collection)) {
            return StringUtils.EMPTY;
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (attribute != null) {
            for (T t : collection) {
                String s = attribute.attribute(t);
                if (ignoreNull && s == null) {
                    continue;
                }
                stringBuilder.append(s).append(split);
            }
        } else {
            for (T t : collection) {
                if (ignoreNull && t == null) {
                    continue;
                }
                stringBuilder.append(t).append(split);
            }
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    /**
     * 匹配并返回所有满足正则表达式的内容
     *
     * @param input 在字符串中找到满足正则包大事的字符串
     * @param reg   正则
     * @return 集合
     */
    public static List<String> find(String input, String reg) {
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(input);
        List<String> finds = new LinkedList<String>();
        while (matcher.find()) {
            finds.add(matcher.group());
        }
        return finds;
    }

    public static void cast(Object src, Object desc) {
        if (!src.getClass().equals(desc.getClass().getSuperclass()) || src.getClass() == Object.class) {
            return;
        }
        BeanUtils.copyProperties(src, desc);
    }

    /**
     * 替换并返回根据正则表达式替换后的内容
     */
    public static String replace(String input, String reg, ReplaceOperate replaceOperate) {
        /*Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(input);
        StringBuffer sb = new StringBuffer(4000);
        int i = 0;
        while (matcher.find()) {
            String group = matcher.group();
            matcher.appendReplacement(sb, replaceOperate.replace(i, group));
            i++;
        }
        matcher.appendTail(sb);
        return sb.toString();*/
        return replaceWithReg(input,reg,replaceOperate);
    }

    public static String replaceWithReg(String input, String reg, ReplaceOperate replaceOperate) {
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(input);
        String replacedStr = input;
        int i = 0;
        while (matcher.find()) {
            String group = matcher.group();
            replacedStr = StringUtils.replace(replacedStr, group, replaceOperate.replace(i, group));
            i++;
        }
        return replacedStr;
    }

    public static <T> Set<T> disUnion(Collection<T> a, Collection<T> b, Compare<T, T> compare) {
        Set<T> set = new HashSet<T>();
        if (ValidatorUtils.isArrayEmpty(a) && ValidatorUtils.isArrayEmpty(b)) {
            return set;
        }
        if (b.isEmpty()) {
            set.addAll(a);
        } else {
            for (T obj : a) {
                if (!ValidatorUtils.contains(b, obj, compare)) {
                    set.add(obj);
                }
            }
        }
        if (a.isEmpty()) {
            set.addAll(b);
        } else {
            for (T obj : b) {
                if (!ValidatorUtils.contains(a, obj, compare)) {
                    set.add(obj);
                }
            }
        }
        return set;
    }

    public static String[] splitBlank(String input) {
        return input.split("\\s+");
    }

    public static <E> List<E> convertTo(Collection<? extends E> collections) {
        return new ArrayList<E>(collections);
    }

    public static <E, F extends E> List<F> convertToP(Collection<E> collections) {
        List<F> ff = new ArrayList<F>();
        try {
            for (E e : collections) ff.add((F) e);
        } catch (Exception ex) {
            return Collections.emptyList();
        }
        return ff;
    }

    public static <E> List<E> convertTo(E[] array) {
        List<E> answer = new ArrayList<E>();
        if (array == null || array.length == 0) {
            return answer;
        }
        for (E e : array) {
            answer.add(e);
        }

        return answer;
    }

    public static <E> Set<E> convertToSet(Collection<? extends E> collections) {
        return new HashSet<E>(collections);
    }


    public static String toDbProperty(String property) {
        StringBuilder ss = new StringBuilder();
        int start = 0, l = property.length();
        for (int i = 0; i < l; i++) {
            char a = property.charAt(i);
            if (Character.isUpperCase(a)) {
                ss.append(property.substring(start, i)).append("_").append(Character.toLowerCase(a));
                start = i + 1;
            }
        }
        if (start < l) {
            ss.append(property.substring(start));
        }
        return ss.toString();
    }

    private static List<Class> BASE_TYPES = Arrays.<Class>asList(
            String.class, Integer.class, Long.class, Date.class, BigDecimal.class,
            Short.class, Boolean.class, Character.class
    );

    public static Set<String> getFiledName(Class t) {
        Field[] fields = t.getDeclaredFields();
        Set<String> st = new HashSet<String>();
        for (Field field : fields) {
            if (BASE_TYPES.contains(field.getType())) {
                st.add(field.getName());
            }
        }
        return st;
    }

    public static List<Integer> parseNumber(int number, int w) {
        List<Integer> size = new ArrayList<Integer>();
        size.add(number % w);
        while (true) {
            number /= w;
            if (number == 0) {
                break;
            } else {
                size.add(number % w);
            }
        }
        Collections.reverse(size);
        return size;
    }

    public static void main(String[] args) {
//        System.out.print(getFiledName(LmsOrgUser.class));
        String aaaa =replaceWithReg("{0}asdasdasd{1}asdaa", "\\{[0-9]\\}", new ReplaceOperate() {
            public String replace(int i, String pattern) {
                return "{asdwws}";
            }
        });
        System.out.print(aaaa);

//        System.out.print(disUnion(Arrays.asList("a", "b", "c"), Arrays.asList("b", "c", "d"), new Compare<String, String>() {
//            public boolean compare(String s, String s2) {
//                return s.equals(s2);
//            }
//        }));
    }


}
