package nju.group6.nat.pojo;


import org.springframework.stereotype.Component;

/**
 * @author jerry
 * @create 2020-12-26-14:00
 */

public class RouterInterface {


    private final String name;

    private String address;

    //ok yes or no meaningless
    private String isOK;

    private String method;

    //on or off
    private String status;

    private String protocol;


    public  RouterInterface(String name, String address, String isOK, String method, String status, String protocol) {
        this.name = name;
        this.address = address;
        this.isOK = isOK;
        this.method = method;
        this.status = status;
        this.protocol = protocol;
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

    public String getIsOK() {
        return isOK;
    }

    public void setIsOK(String isOK) {
        this.isOK = isOK;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public String toString() {
        return "RouterInterface{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", isOK='" + isOK + '\'' +
                ", method='" + method + '\'' +
                ", status='" + status + '\'' +
                ", protocol='" + protocol + '\'' +
                '}';
    }
}
