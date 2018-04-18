package cn.edu.nefu.embedded.user;

import cn.edu.nefu.embedded.user.api.UserService;
import cn.edu.nefu.embedded.user.common.RemoteResult;
import cn.edu.nefu.embedded.user.dto.UserDto;
import java.util.List;
import java.util.UUID;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * UserServiceTest
 * created by banshui on 2018/4/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmbeddedUserApplication.class)
public class UserServiceTest {
  @Autowired
  private UserService userService;
  @Test
  public void testInsert(){
    UserDto userDto = new UserDto();
    userDto.setUuid(UUID.randomUUID().toString().replaceAll("-", ""));
    userDto.setUserName("李四");
    userDto.setNickName("四儿");
    userDto.setAvatar("http://ogmrlx6wj.bkt.clouddn.com/hzw-1002.png");
    userDto.setCollege("机电工程学院");
    userDto.setSubject("电子信息工程");
    userDto.setClassLevel(3);
    userDto.setBirthday(System.currentTimeMillis() / 1000);
    userDto.setEmail("321@163.com");
    userDto.setPhone("15534567812");
    userDto.setWeChat("lisier");
    userDto.setQq("33332222");
    userDto.setSex(1);
    userDto.setSignature("努力，奋斗，加油！");
    userDto.setStudentId("2014333333");
    userDto.setPassword("oooo123");
    userDto.setIsDeleted(Boolean.FALSE);
    RemoteResult<Long> result = userService.insert(userDto);
    System.out.print(result);
  }

  @Test
  public void testUpdate(){
    UserDto userDto = new UserDto();
    userDto.setUserId(1L);
    userDto.setUserName("测试李四");
    RemoteResult<Boolean> result = userService.update(userDto);
    System.out.print(result);
  }

  @Test
  public void testQueryByUserId(){
    RemoteResult<UserDto> result = userService.queryByUserId(2L);
    System.out.println(result);
  }

  @Test
  public void testQueryByEmail(){
    RemoteResult<UserDto> result = userService.queryByEmail("123@163.com");
    System.out.println(result);
  }

  @Test
  public void testQueryByPhone(){
    RemoteResult<UserDto> result = userService.queryByPhone("15512345678");
    System.out.println(result);
  }

  @Test
  public void testQueryByStudentId(){
    RemoteResult<UserDto> result = userService.queryByStudentId("2014333333");
    System.out.println(result);
  }

  @Test
  public void testQueryByNickName(){
    RemoteResult<UserDto> result = userService.queryByNickName("四儿");
    System.out.println(result);
  }

  @Test
  public void testQueryByUserIds(){
    RemoteResult<List<UserDto>> result = userService.queryByUserIds(Lists.newArrayList(1L, 2L));
    System.out.println(result);
  }

  @Test
  public void testDeleteByUserId(){
    RemoteResult<Boolean> result = userService.deleteByUserId(1L);
    System.out.println(result);
  }
}
