package cn.edu.nefu.embedded.user.controller;

import cn.edu.nefu.embedded.user.api.UserService;
import cn.edu.nefu.embedded.user.cache.CacheService;
import cn.edu.nefu.embedded.user.common.RemoteResult;
import cn.edu.nefu.embedded.user.domain.entity.PayLoadInfo;
import cn.edu.nefu.embedded.user.dto.UserDto;
import cn.edu.nefu.embedded.user.response.ResponseResult;
import cn.edu.nefu.embedded.user.util.CacheKeyUtil;
import cn.edu.nefu.embedded.user.util.CookieUtils;
import cn.edu.nefu.embedded.user.util.JWTUtil;
import cn.edu.nefu.embedded.user.util.UUIDUtil;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录控制器 created by banshui on 2018/4/20
 */
@RestController
@RequestMapping("/user/")
public class UserController {

  private final UserService userService;
  private final CacheService cacheService;

  private static final String COOKIE_NAME = "EMBEDDED_TOKEN";
  private static final long TOKEN_EXPIRE = 60 * 30 * 7;

  @Autowired
  public UserController(UserService userService, CacheService cacheService) {
    this.userService = userService;
    this.cacheService = cacheService;
  }

  @RequestMapping(value = "generalRegister", method = RequestMethod.POST)
  public Object generalRegister(@RequestBody UserDto userDto) {
    if (userDto == null) {
      return new ResponseResult().error("1002", "参数异常");
    }
    if (StringUtils.isBlank(userDto.getNickName())) {
      return new ResponseResult().error("1002", "昵称不能为空");
    }
    try {
      userDto.setUuid(UUIDUtil.getUUID());
      RemoteResult<Long> result = userService.insert(userDto);
      if(result.isSuccess() && result.getValue() != null){
        return new ResponseResult().success(result.getValue());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ResponseResult().error("1004", "服务错误！");
  }

  /**
   * 短信注册接口
   * @param phone 手机号
   * @param registerCode 验证码
   * @param password 密码
   * @param re_password 重复的密码
   * @return 响应
   */
  @RequestMapping(value = "msgRegister", method = RequestMethod.POST)
  public Object msgRegister(@RequestParam("phone") String phone,
      @RequestParam("registerCode") String registerCode,
      @RequestParam("password") String password, @RequestParam("re_password") String re_password) {
    if (StringUtils.isBlank(phone) || StringUtils.isBlank(registerCode) || StringUtils
        .isBlank(password) || StringUtils.isBlank(re_password)) {
      return new ResponseResult().error("1002", "参数异常");
    }
    try {
      // 验证码校验，按理说这些校验都应该交给前端的，但防止出错，在后端也验证一下
      String codeVal = cacheService.get(CacheKeyUtil.getRegisterKey(phone)).toString();
      if (!password.equals(re_password)) {
        return new ResponseResult().error("1002", "两次输入的密码不一样，请重新输入！");
      }
      if (!registerCode.equals(codeVal)) {
        return new ResponseResult().error("1002", "验证码输入不正确！");
      }
      // 开始注册用户
      UserDto userDto = new UserDto();
      userDto.setPhone(phone);
      userDto.setUuid(UUIDUtil.getUUID());
      // md5密文存储
      userDto.setPassword(DigestUtils.md5Hex(password));
      RemoteResult<Long> result = userService.insert(userDto);
      if (result.isSuccess() && result.getValue() != null) {
        // 删除缓存中的手机账号注册信息
        cacheService.del(CacheKeyUtil.getRegisterKey(phone));
        return new ResponseResult().success(result.getValue());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ResponseResult().error("1004", "服务错误！");
  }

  /**
   * 验证用户注册信息
   */
  @RequestMapping(value = "/{param}/{type}", method = RequestMethod.GET)
  public Object check(@PathVariable("param") String param,
      @PathVariable("type") Integer type) {
    if (StringUtils.isBlank(param) || type == null) {
      return new ResponseResult().error("1002", "参数为空！");
    }
    try {
      if (type == 1) {
        RemoteResult<UserDto> result = userService.queryByNickName(param);
        return new ResponseResult().success(result.isSuccess() && result.getValue() != null);
      }else if(type == 2){
        RemoteResult<UserDto> result = userService.queryByPhone(param);
        return new ResponseResult().success(result.isSuccess() && result.getValue() != null);
      }else if(type == 3){
        RemoteResult<UserDto> result = userService.queryByEmail(param);
        return new ResponseResult().success(result.isSuccess() && result.getValue() != null);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ResponseResult().error("1004", "参数错误！");
  }

  @RequestMapping(method = RequestMethod.PUT)
  public Object updateUser(@RequestBody UserDto userDto) {
    return null;
  }

  @RequestMapping(value = "login", method = RequestMethod.POST)
  public Object login(@RequestParam("account") String account,
      @RequestParam("password") String password,
      HttpServletRequest request, HttpServletResponse response) {
    if (StringUtils.isBlank(account)) {
      return new ResponseResult().error("1002", "账号为空！");
    }
    try {
      // 验证登录（1-手机号，2-昵称）
      RemoteResult<UserDto> res1 = userService.queryByPhone(account);
      if (res1.isSuccess() && res1.getValue() != null) {
        if (res1.getValue().getPassword().equals(DigestUtils.md5Hex(password))) {
          // 登录成功
          PayLoadInfo payLoadInfo = new PayLoadInfo();
          payLoadInfo.setUserId(res1.getValue().getUserId());
          payLoadInfo.setIss("EMBEDDED-NEFU");
          payLoadInfo.setIat(System.currentTimeMillis() / 1000);
          String token = JWTUtil.sign(payLoadInfo, TOKEN_EXPIRE);
          CookieUtils.setCookie(request, response, COOKIE_NAME, token);
          return new ResponseResult().success(Boolean.TRUE);
        }
      } else {
        RemoteResult<UserDto> res2 = userService.queryByNickName(account);
        if (res2.isSuccess() && res2.getValue() != null) {
          PayLoadInfo payLoadInfo = new PayLoadInfo();
          payLoadInfo.setIss("EMBEDDED-NEFU");
          payLoadInfo.setUserId(res1.getValue().getUserId());
          payLoadInfo.setIat(System.currentTimeMillis() / 1000);
          String token = JWTUtil.sign(payLoadInfo, TOKEN_EXPIRE);
          CookieUtils.setCookie(request, response, COOKIE_NAME, token);
          return new ResponseResult().success(Boolean.TRUE);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseResult().error("1004", "内部服务错误！");
    }
    return new ResponseResult().error("1004", "用户名或密码错误！");
  }

  /**
   * 根据token查询用户
   *
   * @param token 每个用户生成的token
   * @return 响应结果
   */
  @RequestMapping(value = "{token}", method = RequestMethod.GET)
  public Object queryUserByToken(@PathVariable("token") String token) {
    try {
      RemoteResult<UserDto> result = this.userService.queryUserByToken(token);
      if (result.isSuccess() && result.getValue() != null) {
        return new ResponseResult().success(result.getValue());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ResponseResult().error("1004", "服务错误！");
  }

  /**
   * 退出登录
   */
  @RequestMapping(value = "logout/{token}", method = RequestMethod.GET)
  public void logout(@PathVariable("token") String token, HttpServletRequest request,
      HttpServletResponse response) {
    // 删除cookie
    CookieUtils.deleteCookie(request, response, COOKIE_NAME);
    // 删除redis中的数据
    RemoteResult<Boolean> result = userService.logout(token);
    if (result.isSuccess() && result.getValue()) {
      // 退出成功
      try {
        response.sendRedirect("http://www.embedded-nefu.com");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
