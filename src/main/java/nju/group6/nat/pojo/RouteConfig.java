package nju.group6.nat.pojo;

/**
 * @author jerry
 * @create 2021-01-07-19:26
 */
public class RouteConfig {
    private String network;
    private String mask;
    private String nextHop;

    public RouteConfig(String network, String mask, String nextHop) {
        this.network = network;
        this.mask = mask;
        this.nextHop = nextHop;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getNextHop() {
        return nextHop;
    }

    public void setNextHop(String nextHop) {
        this.nextHop = nextHop;
    }
}
