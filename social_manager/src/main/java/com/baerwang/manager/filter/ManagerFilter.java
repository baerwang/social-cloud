package com.baerwang.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.baerwang.common.utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 过滤器类
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/24 14:52
 */
@Component
public class ManagerFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 在请求之前执行还是之后执行
     * 之前：pre
     * 之后：post
     *
     * @return
     */
    @Override
    public String filterType() {
        /*前置过滤器*/
        return "pre";
    }

    /**
     * 多个过滤器之间执行的顺序
     *
     * @return
     */
    @Override
    public int filterOrder() {
        /*优先级为0,数字越大,优先级越低*/
        return 0;
    }

    /**
     * 当前过滤器是否开启
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        /*是否执行过滤器,此处为true,说明需要过滤*/
        return true;
    }

    /**
     * 过滤器内执行的内容
     * return 任何值都表示继续执行
     * setSendZuulResponse(false)表示不再执行
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        /*过滤器中接受到header,转发给微服务*/
        // 得到request上下文
        RequestContext currentContext = RequestContext.getCurrentContext();
        // 得到request域
        HttpServletRequest httpServletRequest = currentContext.getRequest();
        if (httpServletRequest.getMethod().equals("OPTIONS")) {
            return null;
        }
        /*获取路径*/
        String reqURL = httpServletRequest.getRequestURL().toString();
        /*登录不需要判断*/
        if (reqURL.indexOf("login") > 0) {
            System.out.println("登陆页面");
            return null;
        }
        /*得到头信息*/
        String header = httpServletRequest.getHeader("Authorization");
        /*判断是否有头信息*/
        if (!StringUtils.isEmpty(header)) {
            if (header.startsWith("Bearer ")) {
                String token = header.substring(7);
                Claims claims = jwtUtil.parseJWT(token);
                if (claims != null) {
                    if ("admin".equals(claims.get("roles"))) {
                        // 把头信息继续往下传,并且放行
                        currentContext.addZuulRequestHeader("authorizations", token);
                        return null;
                    }
                }
            }
        }
        /*终止运行,令zuul过滤该请求,不对其进行路由*/
        currentContext.setSendZuulResponse(false);
        /*http状态码,返回错误码*/
        currentContext.setResponseStatusCode(401);
        /*返回信息*/
        currentContext.setResponseBody("无权访问");
        /*返回数据设置为UTF-8*/
        currentContext.getResponse().setContentType("text/html;charset=utf-8");
        return null;
    }
}
