package com.sedin.util.redis;

import java.util.*;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.sedin.util.spring.SpringContextUtil;


/**
 * Created by lh on 2016/3/28.
 */
@Component("redisUtil")
public class RedisUtil {

    private static Logger log = LoggerFactory.getLogger(RedisUtil.class);

    @Autowired
    private StringRedisTemplate stringTemplate;
    @Autowired
    private RedisTemplate redisTemplate;



    private static RedisUtil redisUtil;
	public static RedisUtil getRedisUtil() {
		if (redisUtil == null) {
		    synchronized (RedisUtil.class) {
                if (redisUtil == null) {
                    redisUtil =  (RedisUtil)SpringContextUtil.getBeanByName("redisUtil");
                }
            }
		}
		return redisUtil;
	}
    

    
    /**
     * 发布消息
     */
    public void publish(String channel,   String message) {
        redisTemplate.convertAndSend(channel, message);
    }


    public String get(final String key) {
       return stringTemplate.opsForValue().get(key);
    }

    public boolean exists(final String key) {
        return stringTemplate.hasKey(key);
    }
    public boolean hexists(final String key ,final String field) {
       return stringTemplate.opsForHash().hasKey(key , field);
    }
    public void setex(final String key, final int seconds, final String value) {
        stringTemplate.opsForValue().set(key , value , seconds , TimeUnit.SECONDS);
    }

    public void set(final String key, final String value) {
        stringTemplate.opsForValue().set(key , value);
    }
    


    /**
     加数
     * @param key
     * @param count
     * @return
     */
    public long incrBy(final String key, final long count) {
        return stringTemplate.opsForValue().increment(key , count);
    }

    /**
        自增
     * @param key
     * @return
     */
    public long incr(final String key) {
        return stringTemplate.opsForValue().increment(key , 1);
    }
    /**
     自减
     * @param key
     * @return
     */
    public long decr(final String key) {
        return stringTemplate.opsForValue().increment(key , -1);
    }
    /**
            减数
     * @param key
     * @param count
     * @return
     */
    public long decrBy(final String key, final long count) {
        return stringTemplate.opsForValue().increment(key , -count);
    }

    public void del(final String... keys) {
        stringTemplate.delete(Arrays.asList(keys));
    }

    public void append(final String key, final String value) {
        stringTemplate.opsForValue().append(key, value);
    }

    public Long lpush(final String key, final String... values) {
        return stringTemplate.opsForList().leftPushAll(key, values);
    }

    public List lrange(final String key, final int start, final int end) {
        return stringTemplate.opsForList().range(key, start, end);
    }

    public long llen(final String key) {
        return stringTemplate.opsForList().size(key);
    }

    public void hmset(final String key, final Map<String , String> map) {
        stringTemplate.opsForHash().putAll(key , map);
    }
    public void hmset(final String key, final Map<String , String> map , int seconds) {
        this.hmset(key , map);
        stringTemplate.expire(key, seconds , TimeUnit.SECONDS);

    }

    /**
     * 创建列表 --- 有效时间
     * @param key
     * @param list
     * @param seconds
     */
    public void lpushlist(final String key, List<String> list , int seconds){
        if (list == null || list.size() == 0) {
            return ;
        }
        stringTemplate.opsForList().leftPushAll(key, list.toArray(new String[0]));
        stringTemplate.expire(key, seconds , TimeUnit.SECONDS);
    }

    /**
     * 创建列表
     * @param key
     * @param list
     */
    public void lpushlist(final String key, List<String> list ){
        if (list == null || list.size() == 0) {
            return ;
        }
        stringTemplate.opsForList().leftPushAll(key, list.toArray(new String[0]));
    }

    /**
     * 往右边插入 创建列表
     * @param key
     * @param list
     */
    public void rpushlist(final String key, List<String> list ){
        if (list == null || list.size() == 0) {
            return ;
        }
        stringTemplate.opsForList().rightPushAll(key, list.toArray(new String[0]));
    }
    /**
     * 获取所有列表记录
     * @param key
     * @return
     */
    public List lrangeall(final String key) {
        return stringTemplate.opsForList().range(key, 0, -1);
    }

    public void hset(final String key, final String hkey, final String value) {
        stringTemplate.opsForHash().put(key, hkey, value);
    }

    public boolean hsetnx(final String key, final String hkey, final String value) {
       return stringTemplate.opsForHash().putIfAbsent(key, hkey, value);
    }

    public boolean setnx(final String key, final String value) {
         return stringTemplate.opsForValue().setIfAbsent(key, value);
    }

