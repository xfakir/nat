package nju.group6.nat.pojo;

import java.util.Objects;

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

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocal) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NatTranslations that = (NatTranslations) o;
        return Objects.equals(protocol, that.protocol) &&
                Objects.equals(localAddress, that.localAddress) &&
                Objects.equals(globalAddress, that.globalAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocol, localAddress, globalAddress);
    }
}
