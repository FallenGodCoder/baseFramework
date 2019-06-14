package com.tc.common.lang;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 可以在当前线程中存储的HashMap,
 *  多个线程之间互不干扰
 *
 * Created by tangcheng on 2017/11/20.
 */
public class ThreadLocalHashMap<K, V> extends ThreadLocal implements Map<K, V> {
    public final static ThreadLocal<Map> tl = new ThreadLocal<Map>();

    private Map<K, V> getInhertMap(){
        if(null == tl.get()){
            tl.set(new HashMap<K, V>());
        }
        return tl.get();
    }


    @Override
    public int size() {
        return getInhertMap().size();
    }

    @Override
    public boolean isEmpty() {
        return getInhertMap().isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return getInhertMap().containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return getInhertMap().containsValue(value);
    }

    @Override
    public V get(Object key) {
        return getInhertMap().get(key);
    }

    @Override
    public V put(K key, V value) {
        return getInhertMap().put(key, value);
    }

    @Override
    public V remove(Object key) {
        return getInhertMap().remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        getInhertMap().putAll(m);
    }

    @Override
    public void clear() {
        getInhertMap().clear();
    }

    @Override
    public Set<K> keySet() {
        return getInhertMap().keySet();
    }

    @Override
    public Collection<V> values() {
        return getInhertMap().values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return getInhertMap().entrySet();
    }


    public static void main(String[] args) {
        final ThreadLocalHashMap<String, String> map = new ThreadLocalHashMap<String, String>();
        map.put("0","0");
        System.out.println("0:"+ map.get("0"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                map.put("1","1");
                System.out.println("1:"+ map.get("1"));
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                map.put("2","2");
                System.out.println("2:"+ map.get("2"));
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                map.put("3","3");
                System.out.println("3:"+ map.get("3"));
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                map.put("4","4");
                System.out.println("4:"+ map.get("4"));
                System.out.println("F0:"+ map.get("0"));
            }
        }).start();

        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("F4:"+ map.get("4"));
        System.out.println(map);
    }
}