    public Long hdel(final String key, final String... hkeys) {
        return stringTemplate.opsForHash().delete(key , hkeys);
    }

    public Long hinc(final String key, final String hkey, final long value) {
        return stringTemplate.opsForHash().increment(key, hkey, value);
    }

    
    public long hlen(final String key) {
        return   stringTemplate.opsForHash().size(key);
    }

    public Set hkeys(final String key) {
         return      stringTemplate.opsForHash().keys(key);
    }
    
    public List<String> hvals(final String key) {
        List<Object> list = stringTemplate.opsForHash().values(key);
        List<String> result = new ArrayList<>();
        for (Object o :list) {
            result.add(String.valueOf(o));
        }

        return result;
   }

    public List hmget(final String key, final String... hkey) {
         return stringTemplate.opsForHash().multiGet(key, Arrays.asList(hkey));
    }
    public  Map<String, String> hmgetAll(final String key) {
        Map<Object, Object> map = stringTemplate.opsForHash().entries(key);
        Map<String, String> result = new HashMap<>();
        for (Object k : map.keySet()) {
            result.put(String.valueOf(k) , String.valueOf(map.get(k)));
        }
        return result;
    }

    public String hget(final String key, final String hkey) {
        return  String.valueOf(stringTemplate.opsForHash().get(key, hkey));
    }

    public void expire(final String key , int seconds) {
        stringTemplate.expire(key , seconds , TimeUnit.SECONDS);
    }

	public String lpop(final String key) {
        return stringTemplate.opsForList().leftPop(key);
	}
    public String rpop(final String key) {
        return stringTemplate.opsForList().rightPop(key);
    }
	/**
	 * set添加
	 * @param key
	 * @param member
	 * @return
	 */
	public long sadd(final String key, String member) {
        return stringTemplate.opsForSet().add(key, member);
    }
	/**
	 * set移出
	 * @param key
	 * @param member
	 * @return
	 */
	public long srem(final String key, String member) {
        return stringTemplate.opsForSet().remove(key, member);
    }
	/**
	 * set数量
	 * @param key
	 * @return
	 */
	public long scard(final String key) {
        return stringTemplate.opsForSet().size(key);
    }
	/**
	 * 取得set列表
	 * @param key
	 * @return
	 */
	public Set<String> smembers(final String key) {
        return stringTemplate.opsForSet().members(key);
    }
	/**
	 * redisCluster实现keys方法
	 * @param pattern
	 * @return
	 */
    public Set<String> keys(String pattern){  
        return  stringTemplate.keys(pattern);
    }

    public boolean zadd(final String key  , String member  ,double score) {
        return stringTemplate.opsForZSet().add(key, member , score);
    }

    public long zrem(final String key  , String member) {
        return stringTemplate.opsForZSet().remove(key, member);
    }

    public long zcard(final String key) {
        return stringTemplate.opsForZSet().size(key);
    }

    public Double zscore(final String key , String member){
        return stringTemplate.opsForZSet().score(key, member);
    }

    public boolean sismember(final String key , String member) {
        return  stringTemplate.opsForSet().isMember(key, member);
    }

    /**
     * 取得zset列表
     * @param key
     * @return
     */
    public Set<String> zrange(final String key, long start , long end) {
        return  stringTemplate.opsForZSet().range(key , start , end);
    }
    /**
     * 取得zset列表反序
     * @param key
     * @return
     */
    public Set<String> zrevrange(final String key, long start , long end) {
        return stringTemplate.opsForZSet().reverseRange(key , start , end);
    }
    /**
     * 取得zset列表带scores
     * @param key
     * @return
     */
    public Set zrangeWithScores(final String key, long start , long end) {
        return stringTemplate.opsForZSet().rangeWithScores(key , start , end);
    }

    /**
     * 取得zset列表带scores 反序
     * @param key
     * @return
     */
    public Set zrevrangeWithScores(final String key, long start , long end) {
        return stringTemplate.opsForZSet().reverseRangeWithScores(key , start , end);
    }
    /**
     * 移出zset列表
     * @param key
     * @return
     */
    public Long zremrangeByScore(final String key,  double start, double end) {
        return stringTemplate.opsForZSet().removeRangeByScore(key , start , end);
    }

    public long lrem(final String key  , String member) {
        return  stringTemplate.opsForList().remove(key,0, member);
    }

    public Long ttl(String key) {
        return stringTemplate.getExpire(key);
    }

    public String type(String key) {
        return stringTemplate.type(key).code();
    }


}
