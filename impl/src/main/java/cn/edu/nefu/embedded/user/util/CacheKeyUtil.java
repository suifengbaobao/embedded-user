package cn.edu.nefu.embedded.user.util;

/**
 * 缓存key管理工具
 * created by banshui on 2018/5/11
 */
public class CacheKeyUtil {
  private static final String REGISTER_CODE = "REGISTER_CODE";

  /**
   * 获取注册验证码保存的信息key
   * @param phone 手机号
   * @return key
   */
  public static String getRegisterKey(String phone){
    return REGISTER_CODE + "-" + phone;
  }
}
