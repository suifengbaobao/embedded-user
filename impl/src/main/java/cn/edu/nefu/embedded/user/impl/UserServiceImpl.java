package cn.edu.nefu.embedded.user.impl;

import cn.edu.nefu.embedded.user.api.UserService;
import cn.edu.nefu.embedded.user.common.RemoteResult;
import cn.edu.nefu.embedded.user.dal.UserMapper;
import cn.edu.nefu.embedded.user.domain.entity.UserInfo;
import cn.edu.nefu.embedded.user.dto.UserDto;
import cn.edu.nefu.embedded.user.util.DtoUtil;
import java.util.List;
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
    if(userDto == null){
      return new RemoteResult<Long>().error("1002", "参数为空");
    }
    UserInfo userInfo = DtoUtil.change(userDto, UserInfo.class);
    if(userInfo == null){
      return new RemoteResult<Long>().error("4004", "服务异常");
    }
    long count = userMapper.insert(userInfo);
    if(count == 1 && userInfo.getUserId() != null){
      return new RemoteResult<Long>().succes(userInfo.getUserId());
    }
    return new RemoteResult<Long>().error("4004", "服务异常");
  }

  @Override
  public RemoteResult<Boolean> update(UserDto userInfo) {
    return null;
  }

  @Override
  public RemoteResult<Boolean> deleteByUserId(Long userId) {
    return null;
  }

  @Override
  public RemoteResult<UserDto> queryByUserId(Long userId) {
    return null;
  }

  @Override
  public RemoteResult<List<UserDto>> queryByUserIds(List<Long> userIds) {
    return null;
  }

  @Override
  public RemoteResult<UserDto> queryByEmail(String email) {
    return null;
  }

  @Override
  public RemoteResult<UserDto> queryByPhone(String phone) {
    return null;
  }

  @Override
  public RemoteResult<UserDto> queryByStudentId(String studentId) {
    return null;
  }

  @Override
  public RemoteResult<UserDto> queryByNickName(String nickName) {
    return null;
  }
}
