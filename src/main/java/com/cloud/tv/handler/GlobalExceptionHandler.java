package com.cloud.tv.handler;

import com.cloud.tv.core.utils.ResponseUtil;
import org.apache.shiro.session.ExpiredSessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpServerErrorException;

import java.io.FileNotFoundException;

/**
 * <p>
 *     Title: GlobalExceptionHandler.java
 * </p>
 *
 * <p>
 *     Description: 全局异常处理类, 增强的 Controller
 *     全局异常处理
 *     全局数据绑定
*      全局数据预处理
 * </p>
 *
 * <author>
 *     Hkk
 * </author>
 */
@ControllerAdvice
@Order(2)
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public Object badArgumentHandler(IllegalArgumentException e){
        logger.error(e.getMessage(),e);
        return ResponseUtil.badArgumentValue();
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public Object badArgumentHandler(NullPointerException e){
        logger.error(e.getMessage(),e);
        return ResponseUtil.nullPointException();
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Object ArithmeticException(ArithmeticException e){
        logger.error(e.getMessage(), e);
        return ResponseUtil.arithmeticException();
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object seriousHandler(Exception e) {
        logger.error(e.getMessage(), e);
        return ResponseUtil.serious();
    }

//    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//    public Object HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
//        logger.error(e.getMessage(), e);
//        return ResponseUtil.httpRequestMethodNotSupportedException();
//    }

    @ExceptionHandler(FileNotFoundException.class)
    public Object fileNotFoundException(FileNotFoundException e){
        logger.error(e.getMessage());
        return ResponseUtil.fileNotFoundException();
    }

    @ExceptionHandler(value = ExpiredSessionException.class)
    public Object handleExpiredSessionException(ExpiredSessionException e) {
        logger.debug("ExpiredSessionException", e);
        return ResponseUtil.expired();
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Object handleException(HttpRequestMethodNotSupportedException e) {
        return ResponseUtil.error("不支持' " + e.getMethod() + "'请求");
    }

    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public Object handleException(HttpMediaTypeNotSupportedException e) {
        MediaType contentType = e.getContentType();

        return ResponseUtil.error("不支持' " + contentType.getType() + "/" + contentType.getSubtype() + " 媒体类型");
    }

    @ExceptionHandler(value = HttpServerErrorException.class)
    @ResponseBody
    public Object httpServletErrorException(HttpServerErrorException e){
        System.out.println(e.getMessage());
        return ResponseUtil.badArgument("远程调用失败，检查参数是否正确");
    }

    @ExceptionHandler(value = NoSuchMethodError.class)
    @ResponseBody
    public Object NoSuchMethodError(NoSuchMethodError e){
        System.out.println(e.getMessage());
        return ResponseUtil.notFound();
    }


    // 捕捉shiro的异常
//    @ExceptionHandler(ShiroException.class)
//    public Object handleShiroException(ShiroException e) {
//        return ResponseUtil.badArgument(401, e.getMessage());
//    }
//
//    // 捕捉其他所有异常
//    @ExceptionHandler(Exception.class)
//    public Object globalException(HttpServletRequest request, Throwable ex) {
//        return ResponseUtil.badArgument(401, ex.getMessage());
//    }

}
