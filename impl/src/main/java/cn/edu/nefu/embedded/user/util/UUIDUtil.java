package cn.edu.nefu.embedded.user.util;

import java.util.UUID;

/**
 * UUID 工具类
 * created by banshui on 2018/5/15
 */
public class UUIDUtil {

  /**
   * 获取格式化的uuid
   * @return 格式化后的uuid
   */
  public static String getUUID(){
    return UUID.randomUUID().toString().replaceAll("-", "");
  }

  /**
   * 获取原始的uuid
   * @return 原始的uuid
   */
  public static String getOriginalUUID(){
    return UUID.randomUUID().toString();
  }
}
