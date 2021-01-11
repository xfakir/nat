package nju.group6.nat;


import nju.group6.nat.pojo.RouterInterface;
import nju.group6.nat.service.CommandService;
import nju.group6.nat.util.TelnetUtil;
import org.apache.commons.net.io.Util;
import org.apache.commons.net.telnet.TelnetClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
class NatApplicationTests {
    @Autowired
    CommandService commandService ;

    @Test
    void contextLoads() {
        List<RouterInterface> list = new LinkedList<>();

        RouterInterface r1 = new RouterInterface("f0/0","10.0.0.1","10.0.0.0","1");
        RouterInterface r2 = new RouterInterface("f0/1","10.0.0.1","10.0.0.0","0");
        RouterInterface r3 = new RouterInterface("s0/0/0/0","10.0.0.1","10.0.0.0","1");
        RouterInterface r4 = new RouterInterface("s0/0/0/1","10.0.0.1","10.0.0.0","0");
        list.add(r1);
        list.add(r4);
        list.add(r3);
        list.add(r2);
        list.sort(new Comparator<RouterInterface>() {
            @Override
            public int compare(RouterInterface o1, RouterInterface o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        System.out.println(list.toString());
    }


    @Test
    public void test() throws Exception {
//        TelnetUtil.connect("10.0.0.1", "23");
//        TelnetUtil.read("Password:");
//        TelnetUtil.sendCommand("CISCO");
//        TelnetUtil.read(">");
//        commandService.enable();
//        TelnetUtil.read("Password:");
//        TelnetUtil.sendCommand("CISCO");
//        TelnetUtil.read("#");
//       Map<String, RouterInterface> map =  commandService.getRouterInterface();
//        for(Map.Entry<String, RouterInterface> entry : map.entrySet()){
//            System.out.println(entry.getKey()+" "+entry.getValue().toString());
//        }
//        commandService.configTerminal();
//        TelnetUtil.read("#");
//        commandService.enterInterface("f0/1");
//        TelnetUtil.read("#");
//        commandService.confInterfaceIP("f0/1","10.0.0.3","255.0.0.0","1" );
//          TelnetUtil.connect("192.168.3.2","23");
//        TelnetUtil.read(":");
//        TelnetUtil.sendCommand("fgg");
//        TelnetUtil.read("password:");
//        TelnetUtil.sendCommand("123");
//        TelnetUtil.read("fgg>");
        //Util.copyStream(TelnetUtil.getInputStream(), System.out);
        TelnetUtil.connect("10.0.0.1", "23");
        TelnetUtil.read("Password:");
        TelnetUtil.sendCommand("CISCO");
        TelnetUtil.read(">");
        TelnetUtil.sendCommand("enable");
        TelnetUtil.read("Password:");
        TelnetUtil.sendCommand("CISCO");
//        TelnetUtil.sendCommand("config ter");
        TelnetUtil.read("#");


        TelnetUtil.sendCommand("show ip nat translations");
        TelnetUtil.sendCommand("k");
        TelnetUtil.read("#");
    }

}
