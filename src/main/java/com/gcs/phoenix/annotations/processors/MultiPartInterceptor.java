package com.gcs.phoenix.annotations.processors;

import com.gcs.phoenix.annotations.core.FileCopy;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

@MultipartConfig
@Component
public class MultiPartInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        HandlerMethod handlerMethod= (HandlerMethod) o;
        Method method= handlerMethod.getMethod();

       if( method.isAnnotationPresent(FileCopy.class))
       {
           String localPath="";
           FileCopy localFileCopy=method.getAnnotation(FileCopy.class);
           if(!localFileCopy.path().isEmpty()){
               localPath="";
           }

           Part filePart = httpServletRequest.getPart("file"); // Retrieves <input type="file" name="file">
           String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
           System.out.println("Interceptor Called: "+fileName);
           InputStream fileContent = filePart.getInputStream();
           Files.copy(fileContent, Paths.get(""));

       }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
