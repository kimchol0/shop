package com.itheima.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/*
* JedisPool工具类
*   加载配置文件，配置连接池参数
*   提供获取连接的方法
*/
public class JedisPoolUtils {
	 private static JedisPool jedisPool;
	    static {
	        //读取配置文件
	        InputStream is=JedisPoolUtils.class.getClassLoader().getResourceAsStream("jedis.properties");
	        //创建Propertie对象
	        Properties properties=new Properties();
	        try {
	            properties.load(is);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        JedisPoolConfig config=new JedisPoolConfig();
	        config.setMaxTotal(Integer.parseInt(properties.getProperty("redis.maxTotal")));
	        config.setMaxIdle(Integer.parseInt(properties.getProperty("redis.maxIdle")));

	        //初始化Jedispool
	        jedisPool=new JedisPool(config,properties.getProperty("redis.url"), Integer.parseInt(properties.getProperty("redis.port")));


	    }
	    /*获取连接的方法*/
	    public static Jedis getJedis(){
	        return jedisPool.getResource();
	    }
}
