package nju.group6.nat.pojo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jerry
 * @create 2020-12-26-14:00
 */
public class   Router  {
    public static Router router0 ;
    public static Router router1 ;

    static {
        Map<String, RouterInterface> interfaceMap0 = new HashMap<String, RouterInterface>() ;
        interfaceMap0.put("f0/0",
                new RouterInterface("f0/0","10.0.0.1","YES","manual","up","up"));
        interfaceMap0.put("f0/1",
                new RouterInterface("f0/1","unassigned","YES","unset","down","down"));
        interfaceMap0.put("s0/0/0",
                new RouterInterface("s0/0/0","192.168.1.2","YES","manual","up","up"));
        interfaceMap0.put("s0/0/1",
                new RouterInterface("s0/0/1","unassigned","YES","unset","dow","down"));
        router0 = new Router("router0",interfaceMap0);
        Map<String, RouterInterface> interfaceMap1 = new HashMap<String, RouterInterface>() ;
        interfaceMap1.put("f0/0",
                new RouterInterface("f0/0","192.168.3.1","YES","manual","up","up"));
        interfaceMap1.put("f0/1",
                new RouterInterface("f0/1","unassigned","YES","unset","down","down"));
        interfaceMap1.put("s0/0/0",
                new RouterInterface("s0/0/0","192.168.1.1","YES","manual","up","up"));
        interfaceMap1.put("s0/0/1",
                new RouterInterface("s0/0/1","unassigned","YES","unset","dow","down"));
        router1 = new Router("router1",interfaceMap1);
    }


    private final String name;

    private Map<String,RouterInterface> interfaces;


    private Router(String name, Map<String,RouterInterface> interfaces) {
        this.name = name;
        this.interfaces = interfaces;
    }

    public String getName() {
        return name;
    }

    public Map<String, RouterInterface> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(Map<String, RouterInterface> interfaces) {
        this.interfaces = interfaces;
    }
}
