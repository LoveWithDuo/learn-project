package com.dw.demo.util;

import com.dw.demo.entity.User;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.util.UUID;

/**
 *
 * @Author: zhanzhihong
 * @Date: 2021/3/1 15:56
 * @version v1.0
 */
public class PasswordUtils {
    private static final String ALGORITHM_NAME = "MD5";
    private static final Integer HASH_ITERATIONS = 1024;

    public static void entryptPassword(User user) {
        String salt = UUID.randomUUID().toString();
        String temPassword = user.getPassword();
        Object md5Password = new SimpleHash(ALGORITHM_NAME, temPassword, ByteSource.Util.bytes(salt), HASH_ITERATIONS);
//        user.setSalt(salt);
        user.setPassword(md5Password.toString());
    }
}