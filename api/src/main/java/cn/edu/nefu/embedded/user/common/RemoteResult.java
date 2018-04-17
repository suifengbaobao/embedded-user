package cn.edu.nefu.embedded.user.common;

/**
 * 远程调用结果对象
 * created by banshui on 2018/4/14
 */
public class RemoteResult<T> {

  /**
   * 结果值
   */
  private T value;
  /**
   * 结果状态
   */
  private boolean success;
  /**
   * 状态码
   */
  private String code;
  /**
   * 结果信息
   */
  private String msg;

  public RemoteResult<T> success(T value){
    this.value = value;
    this.success = true;
    this.code = "1001";
    this.msg = "SUCCESS";
    return this;
  }

  public RemoteResult<T> error(String code, String msg){
    this.value = null;
    this.success = false;
    this.code = code;
    this.msg = msg;
    return this;
  }

  public T getValue() {
    return value;
  }

  public void setValue(T value) {
    this.value = value;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
}
