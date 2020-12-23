package nju.group6.nat;


import nju.group6.nat.util.IpUtil;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NatApplicationTests {

    @Test
    void contextLoads() {
    }


    @Test
    public void testGetHoststNumbers(){
        String mask = "255.255.255.0";
        System.out.println(IpUtil.getHostsNumber(mask));
    }

}
