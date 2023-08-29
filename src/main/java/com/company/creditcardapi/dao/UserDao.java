package com.company.creditcardapi.dao;

import com.company.creditcardapi.models.User;

import java.util.List;

public interface UserDao {

    User addUser(User user);

    User findUserById(Integer id);

    void deleteUser(Integer id);

    List<User> findAllUsers();

    void updateUser(User user);
}
