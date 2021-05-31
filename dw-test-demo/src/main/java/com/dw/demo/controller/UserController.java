package com.dw.demo.controller;

import com.dw.demo.bean.UserInfo;
import com.dw.demo.entity.User;
import com.dw.demo.service.UserService;
import com.dw.demo.shiro.MyShiroRealm;
import com.dw.demo.util.common.MD5Utils;
import com.dw.demo.util.common.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户
 *
 * @version v1.0
 * @Author: zhanzhihong
 * @Date: 2021/3/1 16:01
 */
@RestController
@RequestMapping("/user")
@Api(tags = "user", description = "用户中心")
public class UserController {

    @Autowired
    MyShiroRealm myShiroRealm;

    @Autowired
    UserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "登录接口")
    public R login(@ApiParam(name = "username", value = "用户名") @RequestParam(value = "username") String username,
                      @ApiParam(name = "password", value = "密码") @RequestParam(value = "password") String password){
        Map<String, Serializable> map = new HashMap<>(16);
        Subject subject = SecurityUtils.getSubject();
        // 判断是否已经登录
        if (subject.getPrincipal() == null) {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            try {
                subject.login(token);
                map.put("token", subject.getSession().getId());
                map.put("msg", "登录成功");
            } catch (IncorrectCredentialsException e) {
                map.put("msg", "密码错误");
            } catch (LockedAccountException e) {
                map.put("msg", "登录失败，该用户已被冻结");
            } catch (AuthenticationException e) {
                map.put("msg", "该用户不存在");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return R.success(map);
    }

    @ApiOperation("insert")
    @RequiresPermissions(value={"insert"})
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/insert", method = {RequestMethod.GET, RequestMethod.POST})
    public R insert(@RequestBody User user){
        String password = MD5Utils.MD5Encode(user.getPassword(), MD5Utils.UTF_8);
        user.setPassword(password);
        int i = userService.saveNotNull(user);
        if (i==0){
            return R.fail("失败");
        }
        return R.success("成功");
    }

    @ApiOperation("查询所有")
    @RequestMapping(value = "/findAll", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions(value={"update"})
    public R<UserInfo> findAll(){

        return R.success("测试权限呀");
    }

    @ApiOperation("更新")
    @RequiresPermissions(value={"update"})
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/update", method = {RequestMethod.GET, RequestMethod.POST})
    public R<String> update(@RequestBody User user){
        String password = MD5Utils.MD5Encode(user.getPassword(), MD5Utils.UTF_8);
        user.setPassword(password);
        int i = userService.updateNotNull(user);
        if (i==0){
            return R.fail("失败");
        }
        return R.success("成功");
    }

    /**
     * 注销接口
     *
     * @return success or fail
     */
    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "注销接口")
    public R logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return R.success();
    }

    /**
     * 未登陆接口
     *
     * @return success or fail
     */
    @GetMapping("/unlogin")
    @ApiOperation(value = "未登陆接口")
    public R unlogin() {
        //只要访问了接口,就代表登录了,因为我们的接口,只要登陆了才能访问到
        //所以,如果访问接口的时候,一段时间后不能访问了,那么就是登陆超时
        return R.unauthentication("登陆超时,请重新登陆", null);
    }


}