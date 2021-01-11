package nju.group6.nat.pojo;

import java.util.List;

public class NatConfig {
    private String network;
    private String total;
    private String netmask;
    private List<String> inside;
    private List<String> outside;

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getNetmask() {
        return netmask;
    }

    public void setNetmask(String netmask) {
        this.netmask = netmask;
    }

    public List<String> getInside() {
        return inside;
    }

    public void setInside(List<String> inside) {
        this.inside = inside;
    }

    public List<String> getOutside() {
        return outside;
    }

    public void setOutside(List<String> outside) {
        this.outside = outside;
    }
}
