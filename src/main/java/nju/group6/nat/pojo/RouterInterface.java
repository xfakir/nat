package nju.group6.nat.pojo;


import org.springframework.stereotype.Component;

/**
 * @author jerry
 * @create 2020-12-26-14:00
 */

public class RouterInterface {


    private String name;

    private String address;

    private String netmask;

    //on or off
    private String state;


    public RouterInterface() {

    }

    public  RouterInterface(String name, String address,String netmask, String state) {
        this.name = name;
        this.address = address;
        this.state = state;
        this.netmask = netmask;
    }

    public String getName() {
        return name;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNetmask() {
        return netmask;
    }

    public void setNetmask(String netmask) {
        this.netmask = netmask;
    }

    @Override
    public String toString() {
        return "RouterInterface{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", status='" + state + '\'' +
                '}';
    }
}
