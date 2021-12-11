package top.lanscarlos.dao;

import top.lanscarlos.pojo.User;

import java.util.List;

public interface UserDAO {

    List<User> getAllUser();

    User getUserById(String uid);

    User getUserByName(String name);

    int addUser(User user);

    int updateUser(User user);

}
