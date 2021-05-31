package com.dw.demo.config;

import com.dw.demo.shiro.MySessionManager;
import com.dw.demo.shiro.MyShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
//    @Value("${spring.redis.timeout}")
    private int timeout = 20000;
    @Value("${spring.redis.password}")
    private String password;

    //shiro拦截接口
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //自定义拦截器
//        //自定义拦截器
        Map<String, Filter> filtersMap = new LinkedHashMap();
        filtersMap.put("authc", new redirect());
        shiroFilterFactoryBean.setFilters(filtersMap);
        Map<String, String> filterMap = new LinkedHashMap<>();
//        filterMap.put("/redis/**", "anon");
//        filterMap.put("/user/login", "anon");
//        filterMap.put("/user/logout", "anon");
        //全拦截
//        filterMap.put("/**", "authc");
        // 未登录重新定位到登陆得界面: 前后端分离的项目,不能重定向
        //配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
//        shiroFilterFactoryBean.setLoginUrl("/unauth");
//        shiroFilterFactoryBean.setLoginUrl("/admin/unlogin");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }
    /**
     * 凭证匹配器（由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了）
     * create by: leigq
     * create time: 2019/7/3 14:30
     *
     * @return HashedCredentialsMatcher
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        // 散列的次数，比如散列两次，相当于 md5(md5(""));
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

    /**
     * 将自己的验证方式加入容器
     * create by: leigq
     * create time: 2019/7/3 14:30
     *
     * @return MyShiroRealm
     */
    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
//        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis, 使用的是shiro-redis开源插件
     * create by: leigq
     * create time: 2019/7/3 14:30
     *
     * @return RedisSessionDAO
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        redisSessionDAO.setSessionIdGenerator(sessionIdGenerator());
        redisSessionDAO.setExpire(1800);
        return redisSessionDAO;
    }

    /**
     * Session ID 生成器
     * <br/>
     * create by: leigq
     * <br/>
     * create time: 2019/7/3 16:08
     *
     * @return JavaUuidSessionIdGenerator
     */
    @Bean
    public JavaUuidSessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }

    /**
     * 自定义sessionManager
     * create by: leigq
     * create time: 2019/7/3 14:31
     *
     * @return SessionManager
     */
    @Bean
    public SessionManager sessionManager() {
        MySessionManager mySessionManager = new MySessionManager();
        mySessionManager.setSessionIdCookieEnabled(true);
        mySessionManager.setSessionIdUrlRewritingEnabled(false);
        //先设置个5个小时超时
        mySessionManager.setGlobalSessionTimeout(18000000);
        mySessionManager.setSessionDAO(redisSessionDAO());
        return mySessionManager;
    }

    /**
     * 配置shiro redisManager, 使用的是shiro-redis开源插件
     * <br/>
     * create by: leigq
     * <br/>
     * create time: 2019/7/3 14:33
     *
     * @return RedisManager
     */
    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setTimeout(timeout);
        redisManager.setPassword(password);
        return redisManager;
    }

    /**
     * cacheManager 缓存 redis实现, 使用的是shiro-redis开源插件
     * <br/>
     * create by: leigq
     * <br/>
     * create time: 2019/7/3 14:33
     *
     * @return RedisCacheManager
     */
    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        // 必须要设置主键名称，shiro-redis 插件用过这个缓存用户信息
        redisCacheManager.setPrincipalIdFieldName("phone");
        return redisCacheManager;
    }

    /**
     * create by: leigq
     * description: 权限管理，配置主要是Realm的管理认证
     * create time: 2019/7/1 10:09
     *
     * @return SecurityManager
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //报错？
        ThreadContext.bind(securityManager);
        securityManager.setRealm(myShiroRealm());
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());
        // 自定义缓存实现 使用redis
        securityManager.setCacheManager(cacheManager());
        return securityManager;
    }

    /*
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

//    @Bean
//    public SimpleCookie cookie() {
//        // cookie的name,对应的默认是 JSESSIONID
//        SimpleCookie cookie = new SimpleCookie("SHARE_JSESSIONID");
//        cookie.setHttpOnly(true);
//        //  path为 / 用于多个系统共享 JSESSIONID
//        cookie.setPath("/");
//        return cookie;
//    }

    /* 此项目使用 shiro 场景为前后端分离项目，这里先注释掉，统一异常处理已在 GlobalExceptionHand.java 中实现 */
    /**
     * create by: leigq
     * description: 异常处理, 详见：https://www.cnblogs.com/libra0920/p/6289848.html
     * create time: 2019/7/1 10:28
     * @return SimpleMappingExceptionResolver
     */
//    @Bean(name = "simpleMappingExceptionResolver")
//    public SimpleMappingExceptionResolver createSimpleMappingExceptionResolver() {
//        SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();
//        Properties mappings = new Properties();
//        mappings.setProperty("DatabaseException", "databaseError");//数据库异常处理
//        mappings.setProperty("UnauthorizedException", "/user/403");
//        r.setExceptionMappings(mappings);  // None by default
//        r.setDefaultErrorView("error");    // No default
//        r.setExceptionAttribute("exception");     // Default is "exception"
//        //r.setWarnLogCategory("example.MvcLogger");     // No default
//        return r;
//    }

//    /**
//     * 注册全局异常处理
//     * @return
//     */
//    @Bean(name = "exceptionHandler")
//    public HandlerExceptionResolver handlerExceptionResolver() {
//        return new MyExceptionHandler();
//    }

}