package com.baizhi.aop;

import com.alibaba.fastjson.JSONObject;
import com.baizhi.redisCache.RedisCache;
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

@Configuration
@Aspect
public class RedisCacheHash {

    @Autowired
    private Jedis jedis;

    @Around("execution(* com.baizhi.service.*.find*(..))")
    public Object cache(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        1.获取将要执行的方法
//        2.判断将要执行的方法上是否含有自定义的缓存注解
//        3.如果含有注解，先去缓存中拿，如果缓存中有直接返回，如果没有查询数据库，并添加到缓存中
//        4.如果没有注解，直接方法放行
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        String methodName = method.getName();
        StringBuilder builder = new StringBuilder();
        builder.append(methodName).append(":");

        String className = proceedingJoinPoint.getTarget().getClass().getName();

        Object[] args = proceedingJoinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            builder.append(args[i].toString());
            if (i == args.length - 1) {
                break;
            }
            builder.append(",");
        }
        String key = builder.toString();

//        判断要执行的方法上是否含有自定义的注解
        boolean b = method.isAnnotationPresent(RedisCache.class);
        if (b) {
//            有注解
            if (this.jedis.hexists(className, key)) {//判断是否对将要执行的方法保存有缓存
                String s = this.jedis.hget(className, key);
                Object result = JSONObject.parse(s);
                return result;
            } else {
                Object s = proceedingJoinPoint.proceed();
                this.jedis.hset(className, key, JSONObject.toJSONString(s));
                return s;
            }
        } else {
//            没有注解
            Object result = proceedingJoinPoint.proceed();
            return result;
        }
    }


    @After("execution(* com.baizhi.service.*.*(..)) && !execution(* com.baizhi.service.*.find*(..)) && !execution(* com.baizhi.service.*.count*(..))")
    public void after(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getName();
        jedis.del(className);
    }


}
