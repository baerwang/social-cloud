package com.baerwang.user.interceptor;

import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.baerwang.common.utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器类
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/22 17:44
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*无论如何都放行，具体能不能操作还是在具体的操作中去判断*/
        /*拦截器只是负责把头请求头中包含token的令牌进行一个解析验证*/
        final String authorization = request.getHeader("Authorization");
        if (!StringUtils.isEmpty(authorization)) {
            if (authorization.startsWith("Bearer ")) {
                String token = authorization.substring(7);
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    if (claims != null) {
                        /*管理员*/
                        if ("admin".equals(claims.get("roles"))) {
                            request.setAttribute("admin_claims", token);
                        }
                        /*用户*/
                        if ("user".equals(claims.get("roles"))) {
                            request.setAttribute("user_claims", token);
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException("令牌不正确!");
                }
            }
        }
        return true;
    }
}
