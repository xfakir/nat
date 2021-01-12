package nju.group6.nat.pojo;

/**
 * @author jerry
 * @create 2021-01-11-21:30
 */
public class RouteItem {
    private String type;

    private String destination;

    private String nextHop;

    @Override
    public String toString() {
        return "RouteItem{" +
                "type='" + type + '\'' +
                ", destination='" + destination + '\'' +
                ", nextHop='" + nextHop + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getNextHop() {
        return nextHop;
    }

    public void setNextHop(String nextHop) {
        this.nextHop = nextHop;
    }

    public RouteItem(String type, String destination, String nextHop) {
        this.type = type;
        this.destination = destination;
        this.nextHop = nextHop;
    }
}
