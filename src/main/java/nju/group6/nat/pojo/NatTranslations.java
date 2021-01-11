package nju.group6.nat.pojo;

/**
 * @author jerry
 * @create 2021-01-07-19:55
 * nat转换表条目对象
 */
public class NatTranslations {
    private String protocol;

    private String localAddress;

    private String globalAddress;

    public NatTranslations(String protocol, String localAddress, String globalAddress) {
        this.protocol = protocol;
        this.localAddress = localAddress;
        this.globalAddress = globalAddress;
    }

    public String getProtocal() {
        return protocol;
    }

    public void setProtocal(String protocal) {
        this.protocol = protocal;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }

    public String getGlobalAddress() {
        return globalAddress;
    }

    public void setGlobalAddress(String globalAddress) {
        this.globalAddress = globalAddress;
    }

    @Override
    public String toString() {
        return "NatTranslations{" +
                "protocol='" + protocol + '\'' +
                ", localAddress='" + localAddress + '\'' +
                ", globalAddress='" + globalAddress + '\'' +
                '}';
    }
}
