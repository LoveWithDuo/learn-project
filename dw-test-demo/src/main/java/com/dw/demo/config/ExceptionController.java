package com.dw.demo.config;

import com.dw.demo.util.common.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @Author: zhanzhihong
 * @Date: 2021/3/1 16:13
 * @version v1.0
 */
@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity HttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.OK).body(R.fail("数据异常,请输入正确的数字"));
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity exceptionHandler(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        assert fieldError != null;
        log.error(fieldError.getField() + ":" + fieldError.getDefaultMessage());
        return ResponseEntity.status(HttpStatus.OK).body(R.fail(fieldError.getDefaultMessage()));
    }

    @ExceptionHandler(value = BindException.class)
    public ResponseEntity BindException(BindException ex) {
        Map<String, String> messages = new HashMap<>();
        BindingResult result = ex.getBindingResult();
        if (result.hasErrors()) {
            transferErrorMessage(messages, result);
        }
        System.out.println(messages);
        String s = messages.toString();
        System.out.println(s);
        return ResponseEntity.status(HttpStatus.OK).body(R.fail(s, messages));
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e) {
        return ResponseEntity.status(HttpStatus.OK).body(R.fail("请求方式不支持", e.getMessage()));
    }

    /**
     * 自己的自定义异常 暂时用不到,没什么用.
     *
     * @param ex 错误信息
     * @return null
     */
    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity bizExceptionHandler(NullPointerException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.OK).body(R.fail("数据异常,请联系管理员", ex.getMessage()));
    }

    /**
     * 捕捉sql异常,防止数据泄露 :ｓｑｌＥｘｃｅｐｔｉｏｎ
     * 查询相关
     *
     * @param ex 错误信息
     * @return 服务异常
     */
    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public ResponseEntity DataAccessException(DataAccessException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.OK).body(R.fail("服务异常"));
    }

    /**
     * 捕捉sql异常,防止数据泄露
     * 没发现相关字段相关
     *
     * @param ex 错误信息
     * @return 服务异常
     */
    @ExceptionHandler(value = BadSqlGrammarException.class)
    public ResponseEntity BadSqlGrammarException(BadSqlGrammarException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.OK).body(R.fail("参数缺失错误: 未发现相关字段.请检查"));
    }


    /**
     * 捕捉sql异常,防止数据泄露
     * 数据长度不够相关
     *
     * @param ex 错误信息
     * @return 服务异常
     */
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity DataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.OK).body(R.fail("参数格式错误: 参数过长,请重新设置"));
    }


    /**
     * 对运行异常进行返回: 现在不能用,使用之后,所有的会优先走这个
     *
     * @return 服务异常
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity RuntimeException(RuntimeException ex) {
        log.info(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.OK).body(R.fail(ex.getMessage()));
    }

    /**
     * 数据格式错误
     *
     * @author huangJin
     * @date 2020/4/2
     */
    @ExceptionHandler(value = NumberFormatException.class)
    public ResponseEntity NumberFormatException(NumberFormatException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.OK).body(R.fail("数据类型转化异常"));
    }

    /**
     * 数据格式错误
     *
     * @author huangJin
     * @date 2020/4/2
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity illegalArgumentException(IllegalArgumentException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.OK).body(R.fail("数据不全,请正确操作"));
    }


    /**
     * 权限认证
     *
     * @author huangJin
     * @date 2020/3/5
     */
    @ExceptionHandler(value = UnknownAccountException.class)
    public ResponseEntity unknownAccountException(UnknownAccountException e) {
        log.info(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.OK).body(R.fail("用户名错误", null));
    }


    /**
     * 权限认证
     *
     * @author huangJin
     * @date 2020/3/5
     */
    @ExceptionHandler(value = IncorrectCredentialsException.class)
    public ResponseEntity incorrectCredentialsException(IncorrectCredentialsException e) {
        log.info(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.OK).body(R.fail("密码错误", null));
    }

    /**
     * 权限认证
     *
     * @author huangJin
     * @date 2020/3/5
     */
    @ExceptionHandler(value = AuthorizationException.class)
    public ResponseEntity AuthorizationException(AuthorizationException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.OK).body(R.unauthentication("未登录或登陆超时,请重新登录", null));
    }


    @ExceptionHandler(value = UnauthorizedException.class)
    public ResponseEntity UnauthorizedException(UnauthorizedException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.OK).body(R.unauthorized("权限不足,请分配后重试", null));
    }


    @ExceptionHandler(value = UnauthenticatedException.class)
    public ResponseEntity UnauthenticatedException(UnauthenticatedException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.OK).body(R.unauthorized("登陆超时,请重新登陆", null));
    }

    private void transferErrorMessage(Map<String, String> messages, BindingResult result) {
        List<ObjectError> errors = result.getAllErrors();
        for (ObjectError error : errors) {
            FieldError fieldError = (FieldError) error;
            messages.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        log.error(messages.toString());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity Exception(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(R.fail("服务异常"));
    }

}