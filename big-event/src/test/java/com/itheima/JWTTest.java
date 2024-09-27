package com.itheima;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTTest {
    @Test
    public void testGen() {
        // 生成jwt的代码
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("username", "张三");
        // 生成 JWT 的代码
        // 设置过期时间为 1 小时
        Date expiresAt = new Date(System.currentTimeMillis() + 1000 * 60 * 60);

        // 生成 JWT 令牌
        String token = JWT.create()
                .withClaim("user", claims) // 放入自定义数据
                .withExpiresAt(expiresAt)  // 设置过期时间
                .sign(Algorithm.HMAC256("itheima")); // 使用 HMAC256 进行签名

        System.out.println(token);

    }

    @Test
    public void testParse() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
                "eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6IuW8oOS4iSJ9LCJleHAiOjE3MjY4NTEwMzV9." +
                "E20ciz2wb18UrHso2lYY0RaawS4ouv0smFV5lRwvxII";
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("itheima")).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        Map<String, Claim> claims = decodedJWT.getClaims();
        System.out.println(claims.get("user"));
    }
}
