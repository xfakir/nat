package nju.group6.nat.pojo;

import java.util.Map;

/**
 * @author jerry
 * @create 2020-12-26-14:00
 */
public class PC  {
    public static PC pc1 = new PC("PC1","10.0.0.2","255.0.0.0","10.0.0.1");
    public static PC pc2 = new PC("PC2","10.0.0.3","255.0.0.0","10.0.0.1");
    public static PC pc3 = new PC("PC3","10.0.0.4","255.0.0.0","10.0.0.1");
    public static PC pc4 = new PC("PC4","192.168.3.2","255.255.255.0","192.168.3.1");

    public static Map<String, PC> pcMap = null;

    private String name;

    private String address;

    private String netmask;

    private String gateway;

    public PC() {
    }

    public PC(String name, String address, String netmask, String gateway) {
        this.name = name;
        this.address = address;
        this.netmask = netmask;
        this.gateway = gateway;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNetmask() {
        return netmask;
    }

    public void setNetmask(String netmask) {
        this.netmask = netmask;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }


}
