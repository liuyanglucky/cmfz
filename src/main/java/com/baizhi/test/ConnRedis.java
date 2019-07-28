package com.baizhi.test;

import redis.clients.jedis.Jedis;

public class ConnRedis {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.171.129", 6379);
        jedis.set("a", "s");
        System.out.println(jedis);
    }
}
