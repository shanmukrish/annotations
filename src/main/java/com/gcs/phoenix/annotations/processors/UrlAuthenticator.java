package com.gcs.phoenix.annotations.processors;

import com.google.common.collect.Lists;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;


public class UrlAuthenticator implements HandlerInterceptor {
    List<String> allowedURLs= Arrays.asList("/login","/swagger", "/swagger-resources/configuration/ui","/swagger-resources","/v2/api-docs","/swagger-resources/configuration/security");


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

       System.out.println("URL hit: "+ httpServletRequest.getRequestURI()+": "+allowedURLs.contains(httpServletRequest.getRequestURI()));

       boolean isAllowed=allowedURLs.contains(httpServletRequest.getRequestURI());
       if(!isAllowed){
           httpServletResponse.setStatus(403);
       }
        return allowedURLs.contains(httpServletRequest.getRequestURI());
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

        if(!allowedURLs.contains(httpServletRequest.getRequestURI())){
            httpServletResponse.setStatus(403);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
