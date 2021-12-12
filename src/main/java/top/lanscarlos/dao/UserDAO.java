package top.lanscarlos.dao;

import org.apache.ibatis.annotations.Param;
import top.lanscarlos.pojo.User;

import java.util.List;

public interface UserDAO {

    List<User> getAllUser();

    User getUserById(@Param("uid") String uid);

    User getUserByName(@Param("name") String name);

    int addUser(User user);

    int updateUser(User user);

}
