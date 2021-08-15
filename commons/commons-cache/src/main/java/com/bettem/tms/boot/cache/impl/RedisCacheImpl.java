package com.bettem.tms.boot.cache.impl;

import com.bettem.tms.boot.cache.ICache;
import com.bettem.tms.boot.config.CacheProperties;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 使用redis实现缓存
 * @author GaoFans
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class RedisCacheImpl<K,Hk,V> implements ICache<K,Hk,V> {

    private RedisTemplate<Object,Object> redisTemplate;

    private CacheProperties cacheProperties;

    /**
     * hash类型的占位符，防止hash字段清空后被redis删除
     */
    private Hk hashHolder;

    /**
     * list类型的占位符，防止list清空后被redis删除
     */
    private V listHolder;


    public RedisCacheImpl(RedisTemplate<Object, Object> redisTemplate, CacheProperties cacheProperties) {
        this.redisTemplate = redisTemplate;
        this.cacheProperties = cacheProperties;
    }

    public RedisCacheImpl(RedisTemplate<Object, Object> redisTemplate, CacheProperties cacheProperties, Hk hashHolder, V listHolder) {
        this.redisTemplate = redisTemplate;
        this.cacheProperties = cacheProperties;
        this.hashHolder = hashHolder;
        this.listHolder = listHolder;
    }

    @Override
    public void put(K key, V value) {
        if(value != null){
            redisTemplate.execute(new SessionCallback() {
                @Override
                public Object execute(RedisOperations redisOperations) throws DataAccessException {
                    //redis乐观锁，观察这个key，出现变化的话修改不会生效
                    redisOperations.watch(key);
                    Long expire = redisOperations.getExpire(key,TimeUnit.MILLISECONDS);
                    redisOperations.multi();
                    //如果还有剩余的过期时间并且写操作不需要延期，就把剩余的过期时间附上去
                    if(expire > 0 && !writeDelay()){
                        redisOperations.opsForValue().set(key, value, Math.min(expire,cacheProperties.getTimeout() * 1000), TimeUnit.MILLISECONDS);
                        //反之直接赋值
                    }else {
                        redisOperations.opsForValue().set(key, value, cacheProperties.getTimeout(), TimeUnit.SECONDS);
                    }
                    return redisOperations.exec();
                }
            });
        }
    }

    @Override
    public V get(K key) {
        if(readDelay()){
            //每次读取都会续期
            redisTemplate.expire(key, cacheProperties.getTimeout(), TimeUnit.SECONDS);
        }
        Object o = redisTemplate.opsForValue().get(key);
        return o == null ? null : (V) o;
    }

    @Override
    public void putHash(K key, Map<Hk, V> map) {
        if(map != null && map.size() > 0){

            //赋值并设置过期时间
            SessionCallback sessionCallback = new SessionCallback() {
                @Override
                public Object execute(RedisOperations redisOperations) throws DataAccessException {
                    redisOperations.watch(key);
                    Boolean hasKey = redisOperations.hasKey(key);
                    redisOperations.multi();
                    redisOperations.opsForHash().putAll(key, map);
                    //每次写都续期 新创建key时候续期
                    if(writeDelay() || !hasKey){
                        redisOperations.expire(key, cacheProperties.getTimeout(), TimeUnit.SECONDS);
                    }
                    return redisOperations.exec();
                }
            };
            redisTemplate.execute(sessionCallback);
        }
    }

    @Override
    public void putHash(K key, Hk hashKey, V value) {
        if(hashKey != null && value != null){
            //赋值并设置过期时间
            SessionCallback sessionCallback = new SessionCallback() {
                @Override
                public Object execute(RedisOperations redisOperations) throws DataAccessException {
                    redisOperations.watch(key);
                    Boolean hasKey = redisOperations.hasKey(key);
                    redisOperations.multi();
                    redisOperations.opsForHash().put(key,hashKey,value);
                    //策略为w、rw或是key不存在时设置超时时间
                    if(writeDelay() || !hasKey){
                        redisOperations.expire(key, cacheProperties.getTimeout(), TimeUnit.SECONDS);
                        //只有新创建key时候续期
                    }
                    return redisOperations.exec();
                }
            };
            redisTemplate.execute(sessionCallback);
        }

    }

    @Override
    public V getHash(K key, Hk hashHey) {
        //如果策略为r、rw就先更新一下过期时间
        if(readDelay()){
            redisTemplate.expire(key, cacheProperties.getTimeout(), TimeUnit.SECONDS);
        }
        Object o = redisTemplate.opsForHash().get(key, hashHey);
        return o == null? null : (V) o;
    }

    @Override
    public Map<Hk, V> getHash(K key) {
        if(readDelay()){
            redisTemplate.expire(key, cacheProperties.getTimeout(), TimeUnit.SECONDS);
        }
        Object o = redisTemplate.opsForHash().entries(key);
        Map<Hk, V> map = (Map<Hk, V>) o;
        map.remove(hashHolder);
        return map;
    }

    @Override
    public void putList(K key, List<V> list) {
        if(list != null && list.size() > 0){
            SessionCallback callback = new SessionCallback() {

                @Override
                public Object execute(RedisOperations redisOperations) throws DataAccessException {
                    redisOperations.watch(key);
                    Boolean hasKey = redisOperations.hasKey(key);
                    redisOperations.multi();
                    redisOperations.opsForList().rightPushAll(key,list);
                    //策略为w、rw或是key不存在时设置超时时间
                    if(writeDelay() || !hasKey){
                        redisOperations.expire(key,cacheProperties.getTimeout(),TimeUnit.SECONDS);
                    }
                    return redisOperations.exec();
                }
            };
            redisTemplate.execute(callback);
        }
    }

    @Override
    public void putList(K key, V value) {
        if(key != null && value != null){
            SessionCallback callback = new SessionCallback() {

                @Override
                public Object execute(RedisOperations redisOperations) throws DataAccessException {
                    redisOperations.watch(key);
                    Boolean hasKey = redisOperations.hasKey(key);
                    redisOperations.multi();
                    redisOperations.opsForList().rightPush(key,value);

                    //策略为w、rw或是key不存在时设置超时时间
                    if(writeDelay() || !hasKey){
                        redisOperations.expire(key,cacheProperties.getTimeout(),TimeUnit.SECONDS);
                    }

                    return redisOperations.exec();
                }
            };
            redisTemplate.execute(callback);
        }
    }

    @Override
    public V getList(K key, int index) {
        if(readDelay()){
            redisTemplate.expire(key,cacheProperties.getTimeout(),TimeUnit.SECONDS);
        }
        Object o = redisTemplate.opsForList().index(key, index);
        return o == null ? null : (V) o;
    }

    @Override
    public List<V> getList(K key) {
        if(readDelay()){
            redisTemplate.expire(key,cacheProperties.getTimeout(),TimeUnit.SECONDS);
        }
        int start = 0;
        List<Object> list = redisTemplate.opsForList().range(key, start, -1);
        if(list != null){
            return (List<V>) list;
        }
        return null;
    }

    @Override
    public void remove(K key) {
        if(key != null){
            redisTemplate.delete(key);
        }
    }

    @Override
    public void remove(K... key) {
        redisTemplate.delete(Arrays.asList(key));
    }

    @Override
    public void removeAll() {
        Set<Object> keys = redisTemplate.keys("*");
        if(keys.size() > 0){
            redisTemplate.delete(keys);
        }
    }

    @Override
    public void removeHash(K key, Hk hashKey) {
        removeRedisHash(key, hashKey);
    }

    private void removeRedisHash(K key, Hk... hashKey) {
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                redisOperations.multi();
                if(hashHolder != null && listHolder != null){
                    redisOperations.opsForHash().put(key,hashHolder,listHolder);
                }
                if(writeDelay()){
                    redisOperations.expire(key,cacheProperties.getTimeout(), TimeUnit.SECONDS);
                }
                redisOperations.opsForHash().delete(key,hashKey);
                return redisOperations.exec();
            }
        });
    }

    @Override
    public void removeHash(K key, Hk... hashKey) {
        removeRedisHash(key, hashKey);
    }

    /**
     * redis无法实现根据下标的删除
     * @param key
     * @param index
     */
    @Override
    public void removeList(K key, int index) {
        throw new RuntimeException("redis无法实现根据下标的删除");
    }

    /**
     * redis无法实现根据下标的删除
     * @param key
     * @param index
     */
    @Override
    public void removeList(K key, int... index) {
        throw new RuntimeException("redis无法实现根据下标的删除");
    }

    /**
     * 判断读操作是否要延期
     * @return
     */
    private boolean readDelay(){
        if(cacheProperties.getTimeOutPolicy() == CacheProperties.TimeOutPolicy.READ_AND_WRITE ||
                cacheProperties.getTimeOutPolicy() == CacheProperties.TimeOutPolicy.READ){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 判断写操作是否要延期
     * @return
     */
    private boolean writeDelay(){
        if(cacheProperties.getTimeOutPolicy() == CacheProperties.TimeOutPolicy.READ_AND_WRITE ||
                cacheProperties.getTimeOutPolicy() == CacheProperties.TimeOutPolicy.WRITE){
            return true;
        }else {
            return false;
        }
    }
}
