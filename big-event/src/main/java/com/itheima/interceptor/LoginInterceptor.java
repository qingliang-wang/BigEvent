package com.itheima.interceptor;

import com.itheima.pojo.Result;
import com.itheima.utils.JwtUtil;
import com.itheima.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 令牌 验证
        String token = request.getHeader("Authorization");
        try {
            Map<String, Object> clamis = JwtUtil.parseToken(token);

            // 把业务数据存储到ThreadLocal 中
            ThreadLocalUtil.set(clamis);

            return  true ;
        }catch (Exception e)
        {
            //http 响应状态码是401
            response.setStatus(401);
            return false;

        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }
}
