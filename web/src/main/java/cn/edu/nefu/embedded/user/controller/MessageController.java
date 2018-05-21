package cn.edu.nefu.embedded.user.controller;

import cn.edu.nefu.embedded.user.api.MessageService;
import cn.edu.nefu.embedded.user.api.UserService;
import cn.edu.nefu.embedded.user.common.RemoteResult;
import cn.edu.nefu.embedded.user.dto.UserDto;
import cn.edu.nefu.embedded.user.response.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短信服务相关接口 created by banshui on 2018/5/11
 */
@RestController
@RequestMapping("/msg/")
public class MessageController {

  private final MessageService messageService;
  private final UserService userService;

  @Autowired
  public MessageController(MessageService messageService, UserService userService) {
    this.messageService = messageService;
    this.userService = userService;
  }

  /**
   * 手机号注册获取验证码
   * @param phone 手机
   * @return 响应结果
   */
  @RequestMapping(value = "registerCode")
  public Object registerCode(@RequestParam("phone") String phone) {
    if (StringUtils.isBlank(phone)) {
      return new ResponseResult().error("1002", "手机号为空！");
    }
    // 判断当前手机号是否被注册
    RemoteResult<UserDto> userDtoRemoteResult = userService.queryByPhone(phone);
    if (userDtoRemoteResult.isSuccess() && userDtoRemoteResult.getValue() != null) {
      return new ResponseResult().error("1004", "该手机已被注册，请直接登录~");
    }
    // 开始发送验证码服务
    try {
      RemoteResult<Boolean> result = messageService.sendRegisterMsg(phone);
      if (result.isSuccess() && result.getValue()) {
        return new ResponseResult().success(result.getValue());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ResponseResult().error("1004", "服务错误！");
  }

  /**
   * 手机号验证获取注册码
   * @param phone 手机号
   * @return 响应结果
   */
  @RequestMapping(value = "phoneVerify")
  public Object phoneVerify(@RequestParam("phone") String phone) {
    if (StringUtils.isBlank(phone)) {
      return new ResponseResult().error("1002", "手机号为空！");
    }
    // 判断当前手机号是否被注册
    RemoteResult<UserDto> userDtoRemoteResult = userService.queryByPhone(phone);
    if (userDtoRemoteResult.isSuccess() && userDtoRemoteResult.getValue() != null) {
      return new ResponseResult().error("1004", "该手机已被注册，请更换一个~");
    }
    // 开始发送验证码服务
    try {
      RemoteResult<Boolean> result = messageService.sendPhoneVerify(phone);
      if (result.isSuccess() && result.getValue()) {
        return new ResponseResult().success(result.getValue());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ResponseResult().error("1004", "服务错误！");
  }
}
