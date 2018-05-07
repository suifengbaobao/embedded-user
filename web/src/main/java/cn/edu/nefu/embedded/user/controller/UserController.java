package cn.edu.nefu.embedded.user.controller;

import cn.edu.nefu.embedded.user.api.UserService;
import cn.edu.nefu.embedded.user.cache.CacheService;
import cn.edu.nefu.embedded.user.domain.entity.UserInfo;
import cn.edu.nefu.embedded.user.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
  @RequestMapping(value = "register", method = RequestMethod.POST)
  public Object register(@RequestBody UserInfo userInfo){
    if (userInfo == null){
      return new ResponseResult().error("1002", "参数异常");
    }

    return null;
  }


  @RequestMapping(value = "login", method = RequestMethod.POST)
  public Object login(){
    return null;
  }
}
