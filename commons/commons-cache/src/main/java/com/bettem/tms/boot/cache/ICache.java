package com.bettem.tms.boot.cache;

import java.util.List;
import java.util.Map;

/**
 * 缓存接口
 * @author GaoFans
 */
public interface ICache<K,Hk,V> {

    /**
     * 放入普通对象
     * @param key
     * @param value
     */
    void put(K key, V value);

    /**
     * 获取一个对象
     * @param key
     * @return
     */
    V get(K key);

    /**
     * 放入map
     * @param key
     * @param map
     */
    void putHash(K key, Map<Hk, V> map);

    /**
     * 设置map的值
     * @param key
     * @param hashKey
     * @param value
     */
    void putHash(K key, Hk hashKey, V value);

    /**
     * 获取map的值
     * @param key
     * @param hashHey
     * @return
     */
    V getHash(K key, Hk hashHey);

    /**
     * 获取map
     * @param key
     * @return
     */
    Map<Hk,V> getHash(K key);

    /**
     * 放入list
     * @param key
     * @param list
     */
    void putList(K key, List<V> list);

    /**
     * 在list放入一个值
     * @param key
     * @param v
     */
    void putList(K key, V v);

    /**
     * 获取下标为index的某个值
     * @param k
     * @param index
     * @return
     */
    V getList(K k, int index);

    /**
     * 获取一个list
     * @param key
     * @return
     */
    List<V> getList(K key);

    /**
     * 删除
     * @param key
     */
    void remove(K key);

    /**
     * 删除多个
     * @param key
     */
    void remove(K... key);

    /**
     * 全部删除
     */
    void removeAll();

    /**
     * 删除map某个值
     * @param key
     * @param hashKey
     */
    void removeHash(K key, Hk hashKey);

    /**
     * 删除map多个值
     * @param key
     * @param hashKey
     */
    void removeHash(K key, Hk... hashKey);

    /**
     * 删除list某个值
     * @param key
     * @param index
     */
    void removeList(K key, int index);

    /**
     * 删除list多个值
     * @param key
     * @param index
     */
    void removeList(K key, int... index);
}
