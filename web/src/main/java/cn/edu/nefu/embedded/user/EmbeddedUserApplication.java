package cn.edu.nefu.embedded.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * created by banshui on 2018/4/17
 */
@SpringBootApplication
@MapperScan("cn.edu.nefu.embedded.user.dal")
public class EmbeddedUserApplication {

  public static void main(String[] args) {
    SpringApplication.run(EmbeddedUserApplication.class, args);
  }
}
