package com.itheima.service;

import com.itheima.pojo.Result;
import com.itheima.pojo.User;

import java.util.Map;

public interface UserService {
    // 根据用户名查询用户
    User findByUserName(String username);

    // 注册
    void register(String username, String password);

    // 更新
    void update(User user);

    void updateAvatar(String avatarUrl);

    void updaePwd(Map<String, String> params);

    void updatePwd(String newPwd);

    Result updatePwd(String oldPwd, String newPwd, String rePwd);
}
