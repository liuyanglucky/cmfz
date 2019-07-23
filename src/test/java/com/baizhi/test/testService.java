package com.baizhi.test;

import com.baizhi.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testService {
    @Autowired
    private UserService userService;
    @Test
    public void Test1(){
        Integer[] integers = userService.findOneYearUser(2019);
        for (Integer integer : integers) {
            System.out.println(integer);
        }
    }
}
