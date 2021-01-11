package nju.group6.nat.pojo;

/**
 * @author jerry
 * @create 2021-01-11-21:30
 */
public class RouteItem {
    private String type;

    private String dest;

    private String nextHop;

    @Override
    public String toString() {
        return "RouteItem{" +
                "type='" + type + '\'' +
                ", dest='" + dest + '\'' +
                ", nextHop='" + nextHop + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getNextHop() {
        return nextHop;
    }

    public void setNextHop(String nextHop) {
        this.nextHop = nextHop;
    }

    public RouteItem(String type, String dest, String nextHop) {
        this.type = type;
        this.dest = dest;
        this.nextHop = nextHop;
    }
}
