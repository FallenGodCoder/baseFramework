package com.tc.common.utils;

/**
 * Created with IntelliJ IDEA.
 * User: New
 * Date: 13-4-11
 * Time: 下午12:33
 * To change this template use File | Settings | File Templates.
 */

/**
 * 要获取属性返回值需要实现的接口
 *
 * @param <T>
 * @param <E>
 */
public interface Attribute<T, E> {

    T attribute(E t);

}
