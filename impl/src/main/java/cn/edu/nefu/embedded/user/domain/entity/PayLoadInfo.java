package cn.edu.nefu.embedded.user.domain.entity;

import lombok.Data;

/**
 * JWT 负载实体信息
 * created by banshui on 2018/5/7
 */
@Data
public class PayLoadInfo {

  /**
   * 用户id
   */
  private Long userId;
  /**
   * 签发者
   */
  private String iss;
  /**
   * 签发时间
   */
  private Long iat;
}
