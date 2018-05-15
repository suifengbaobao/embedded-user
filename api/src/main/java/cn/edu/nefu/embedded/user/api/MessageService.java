package cn.edu.nefu.embedded.user.api;

import cn.edu.nefu.embedded.user.common.RemoteResult;

/**
 * 短信服务，登录-注册（暂时只开发注册功能）
 * created by banshui on 2018/5/11
 */
public interface MessageService {

  /**
   * 发送短信注册码
   * @param phone 电话号码
   * @return true-发送成功，false-发送失败
   */
  RemoteResult<Boolean> sendRegisterMsg(String phone);
}
