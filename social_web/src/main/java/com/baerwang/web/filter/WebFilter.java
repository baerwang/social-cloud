package com.baerwang.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author baerwang
 * @version 1.0
 * @date 2020/2/24 14:16
 */
@Component
public class WebFilter extends ZuulFilter {

    @Override
    public String filterType() {
        /*前置过滤器*/
        return "pre";
    }

    @Override
    public int filterOrder() {
        /*优先级为0,数字越大,优先级越低*/
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        /*是否执行过滤器,此处为true,说明需要过滤*/
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        /*过滤器中接受到header,转发给微服务*/
        // 得到request上下文
        RequestContext currentContext = RequestContext.getCurrentContext();
        // 得到request域
        HttpServletRequest request = currentContext.getRequest();
        // 得到头信息
        String header = request.getHeader("Authorization");
        // 判断是否有头信息
        if (!StringUtils.isEmpty(header)) {
            // 把头信息继续往下传
            currentContext.addZuulRequestHeader("authorization", header);
        }
        return null;
    }
}
