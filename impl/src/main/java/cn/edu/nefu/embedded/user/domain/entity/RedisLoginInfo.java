package cn.edu.nefu.embedded.user.domain.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * 存储在Redis中的用户信息
 * created by banshui on 2018/4/19
 */
@Data
public class RedisLoginInfo implements Serializable {

  /**
   * 用户id
   */
  private Long userId;
  /**
   * 生成的token
   */
  private String token;
  /**
   * 登录或者刷新应用的时间
   */
  private Long refTime;
}
