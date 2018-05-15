package cn.edu.nefu.embedded.user.controller;

import cn.edu.nefu.embedded.user.api.UserService;
import cn.edu.nefu.embedded.user.cache.CacheService;
import cn.edu.nefu.embedded.user.common.RemoteResult;
import cn.edu.nefu.embedded.user.dto.UserDto;
import cn.edu.nefu.embedded.user.response.ResponseResult;
import cn.edu.nefu.embedded.user.util.CacheKeyUtil;
import cn.edu.nefu.embedded.user.util.UUIDUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录控制器
 * created by banshui on 2018/4/20
 */
@RestController
@RequestMapping("/user/")
public class UserController {
  private final UserService userService;
  private final CacheService cacheService;

  @Autowired
  public UserController(UserService userService, CacheService cacheService) {
    this.userService = userService;
    this.cacheService = cacheService;
  }
  @RequestMapping(value = "generalRegister", method = RequestMethod.POST)
  public Object generalRegister(@RequestBody UserDto userDto){
    if (userDto == null){
      return new ResponseResult().error("1002", "参数异常");
    }
    // 一大堆的参数校验
    if(StringUtils.isBlank(userDto.getUserName())){
      return new ResponseResult().error("1002", "用户名为空白符");
    }
    if(StringUtils.isBlank(userDto.getNickName())){
      return new ResponseResult().error("1002", "昵称不能为空");
    }
    RemoteResult<UserDto> userRes = userService.queryByNickName(userDto.getNickName());
    if(userRes.isSuccess() && userRes.getValue() != null){
      return new ResponseResult().error("1002", "该昵称已被使用，换一个吧~");
    }
    return null;
  }

  /**
   * 短信注册接口
   * @param phone         手机号
   * @param registerCode  验证码
   * @param password      密码
   * @param re_password   重复的密码
   * @return 响应
   */
  @RequestMapping(value = "msgRegister", method = RequestMethod.POST)
  public Object msgRegister(@RequestParam("phone") String phone, @RequestParam("registerCode") String registerCode,
                            @RequestParam("password") String password, @RequestParam("re_password") String re_password){
    if(StringUtils.isBlank(phone) || StringUtils.isBlank(registerCode) || StringUtils.isBlank(password) || StringUtils.isBlank(re_password)){
      return new ResponseResult().error("1002", "参数异常");
    }
    try {
      // 验证码校验，按理说这些校验都应该交给前端的，但防止出错，在后端也验证一下
      String codeVal = cacheService.get(CacheKeyUtil.getRegisterKey(phone)).toString();
      if(! password.equals(re_password)){
        return new ResponseResult().error("1002", "两次输入的密码不一样，请重新输入！");
      }
      if(! registerCode.equals(codeVal)){
        return new ResponseResult().error("1002", "验证码输入不正确！");
      }
      // 开始注册用户
      UserDto userDto = new UserDto();
      userDto.setPhone(phone);
      userDto.setUuid(UUIDUtil.getUUID());
      // md5密文存储
      userDto.setPassword(DigestUtils.md5Hex(password));
      RemoteResult<Long> result = userService.insert(userDto);
      if(result.isSuccess() && result.getValue() != null){
        // 删除缓存中的手机账号注册信息
        cacheService.del(CacheKeyUtil.getRegisterKey(phone));
        return new ResponseResult().success(result.getValue());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ResponseResult().error("1004", "服务错误！");
  }

  @RequestMapping(method = RequestMethod.PUT)
  public Object updateUser(@RequestBody UserDto userDto){
    return null;
  }

  @RequestMapping(value = "login", method = RequestMethod.POST)
  public Object login(){
    return null;
  }
}
