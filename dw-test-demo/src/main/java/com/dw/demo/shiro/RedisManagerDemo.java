package com.dw.demo.shiro;

import lombok.Data;
import org.crazycake.shiro.IRedisManager;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@Component
public class RedisManagerDemo implements IRedisManager {
    /**
     * Expire time in seconds.
     */
    protected static final int DEFAULT_EXPIRE = -1;

    /**
     * Default value of count.
     */
    protected static final int DEFAULT_COUNT = 100;

    /**
     * The number of elements returned at every iteration.
     */
    private int count = DEFAULT_COUNT;

    private LettuceConnectionFactory lettuceConnectionFactory;

    private RedisConnection getConnection() {
        return this.lettuceConnectionFactory.getConnection();
    }

    @Override
    public byte[] get(byte[] key) {
        if (key == null) {
            return null;
        }
        byte[] value = null;
        RedisConnection connection = getConnection();
        try {
            value = connection.get(key);
        } finally {
            connection.close();
        }
        return value;
    }

    @Override
    public byte[] set(byte[] key, byte[] value, int expire) {
        if (key == null) {
            return null;
        }
        RedisConnection connection = getConnection();
        try {
            connection.set(key, value);
            if (expire >= 0) {
                connection.expire(key, expire);
            }
        } finally {
            connection.close();
        }
        return value;
    }

    @Override
    public void del(byte[] key) {
        if (key == null) {
            return;
        }
        RedisConnection connection = getConnection();
        try {
            connection.del(key);
        } finally {
            connection.close();
        }
    }

    @Override
    public Long dbSize(byte[] pattern) {
        long dbSize = 0L;
        RedisConnection connection = getConnection();
        try {
            ScanOptions options = ScanOptions.scanOptions()
                    .count(count)
                    .match(new String(pattern))
                    .build();
            Cursor<byte[]> scanResult;
            scanResult = connection.scan(options);
            while (scanResult.hasNext()) {
                dbSize++;
            }
        } finally {
            connection.close();
        }
        return dbSize;
    }

    @Override
    public Set<byte[]> keys(byte[] pattern) {
        Set<byte[]> keys = new HashSet<byte[]>();
        RedisConnection connection = getConnection();

        try {
            ScanOptions options = ScanOptions.scanOptions()
                    .count(count)
                    .match(new String(pattern))
                    .build();
            Cursor<byte[]> scanResult;
            scanResult = connection.scan(options);
            List<byte[]> bytes = new ArrayList<>();
            while (scanResult.hasNext()) {
                bytes.add(scanResult.next());
            }
            keys.addAll(bytes);
        } finally {
            connection.close();
        }
        return keys;
    }
}
