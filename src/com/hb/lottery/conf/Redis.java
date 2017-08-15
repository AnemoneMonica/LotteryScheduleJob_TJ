package com.hb.lottery.conf;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Redis {

    private static String addr = "";
    private static int port = 6379;
    private static String AUTH = "admin";
    private static int MAX_ACTIVE = 1024;
    private static int MAX_IDLE = 200;
    private static String MAX_WAIT = "10000";
    private static int TIMEOUT = 10000;
    private static boolean TEST_ON_BORROW = true;
    private static JedisPool jedisPool;
    private static Redis redis = new Redis();

    public Redis() {
       initParam();
       initRedisPool();

    }

    public void initParam() {
        addr = Configuration.getGlobalMsg("redis.addr");
        port = Integer.parseInt(Configuration.getGlobalMsg("redis.port"));
        AUTH = Configuration.getGlobalMsg("redis.auth");
        MAX_ACTIVE = Integer.parseInt(Configuration.getGlobalMsg("redis.max_active"));
        MAX_IDLE = Integer.parseInt(Configuration.getGlobalMsg("redis.max_idle"));
        MAX_WAIT = Configuration.getGlobalMsg("redis.max_wait");
        TIMEOUT = Integer.parseInt(Configuration.getGlobalMsg("redis.timeout"));
    }

    public void initRedisPool() {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxActive(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWait(Long.parseLong(MAX_WAIT));
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config, addr, port, TIMEOUT, AUTH);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public synchronized Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }
    public static Redis getInstance() {
        if (redis == null) {
            redis = new Redis();
        }
        return redis;
    }
}


