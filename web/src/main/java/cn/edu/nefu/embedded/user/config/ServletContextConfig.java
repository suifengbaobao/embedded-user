package cn.edu.nefu.embedded.user.config;

import cn.edu.nefu.embedded.user.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * MVC 配置文件
 * created by banshui on 2018/4/19
 */
@Configuration
public class ServletContextConfig extends WebMvcConfigurationSupport {

  @Override
  protected void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LoginInterceptor())
            .addPathPatterns("/audit/**");
  }
}
