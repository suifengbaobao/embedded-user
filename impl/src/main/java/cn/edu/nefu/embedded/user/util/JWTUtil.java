package cn.edu.nefu.embedded.user.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类，用于生成token created by banshui on 2018/4/19
 */
public class JWTUtil {

  // 密钥
  private final static String SECRET = "123456";
  // Json序列化对象
  private static ObjectMapper mapper = new ObjectMapper();

  /**
   * 生成token
   *
   * @param obj 对象数据
   * @param maxAge 有效期 毫秒
   * @param <T> 对象类型
   * @return token
   * @throws UnsupportedEncodingException 字符编码异常
   * @throws JsonProcessingException Json解析异常
   */
  public static <T> String sign(T obj, long maxAge)
      throws UnsupportedEncodingException, JsonProcessingException {
    JWTCreator.Builder builder = JWT.create();
    builder.withHeader(createHead())//header
        .withSubject(mapper.writeValueAsString(obj));  //payload
    if (maxAge >= 0) {
      long expMillis = System.currentTimeMillis() + maxAge;
      Date exp = new Date(expMillis);
      builder.withExpiresAt(exp);
    }
    return builder.sign(Algorithm.HMAC256(SECRET));
  }

  /**
   * 解密
   *
   * @param token token字符串
   * @param clazz 解密后的类型
   * @param <T> 对象类型
   * @return 解密后的对象
   * @throws IOException IO异常
   */
  public static <T> T unsign(String token, Class<T> clazz) throws IOException {
    JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
    DecodedJWT jwt = verifier.verify(token);
    Date exp = jwt.getExpiresAt();
    if (exp != null && exp.after(new Date())) {
      String subject = jwt.getSubject();
      return mapper.readValue(subject, clazz);
    }
    return null;
  }

  /**
   * 创建head
   */
  private static Map<String, Object> createHead() {
    Map<String, Object> map = new HashMap<>();
    map.put("typ", "JWT");
    map.put("alg", "HS256");
    return map;
  }
}
