package cn.edu.nefu.embedded.user.impl;

import cn.edu.nefu.embedded.user.api.UserService;
import cn.edu.nefu.embedded.user.common.RemoteResult;
import cn.edu.nefu.embedded.user.dto.UserDto;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现
 * created by banshui on 18/04/14
 */
@Service
public class UserServiceImpl implements UserService {

  @Override
  public RemoteResult<Long> insert(UserDto userDto) {
    return null;
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
