package cn.edu.nefu.embedded.user.dal;

import cn.edu.nefu.embedded.user.domain.entity.UserInfo;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 用户映射类 created by banshui on 2018/4/14
 */
@Repository
public interface UserMapper {

  long insert(UserInfo userInfo);

  long update(UserInfo userInfo);

  long deleteByUserId(@Param("userId") Long userId);

  UserInfo queryByUserId(@Param("userId") Long userId);

  List<UserInfo> queryByUserIds(@Param("userIds") List<Long> userIds);

  UserInfo queryByEmail(@Param("email") String email);

  UserInfo queryByPhone(@Param("phone") String phone);

  UserInfo queryByStudentId(@Param("studentId") String studentId);

  UserInfo queryByNickName(@Param("nickName") String nickName);
}
