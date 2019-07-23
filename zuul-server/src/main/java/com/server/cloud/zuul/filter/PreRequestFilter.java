//package com.server.cloud.zuul.filter;
//
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//import com.netflix.zuul.exception.ZuulException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * @Description 测试前置过滤器
// * @Auther sea-hibn
// * @Date 2019-06-16
// */
//@Slf4j
//@Component
//public class PreRequestFilter extends ZuulFilter {
//    @Override
//    public String filterType() {
//        return FilterConstants.PRE_TYPE;
//    }
//
//    @Override
//    public int filterOrder() {
//        return FilterConstants.DEBUG_FILTER_ORDER;
//    }
//
//    /**
//     * 指定需要执行该Filter的规则
//     * 返回true则执行run()
//     * 返回false则不执行run()
//     */
//    @Override
//    public boolean shouldFilter() {
//        return true;
//    }
//
//    @Override
//    public Object run() throws ZuulException {
//        RequestContext ctx = RequestContext.getCurrentContext();
//        HttpServletRequest request = ctx.getRequest();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        log.info("send {} request to {}",request.getMethod(),request.getRequestURL().toString());
//        log.info("authentication name: {}",authentication.getName());
//        return null;
//    }
//}
