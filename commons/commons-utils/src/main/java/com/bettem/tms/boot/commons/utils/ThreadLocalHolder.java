package com.bettem.tms.boot.commons.utils;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 线程存储容器
 * @author GaoFans
 */
public class ThreadLocalHolder {

    private final ThreadLocal<ConcurrentHashMap<String,Object>> data;
    private final ReentrantReadWriteLock.ReadLock readLock;
    private final ReentrantReadWriteLock.WriteLock writeLock;
    private enum Instance{
        /**
         * 实例
         */
        INSTANCE;

        private ThreadLocalHolder instance;
        Instance() {
            instance = new ThreadLocalHolder();
        }

        public ThreadLocalHolder getInstance() {
            return instance;
        }
    }

    public static ThreadLocalHolder getInstance(){
        return Instance.INSTANCE.getInstance();
    }

    private ThreadLocalHolder() {
        this.data = new ThreadLocal<>();
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        this.readLock = readWriteLock.readLock();
        this.writeLock = readWriteLock.writeLock();
    }

    public <T> T get(String key, Class<T> clazz){
        Objects.requireNonNull(key,"key");
        readLock.lock();
        try {
            return Optional.ofNullable(this.data.get()).map(map -> (T) map.get(key)).orElse(null);
        }finally {
            readLock.unlock();
        }
    };

    public Object get(String key){
        Objects.requireNonNull(key,"key");
        readLock.lock();
        try {
            return Optional.ofNullable(this.data.get()).map(map -> map.get(key)).orElse(null);
        }finally {
            readLock.unlock();
        }
    }

    public void put(String key,Object value){
        Objects.requireNonNull(key,"key");
        Objects.requireNonNull(value,"value");
        writeLock.lock();
        try {
            ConcurrentHashMap<String, Object> map = data.get();
            if(map == null){
                map = new ConcurrentHashMap<>();
                data.set(map);
            }
            map.put(key,value);
        }finally {
            writeLock.unlock();
        }
    }
}
