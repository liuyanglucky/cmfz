package com.baizhi.aop;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Method;
import java.util.Set;

//为实现向redis中缓存查询数据，创建一个通知类
//利用aop思想的环绕型
//@Configuration
//@Aspect
public class RedisCache {
    @Autowired
    private Jedis jedis;

    //因为利用环绕型思想，使用@around注解
    //* com.baizhi.service.*.find*(..)
    //第一个*为返回值类型  第二个*为com.baizhi.service下的所有类
    //第三个*为com.baizhi.service下的所有类下以find开头的所有方法
    @Around("execution(* com.baizhi.service.*.find*(..))")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        /**
         * 基本思路：
         * 1.获取将要执行的方法
         * 2.判断将要执行的方法上是否含有自定义的缓存注解
         * 3.如果含有注解，先去缓存中拿，如果缓存中有直接返回，如果没有查询数据库，并添加到缓存中
         * 4.如果没有注解，直接方法放行
         */
        //获取执行的方法
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        //判断该方法上是否有自定义注解
        boolean b = method.isAnnotationPresent(com.baizhi.redisCache.RedisCache.class);
        if (b) {
//            当前要执行的方法上含有该注解
//            判断redis中是否含有某个key
//            如果含有的话，直接拿缓存
//            如果不含有的话，插数据库，然后放入到缓存中一份
            //获取redis存储的key    存放在StringBuilder中
            StringBuilder builder = new StringBuilder();
            //类的全限定名：例 com.baizhi.aop.RedisCache
            String className = proceedingJoinPoint.getTarget().getClass().getName();
            builder.append(className).append(".");
            //获取方法的名称
            String methodName = method.getName();
            builder.append(methodName).append(":");
            //获取实参
            Object[] args = proceedingJoinPoint.getArgs();
            //遍历此Object[]得到每一个args
            for (int i = 0; i < args.length; i++) {
                builder.append(args[i].toString());
                //在String类型的字符串拼接时，如果到最后一个直接退出，不拼接“,”
                if (i == args.length - 1) {
                    break;
                }
                //不是最后一个，在后面添加","
                builder.append(",");
            }
            //将builder转换成String类型
            String key = builder.toString();
            if (jedis.exists(key)) {
                //如果redis中含有这个key
                String s = jedis.get(key);
                Object result = JSONObject.parse(s);
                return result;
            } else {
                ////如果redis中没有这个key
                Object result = proceedingJoinPoint.proceed();
                //放入到redis中
                jedis.set(key, JSONObject.toJSONString(result));
                return result;
            }
        } else {
            //当前要执行的方法上不含有该注解
            Object result = proceedingJoinPoint.proceed();
            return result;
        }
    }

    @After("execution(* com.baizhi.service.*.*(..)) && !execution(* com.baizhi.service.*.find*(..)) && !execution(* com.baizhi.service.*.count*(..))")
    public void after(JoinPoint joinPoint) {
//        获取将要执行的方法     banner add edit del
        String className = joinPoint.getTarget().getClass().getName();
        System.out.println(className);
//        删除相关的缓存       banner
        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            if (key.startsWith(className)) {
                jedis.del(key);
            }
        }
    }
}
