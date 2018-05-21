package cn.edu.nefu.embedded.user.impl;

import cn.edu.nefu.embedded.user.api.MessageService;
import cn.edu.nefu.embedded.user.cache.CacheService;
import cn.edu.nefu.embedded.user.common.RemoteResult;
import cn.edu.nefu.embedded.user.util.CacheKeyUtil;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import java.util.Random;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * created by banshui on 2018/5/11
 */
@Service
public class MessageServiceImpl implements MessageService {

  private final CacheService cacheService;
  private Random random = new Random();
  private static final int EXPIRE_TIME = 60 * 5;   // 5分钟有效时间
  private static final String product = "Dysmsapi";// 短信API产品名称（短信产品名固定，无需修改）
  private static final String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名（接口地址固定，无需修改）
  private static final String accessKeyId = "****";// 你的accessKeyId,参考本文档步骤2
  private static final String accessKeySecret = "****";// 你的accessKeySecret

  @Autowired
  public MessageServiceImpl(CacheService cacheService) {
    this.cacheService = cacheService;
  }

  @Override
  public RemoteResult<Boolean> sendRegisterMsg(String phone) {
    if (StringUtils.isBlank(phone)) {
      return new RemoteResult<Boolean>().error("1002", "手机号为空！");
    }
    try {
       SendSmsResponse sendSmsResponse = getSendSmsResponse(phone, "SMS_134220008", CacheKeyUtil.getRegisterKey(phone));
      if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
        return new RemoteResult<Boolean>().success(Boolean.TRUE);
      }
    } catch (ClientException e) {
      e.printStackTrace();
    }
    return new RemoteResult<Boolean>().error("1004", "服务出错！");
  }

  @Override
  public RemoteResult<Boolean> sendPhoneVerify(String phone) {
    if (StringUtils.isBlank(phone)) {
      return new RemoteResult<Boolean>().error("1002", "手机号为空！");
    }
    try {
      SendSmsResponse sendSmsResponse = getSendSmsResponse(phone, "SMS_135255182", CacheKeyUtil.getPhoneVerifyKey(phone));
      if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
        return new RemoteResult<Boolean>().success(Boolean.TRUE);
      }
    } catch (ClientException e) {
      e.printStackTrace();
    }
    return new RemoteResult<Boolean>().error("1004", "服务出错！");
  }

  /**
   * 随机生成五位数的验证码
   *
   * @return 一个五位数字符串
   */
  private String randomCode() {
    StringBuilder code = new StringBuilder();
    for (int i = 0; i < 5; i++) {
      code.append(random.nextInt(10));
    }
    return code.toString();
  }

  /**
   * 发送短信响应
   * @param phone        手机号
   * @param templateCode 模板
   * @param cacheKey     缓存key
   * @return             响应结果
   * @throws ClientException
   */
  private SendSmsResponse getSendSmsResponse(String phone, String templateCode, String cacheKey)
      throws ClientException {
    //设置超时时间-可自行调整
    System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
    System.setProperty("sun.net.client.defaultReadTimeout", "10000");
    //初始化ascClient需要的几个参数
    //初始化ascClient,暂时不支持多region（请勿修改）
    IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
        accessKeySecret);
    DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
    IAcsClient acsClient = new DefaultAcsClient(profile);
    //组装请求对象
    SendSmsRequest request = new SendSmsRequest();
    //使用post提交
    request.setMethod(MethodType.POST);
    //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
    request.setPhoneNumbers(phone);
    //必填:短信签名-可在短信控制台中找到
    request.setSignName("Embedded实验室");
    //必填:短信模板-可在短信控制台中找到
    request.setTemplateCode(templateCode);
    //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
    //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
    // 获取随机验证码
    String code = randomCode();
    // 保存到redis中
    cacheService.setEx(cacheKey, code, EXPIRE_TIME);
    request.setTemplateParam("{\"code\":" + code + "}");
    //请求失败这里会抛ClientException异常
    return acsClient.getAcsResponse(request);
  }
}
