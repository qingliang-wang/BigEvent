package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import com.itheima.utils.JwtUtil;
import com.itheima.utils.Md5Util;
import com.itheima.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.NamedBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {

        //查询用户
        User user = userService.findByUserName(username);
        if (user == null) {
            // 没有占用
            // 注册
            userService.register(username, password);
            return Result.success();

        } else {
            // 占用
            return Result.error("用户名已经被占用");

        }
        // 注册
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        User loginuser = userService.findByUserName(username);
        if (loginuser == null) {
            return Result.error("用户名错误!");
        }

        if (Md5Util.getMD5String(password).equals(loginuser.getPassword())) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginuser.getId());
            claims.put("username", loginuser.getUsername());

            // 登录成功
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }
        return Result.error("密码错误!");
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo(@RequestHeader(name = "Authorization") String token) {
//        Map<String, Object> map = JwtUtil.parseToken(token);
//        String username = (String) map.get("username");
//        Map o = ThreadLocalUtil.get();
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUserName(username);
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("updateAvatar")
    public Result updateAcatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params) {
        // 校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");
        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd))
        {
            return Result.error("缺少必要的参数");
        }
        return userService.updatePwd(oldPwd, newPwd, rePwd);
        // 调用service 完成密码更新
    }



}
