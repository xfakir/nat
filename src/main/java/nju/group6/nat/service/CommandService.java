package nju.group6.nat.service;


import nju.group6.nat.util.IOUtil;
import org.apache.commons.net.io.Util;
import org.apache.commons.net.telnet.TelnetClient;


import nju.group6.nat.util.IpUtil;
import nju.group6.nat.util.TelnetUtil;
import org.apache.commons.net.io.Util;
import org.apache.commons.net.telnet.TelnetClient;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class CommandService {

//    private TelnetClient telnetClient = null;


    //enter conf termnal mode
    public void enter(){
        String command1 = "enable";
        String command2 = "configure terminal";
        try {
            TelnetUtil.sendCommand(command1);
            TelnetUtil.sendCommand(command2);
        } catch (IOException e) {
            System.out.println("enter failed");
            e.printStackTrace();
        }
    }

    public void exit(){
        String command = "exit";
        try {
            TelnetUtil.sendCommand(command);
        } catch (IOException e) {
            System.out.println("exit failed");
            e.printStackTrace();
        }
    }

    public void enterInterface(String interfaceName){
        String command = "interface "+interfaceName;
        try {
            TelnetUtil.sendCommand(command);
        } catch (IOException e) {
            System.out.println("enter interface failed");
            e.printStackTrace();
        }
    }
    //configure interface ip
    public void confInterfaceIP(String interfaceName,String ip, String netmask){
        enterInterface(interfaceName);
        try {
            String command1 = "ip address "+ip+" "+netmask;
            String command2 = "no shutdown";
            TelnetUtil.sendCommand(command1);
            TelnetUtil.sendCommand(command2);
        } catch (IOException e) {
            System.out.println("interface ip conf failed");
            e.printStackTrace();
        }
        exit();
    }

    //configure nat router interface inside or outside
    //already enter interface
    //0 : outside 1 : inside
    public void configureSide(String side){
//        enterInterface(outsideInterface);
        try {
            String command1 = "";
            if(side.equals("0")){
                command1 = "ip nat outside";
            }
            if(side.equals("1")){
                command1 = "ip nat inside";
            }
            TelnetUtil.sendCommand(command1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        exit();
    }

    public void natConfigure(String ip, String netmask, String hostsNumber){
        String startIp = IpUtil.getStartIp(ip, netmask, Integer.parseInt(hostsNumber)).get(0);
        String endIp = IpUtil.getStartIp(ip, netmask, Integer.parseInt(hostsNumber)).get(1);
        String command1 = "ip nat pool gloabl "+startIp+" "+endIp+" netmask "+netmask;
        String command2 = "access-list 1 permit 10.0.0.0 0.0.0.255";
        String command3 = "ip nat inside source list 1  pool gloabl overload";
        try {
            TelnetUtil.sendCommand(command1);
            TelnetUtil.sendCommand(command2);
            TelnetUtil.sendCommand(command3);
        } catch (IOException e) {
            System.out.println("nat config failed");
            e.printStackTrace();
        }
    }

    public boolean checkNetwork(String ip, String netmask, String hostsNumber){
        return IpUtil.IsIpv4(ip) && IpUtil.IsSubnetMask(netmask) && IpUtil.getHostsNumber(netmask) <= Integer.parseInt(hostsNumber);
    }


    public void sendUsername(String username) throws IOException, InterruptedException {
        System.out.println(username);
        TelnetUtil.sendCommand(username);
//        System.out.println(System.currentTimeMillis());
//        String result = TelnetUtil.receive();
//        System.out.println(System.currentTimeMillis());
//        System.out.println("===========receive==========");
//        System.out.println(result);
    }

    public void sendPassword(String password) throws IOException, InterruptedException {
        System.out.println(password);
        TelnetUtil.sendCommand(password);
//        System.out.println(System.currentTimeMillis());
//        String result = TelnetUtil.receive();
//        System.out.println(System.currentTimeMillis());
//        System.out.println("===========receive==========");
//        System.out.println(result);
    }


}
