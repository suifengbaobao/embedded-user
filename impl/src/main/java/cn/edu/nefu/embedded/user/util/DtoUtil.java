package cn.edu.nefu.embedded.user.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

/**
 * 对象转换工具
 * created by banshui on 2018/4/15
 */
public class DtoUtil {

  /**
   * 转换属性相同的实体类
   * @param obj   源对象
   * @param clazz 目标类型
   * @param <T>   目标类型
   * @return 目标对象
   */
  public static <T> T change(Object obj, Class<T> clazz){
    try {
      T t = clazz.newInstance();
      BeanUtils.copyProperties(obj, t);
      return t;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 批量转换属性相同的实体类
   * @param objs  源对象集合
   * @param clazz 目标对象类型
   * @param <T>   目标对象类型
   * @return 目标对象集合
   */
  public static <T> List<T> changeList(List<? extends Object> objs, Class<T> clazz){
    List<T> list = new ArrayList<>();
    if(CollectionUtils.isEmpty(objs)){
      return list;
    }
    return objs.stream().map(item -> change(item, clazz)).collect(Collectors.toList());
  }
}
