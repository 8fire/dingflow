package com.agee.framework.handler;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.dev33.satoken.exception.SaTokenException;
import com.agee.common.core.domain.R;
import com.agee.common.enums.ResponseCodeEnum;
import com.agee.common.exception.DemoModeException;
import com.agee.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @program: df
 * @description 全局异常处理器
 * @author: 没用的阿吉
 * @create: 2022-09-12 10:57
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NotLoginException.class)
    public R<?> handlerNotLoginException(NotLoginException nle) {
        log.error(nle.getMessage(), nle);
        // 判断场景值，定制化异常信息
        if(nle.getType().equals(NotLoginException.NOT_TOKEN)) {
            return R.fail(ResponseCodeEnum.NO_TOKEN_ERROR);
        } else if(nle.getType().equals(NotLoginException.INVALID_TOKEN)) {
            return R.fail(ResponseCodeEnum.INVALID_TOKEN_ERROR);
        } else if(nle.getType().equals(NotLoginException.TOKEN_TIMEOUT)) {
            return R.fail(ResponseCodeEnum.EXPIRE_TOKEN_ERROR);
        } else if(nle.getType().equals(NotLoginException.BE_REPLACED)) {
            return R.fail(ResponseCodeEnum.BE_REPLACED_ERROR);
        } else if(nle.getType().equals(NotLoginException.KICK_OUT)) {
            return R.fail(ResponseCodeEnum.KICK_OUT_ERROR);
        } else {
            return R.fail(ResponseCodeEnum.NO_LOGIN_ERROR);
        }
    }

    /**
     * 权限校验失败
     */
    @ExceptionHandler({NotPermissionException.class, NotRoleException.class})
    public R<?> handleAuthorizationException(SaTokenException e) {
        log.error(e.getMessage(), e);
        return R.fail(ResponseCodeEnum.ACCOUNT_NOT_AUTH_ERROR);

    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
    public R<?> handleException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        return R.fail("不支持' " + e.getMethod() + "'请求");
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public R<?> notFount(RuntimeException e) {
        log.error("运行时异常:", e);
        return R.fail("运行时异常:" + e.getMessage());
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public R<?> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return R.fail(ResponseCodeEnum.SERVER_FAIL);
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public R<?> businessException(ServiceException e) {
        log.error(e.getMessage(), e);
        return R.fail(e.getCode(),e.getMessage());
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public R<?> validatedBindException(BindException e) {
        log.error(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return R.fail(message);
    }

    /**
     * 演示模式异常
     */
    @ExceptionHandler(DemoModeException.class)
    public R<?> demoModeException(DemoModeException e) {
        return R.fail("演示模式，不允许操作");
    }
}
