package cn.edu.nefu.embedded.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试controller
 * created by banshui on 2018/4/19
 */
@RestController
@RequestMapping("/audit/test")
public class TestController {
  @RequestMapping("hello")
  public Object test(){
    return "Hello World!";
  }
}
