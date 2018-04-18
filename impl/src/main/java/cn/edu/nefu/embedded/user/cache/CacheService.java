package cn.edu.nefu.embedded.user.cache;

import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 缓存服务
 * created by banshui on 2018/4/18
 */
@Service
public class CacheService {
  @Autowired
  private RedisTemplate<String, Object> redisTemplate;

  /**
   * 设置缓存值
   * @param key   键
   * @param value 值
   */
  public void set(String key, Object value){
    redisTemplate.opsForValue().set(key, value);
  }

  /**
   * 根据key获取缓存值
   * @param key 键
   * @return    值
   */
  public Object get(String key){
    return redisTemplate.opsForValue().get(key);
  }

  /**
   * 设置有失效时间的存储对
   * @param key    键
   * @param value  值
   * @param expire 失效时间，秒
   */
  public void setEx(String key, Object value, int expire){
    redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
  }

  /**
   * 删除某一个键
   * @param key 键
   */
  public void del(String key){
    redisTemplate.delete(key);
  }
}
