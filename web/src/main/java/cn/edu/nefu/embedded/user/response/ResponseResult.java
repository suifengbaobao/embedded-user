package cn.edu.nefu.embedded.user.response;

/**
 * 返回结果
 * created by banshui on 2018/4/20
 */
public class ResponseResult {

  /**
   * 返回值
   */
  private Object value;
  /**
   * 返回状态
   */
  private boolean success;
  /**
   * 返回状态码
   */
  private String code;
  /**
   * 返回信息
   */
  private String msg;

  public ResponseResult success(Object value){
    this.value = value;
    this.success = true;
    this.code = "1001";
    this.msg = "SUCCESS";
    return this;
  }

  public ResponseResult error(String code, String msg){
    this.value = null;
    this.success = false;
    this.code = code;
    this.msg = msg;
    return this;
  }
}
