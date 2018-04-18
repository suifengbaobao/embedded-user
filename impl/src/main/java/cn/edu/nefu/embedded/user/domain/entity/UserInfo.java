package cn.edu.nefu.embedded.user.domain.entity;

import lombok.Data;

/**
 * 用户信息实体
 * created by banshui on 2018/4/14
 */
@Data
public class UserInfo {
  /**
   * 用户唯一标识
   */
  private Long userId;
  /**
   * 全局通用唯一识别码
   */
  private String uuid;
  /**
   * 用户名（真名）
   */
  private String userName;
  /**
   * 昵称
   */
  private String nickName;
  /**
   * 头像
   */
  private String avatar;
  /**
   * 签名
   */
  private String signature;
  /**
   * 学号
   */
  private String studentId;
  /**
   * 密码
   */
  private String password;
  /**
   * 出生日期
   */
  private Long birthday;
  /**
   * 学院
   */
  private String college;
  /**
   * 专业
   */
  private String subject;
  /**
   * 班级
   */
  private Integer classLevel;
  /**
   * 性别 0-未知 1-男性 2-女性 3-中性
   */
  private Integer sex;
  /**
   * 电话号码
   */
  private String phone;
  /**
   * 邮箱
   */
  private String email;
  /**
   * 微信号
   */
  private String weChat;
  /**
   * QQ号
   */
  private String qq;
  /**
   * false-未删除 true-删除
   */
  private Boolean isDeleted;
  private Long updated;
  private Long created;
}
