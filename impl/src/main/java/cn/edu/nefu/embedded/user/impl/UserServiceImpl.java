package cn.edu.nefu.embedded.user.impl;

import cn.edu.nefu.embedded.user.api.UserService;
import cn.edu.nefu.embedded.user.cache.CacheService;
import cn.edu.nefu.embedded.user.common.RemoteResult;
import cn.edu.nefu.embedded.user.dal.UserMapper;
import cn.edu.nefu.embedded.user.domain.entity.PayLoadInfo;
import cn.edu.nefu.embedded.user.domain.entity.UserInfo;
import cn.edu.nefu.embedded.user.dto.UserDto;
import cn.edu.nefu.embedded.user.util.DtoUtil;
import cn.edu.nefu.embedded.user.util.JWTUtil;
import java.io.IOException;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现 created by banshui on 18/04/14
 */
@Service
public class UserServiceImpl implements UserService {

  private final UserMapper userMapper;
  private final CacheService cacheService;
  // 30分钟内没有浏览网页视为登录失效
  private static final Long REDIS_TIME = 60 * 30L;

  @Autowired
  public UserServiceImpl(UserMapper userMapper,
      CacheService cacheService) {
    this.userMapper = userMapper;
    this.cacheService = cacheService;
  }

  @Override
  public RemoteResult<Long> insert(UserDto userDto) {
    if (userDto == null) {
      return new RemoteResult<Long>().error("1002", "参数为空");
    }
    UserInfo userInfo = DtoUtil.change(userDto, UserInfo.class);
    long count = userMapper.insert(userInfo);
    if (count == 1 && userInfo != null && userInfo.getUserId() != null) {
      return new RemoteResult<Long>().success(userInfo.getUserId());
    }
    return new RemoteResult<Long>().error("4004", "服务异常");
  }

  @Override
  public RemoteResult<Boolean> update(UserDto userDto) {
    if (userDto == null || userDto.getUserId() == null) {
      return new RemoteResult<Boolean>().error("1002", "参数为空");
    }
    UserInfo userInfo = DtoUtil.change(userDto, UserInfo.class);
    long count = userMapper.update(userInfo);
    if (count == 1) {
      return new RemoteResult<Boolean>().success(Boolean.TRUE);
    } else {
      return new RemoteResult<Boolean>().error("4004", "更新失败");
    }
  }

  @Override
  public RemoteResult<Boolean> deleteByUserId(Long userId) {
    if (userId == null) {
      return new RemoteResult<Boolean>().error("1002", "参数为空");
    }
    long count = userMapper.deleteByUserId(userId);
    if (count == 1) {
      return new RemoteResult<Boolean>().success(Boolean.TRUE);
    } else {
      return new RemoteResult<Boolean>().error("4004", "删除失败");
    }
  }

  @Override
  public RemoteResult<UserDto> queryByUserId(Long userId) {
    if (userId == null) {
      return new RemoteResult<UserDto>().error("1002", "参数为空");
    }
    UserInfo userInfo = userMapper.queryByUserId(userId);
    return new RemoteResult<UserDto>().success(DtoUtil.change(userInfo, UserDto.class));
  }

  @Override
  public RemoteResult<List<UserDto>> queryByUserIds(List<Long> userIds) {
    if (CollectionUtils.isEmpty(userIds)) {
      return new RemoteResult<List<UserDto>>().error("1002", "参数为空");
    }
    List<UserInfo> userInfos = userMapper.queryByUserIds(userIds);
    return new RemoteResult<List<UserDto>>().success(DtoUtil.changeList(userInfos, UserDto.class));
  }

  @Override
  public RemoteResult<UserDto> queryByEmail(String email) {
    if (StringUtils.isBlank(email)) {
      return new RemoteResult<UserDto>().error("1002", "参数为空");
    }
    UserInfo userInfo = userMapper.queryByEmail(email);
    return new RemoteResult<UserDto>().success(DtoUtil.change(userInfo, UserDto.class));
  }

  @Override
  public RemoteResult<UserDto> queryByPhone(String phone) {
    if (StringUtils.isBlank(phone)) {
      return new RemoteResult<UserDto>().error("1002", "参数为空");
    }
    UserInfo userInfo = userMapper.queryByPhone(phone);
    return new RemoteResult<UserDto>().success(DtoUtil.change(userInfo, UserDto.class));
  }

  @Override
  public RemoteResult<UserDto> queryByStudentId(String studentId) {
    if (StringUtils.isBlank(studentId)) {
      return new RemoteResult<UserDto>().error("1002", "参数为空");
    }
    UserInfo userInfo = userMapper.queryByStudentId(studentId);
    return new RemoteResult<UserDto>().success(DtoUtil.change(userInfo, UserDto.class));
  }

  @Override
  public RemoteResult<UserDto> queryByNickName(String nickName) {
    if (StringUtils.isBlank(nickName)) {
      return new RemoteResult<UserDto>().error("1002", "参数为空");
    }
    UserInfo userInfo = userMapper.queryByNickName(nickName);
    return new RemoteResult<UserDto>().success(DtoUtil.change(userInfo, UserDto.class));
  }

  @Override
  public RemoteResult<UserDto> queryUserByToken(String token) {
    if(StringUtils.isBlank(token)){
      return new RemoteResult<UserDto>().error("1002", "参数为空！");
    }
    try {
      PayLoadInfo payLoadInfo = JWTUtil.unsign(token, PayLoadInfo.class);
      if (payLoadInfo != null) {
        UserInfo userInfo = (UserInfo) cacheService.get("TOKEN_" + payLoadInfo.getUserId());
        // 更新redis中token的生存时间(非常重要，调用这个方法时意味着用户一直在浏览网页)
        cacheService.expire("TOKEN_" + payLoadInfo.getUserId(), REDIS_TIME);
        return new RemoteResult<UserDto>().success(DtoUtil.change(userInfo, UserDto.class));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new RemoteResult<UserDto>().error("1004", "服务错误！");
  }

  @Override
  public RemoteResult<Boolean> logout(String token) {
    if(StringUtils.isBlank(token)){
      return new RemoteResult<Boolean>().error("1002", "参数为空！");
    }
    try {
      PayLoadInfo payLoadInfo = JWTUtil.unsign(token, PayLoadInfo.class);
      if (payLoadInfo != null) {
        // 删除redis中的数据
        cacheService.del("TOKEN_" + payLoadInfo.getUserId());
        return new RemoteResult<Boolean>().success(Boolean.TRUE);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new RemoteResult<Boolean>().error("1004", "服务错误！");
  }
}
