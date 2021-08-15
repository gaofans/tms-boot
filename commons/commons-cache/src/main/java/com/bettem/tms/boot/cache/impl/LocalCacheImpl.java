package com.bettem.tms.boot.cache.impl;

import com.bettem.tms.boot.cache.ICache;
import com.github.benmanes.caffeine.cache.Cache;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 本地缓存使用caffeine实现
 * @author GaoFans
 */
@SuppressWarnings({"unchecked","rowtypes"})
public class LocalCacheImpl<K,Hk,V> implements ICache<K,Hk,V> {

    private Cache<Object,Object> cache;

    private ReentrantReadWriteLock.ReadLock rLock;

    private ReentrantReadWriteLock.WriteLock wLock;

    public LocalCacheImpl(Cache<Object, Object> cache) {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
        rLock = lock.readLock();
        wLock = lock.writeLock();
        this.cache = cache;
    }

    @Override
    public void put(K key, V value) {
        wLock.lock();
        try {
            cache.put(key,value);
        }finally {
            wLock.unlock();
        }
    }

    @Override
    public V get(K key) {
        rLock.lock();
        try {
            return (V) cache.getIfPresent(key);
        }finally {
            rLock.unlock();
        }
    }

    @Override
    public void putHash(K key, Map<Hk, V> map) {
        if(map != null && map.size() > 0){
            wLock.lock();
            try {
                Map<Hk, V> hashMap = (Map<Hk, V>) cache.get(key, o -> new HashMap<Hk, V>(2));
                hashMap.putAll(map);
                cache.put(key, hashMap);
            }finally {
                wLock.unlock();
            }
        }
    }

    @Override
    public void putHash(K key, Hk hashKey, V value) {
        if(key != null
                && hashKey != null
                && value != null){
            wLock.lock();
            try {
                Map<Hk, V> hashMap = (Map<Hk, V>) cache.get(key, o -> new HashMap<Hk, V>(2));
                hashMap.put(hashKey,value);
            }finally {
                wLock.unlock();
            }
        }

    }

    @Override
    public V getHash(K key, Hk hashHey) {
        if(key != null && hashHey != null){
            rLock.lock();
            try {
                Map<Hk, V> hashMap = (Map<Hk, V>) cache.get(key, o -> new HashMap<Hk, V>(2));
                return hashMap.get(hashHey);
            }finally {
                rLock.unlock();
            }
        }
        return null;
    }

    @Override
    public Map<Hk, V> getHash(K key) {
        if(key != null){
            rLock.lock();
            try {
                Map<Hk, V> hashMap = (Map<Hk, V>) cache.get(key, o -> new HashMap<Hk, V>(2));
                return Collections.unmodifiableMap(hashMap);
            }finally {
                rLock.unlock();
            }
        }
        return null;
    }

    @Override
    public void putList(K key, List<V> list) {
        if(list.size() > 0){
            wLock.lock();
            try {
                List<V> arrayList = (List<V>) cache.get(key, o -> new ArrayList<V>());
                arrayList.addAll(list);
                cache.put(key,arrayList);
            }finally {
                wLock.unlock();
            }
        }
    }

    @Override
    public void putList(K key, V v) {
        wLock.lock();
        try {
            List<V> list = (List<V>) cache.get(key, o -> new ArrayList<V>());
            list.add(v);
        }finally {
            wLock.unlock();
        }
    }

    @Override
    public V getList(K key, int index) {
        rLock.lock();
        try {
            List<V> list = (List<V>) cache.get(key, o -> new ArrayList<V>());
            return list.get(index);
        }finally {
            rLock.unlock();
        }
    }

    @Override
    public List<V> getList(K key) {
        rLock.lock();
        try {
            List<V> list = (List<V>) cache.get(key, o -> new ArrayList<V>());
            return Collections.unmodifiableList(list);
        }finally {
            rLock.unlock();
        }
    }

    @Override
    public void remove(K key) {
        wLock.lock();
        try {
            cache.invalidate(key);
        }finally {
            wLock.unlock();
        }
    }

    @Override
    public void remove(K... key) {
        wLock.lock();
        try {
            cache.invalidateAll(Arrays.asList(key));
        }finally {
            wLock.unlock();
        }
    }

    @Override
    public void removeAll() {
        wLock.lock();
        try {
            cache.invalidateAll();
        }finally {
            wLock.unlock();
        }
    }

    @Override
    public void removeHash(K key, Hk hashKey) {
        wLock.lock();
        try {
            Map<Hk, V> hashMap = (Map<Hk, V>) cache.get(key, o -> new HashMap<Hk, V>(2));
            hashMap.remove(hashKey);
        }finally {
            wLock.unlock();
        }
    }

    @Override
    public void removeHash(K key, Hk... hashKey) {
        wLock.lock();
        try {
            Map<Hk, V> hashMap = (Map<Hk, V>) cache.get(key, o -> new HashMap<Hk, V>(2));
            for (int i = 0; i < hashKey.length; i++) {
                hashMap.remove(hashKey[i]);
            }
        }finally {
            wLock.unlock();
        }
    }

    @Override
    public void removeList(K key, int index) {
        wLock.lock();
        try {
            List<V> list = (List<V>) cache.get(key, o -> new ArrayList<V>());
            list.remove(index);
        }finally {
            wLock.unlock();
        }
    }

    @Override
    public void removeList(K key, int... index) {
        wLock.lock();
        try {
            List<V> list = (List<V>) cache.get(key, o -> new ArrayList<V>());
            for (int i = 0; i < index.length; i++) {
                list.remove(index[i]);
            }
        }finally {
            wLock.unlock();
        }
    }

}
