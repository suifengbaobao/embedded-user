package cn.edu.nefu.embedded.user.api;

import cn.edu.nefu.embedded.user.common.RemoteResult;
import cn.edu.nefu.embedded.user.dto.UserDto;
import java.util.List;

/**
 * 用户服务
 * created by banshui on 18/04/14
 */
public interface UserService {

  /**
   * 添加一个新用户
   * @param userDto 用户传输对象
   * @return userId
   */
  RemoteResult<Long> insert(UserDto userDto);

  /**
   * 更新用户信息
   * @param userDto 用户传输对象
   * @return true-更新成功 false-更新失败
   */
  RemoteResult<Boolean> update(UserDto userDto);

  /**
   * 根据userId删除一个用户（软删除）
   * @param userId 用户id
   * @return true-删除成功 false-删除失败
   */
  RemoteResult<Boolean> deleteByUserId(Long userId);

  /**
   * 根据userId查询用户信息
   * @param userId 用户id
   * @return 用户信息
   */
  RemoteResult<UserDto> queryByUserId(Long userId);

  /**
   * 根据userIds批量查询用户，每次最大支持查询500条
   * @param userIds 用户id集合
   * @return 用户信息集合
   */
  RemoteResult<List<UserDto>> queryByUserIds(List<Long> userIds);

  /**
   * 根据邮箱查询用户信息
   * @param email 邮箱
   * @return 用户信息
   */
  RemoteResult<UserDto> queryByEmail(String email);

  /**
   * 根据用户手机号查询用户信息
   * @param phone 手机号码
   * @return 用户信息
   */
  RemoteResult<UserDto> queryByPhone(String phone);

  /***
   * 根据学号查询用户信息
   * @param studentId 学号（学校学生唯一标识）
   * @return 用户信息
   */
  RemoteResult<UserDto> queryByStudentId(String studentId);

  /**
   * 根据昵称查询用户信息
   * @param nickName 昵称
   * @return 用户信息
   */
  RemoteResult<UserDto> queryByNickName(String nickName);
}
