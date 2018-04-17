package cn.edu.nefu.embedded.user.impl;

import cn.edu.nefu.embedded.user.api.UserService;
import cn.edu.nefu.embedded.user.common.RemoteResult;
import cn.edu.nefu.embedded.user.dal.UserMapper;
import cn.edu.nefu.embedded.user.domain.entity.UserInfo;
import cn.edu.nefu.embedded.user.dto.UserDto;
import cn.edu.nefu.embedded.user.util.DtoUtil;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现
 * created by banshui on 18/04/14
 */
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserMapper userMapper;

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
    if(CollectionUtils.isEmpty(userIds)){
      return new RemoteResult<List<UserDto>>().error("1002", "参数为空");
    }
    List<UserInfo> userInfos = userMapper.queryByUserIds(userIds);
    return new RemoteResult<List<UserDto>>().success(DtoUtil.changeList(userInfos, UserDto.class));
  }

  @Override
  public RemoteResult<UserDto> queryByEmail(String email) {
    if(StringUtils.isBlank(email)){
      return new RemoteResult<UserDto>().error("1002", "参数为空");
    }
    UserInfo userInfo = userMapper.queryByEmail(email);
    return new RemoteResult<UserDto>().success(DtoUtil.change(userInfo, UserDto.class));
  }

  @Override
  public RemoteResult<UserDto> queryByPhone(String phone) {
    if(StringUtils.isBlank(phone)){
      return new RemoteResult<UserDto>().error("1002", "参数为空");
    }
    UserInfo userInfo = userMapper.queryByEmail(phone);
    return new RemoteResult<UserDto>().success(DtoUtil.change(userInfo, UserDto.class));
  }

  @Override
  public RemoteResult<UserDto> queryByStudentId(String studentId) {
    if(StringUtils.isBlank(studentId)){
      return new RemoteResult<UserDto>().error("1002", "参数为空");
    }
    UserInfo userInfo = userMapper.queryByEmail(studentId);
    return new RemoteResult<UserDto>().success(DtoUtil.change(userInfo, UserDto.class));
  }

  @Override
  public RemoteResult<UserDto> queryByNickName(String nickName) {
    if(StringUtils.isBlank(nickName)){
      return new RemoteResult<UserDto>().error("1002", "参数为空");
    }
    UserInfo userInfo = userMapper.queryByEmail(nickName);
    return new RemoteResult<UserDto>().success(DtoUtil.change(userInfo, UserDto.class));
  }
}
