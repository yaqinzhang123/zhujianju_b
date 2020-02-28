package com.xyvideo.zhujianju.util.filter;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletResponse rep = (HttpServletResponse) response;
        rep.addHeader("Access-Control-Allow-Origin", "*");
        rep.addHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        rep.addHeader("Access-Control-Allow-Headers", "*");
        rep.addHeader("Access-Control-Max-Age", "3600");
        if("OPTIONS".equalsIgnoreCase(((HttpServletRequest)request).getMethod())){
            rep.setStatus(HttpServletResponse.SC_OK);
        }else{
            chain.doFilter(request,response);
        }
    }
    @Override
    public void destroy() {

    }
}