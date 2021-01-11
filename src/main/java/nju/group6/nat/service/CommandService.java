package nju.group6.nat.service;


import nju.group6.nat.pojo.NatConfig;
import nju.group6.nat.pojo.NatTranslations;
import nju.group6.nat.pojo.RouterInterface;
import nju.group6.nat.util.IpUtil;
import nju.group6.nat.util.TelnetUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class CommandService {


    public void enable(){
        String command = "enable";
        try {
            TelnetUtil.sendCommand(command);
            TelnetUtil.read("Password:");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void configTerminal(){
        String command = "config terminal";
        try {
            TelnetUtil.sendCommand(command);
            TelnetUtil.read("#");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void exit(){
        String command = "exit";
        try {
            TelnetUtil.sendCommand(command);
            TelnetUtil.read("#");
        } catch (IOException e) {
            System.out.println("exit failed");
            e.printStackTrace();
        }
    }

    public void enterInterface(String interfaceName){
        String command = "int "+interfaceName;
        try {
            TelnetUtil.sendCommand(command);
            TelnetUtil.read("#");
        } catch (IOException e) {
            System.out.println("enter interface failed");
            e.printStackTrace();
        }
    }
    //configure interface ip
    public void configureInterfaceIP(String interfaceName,String ip, String netmask, String status){
        try {
            String command1 = "ip address "+ip+" "+netmask;
            String command2 = status.equals("0") ? "shutdown":"no shutdown";
            TelnetUtil.sendCommand(command1);
            TelnetUtil.sendCommand(command2);
            TelnetUtil.read("#");
        } catch (IOException e) {
            System.out.println("interface ip conf failed");
            e.printStackTrace();
        }
    }

    //configure nat router interface inside or outside
    //already enter interface
    //0 : outside 1 : inside


    public void configureSide(NatConfig natConfig) throws IOException {
        for(String inside : natConfig.getInside()){
            String command = "int "+inside;
            TelnetUtil.sendCommand(command);
            TelnetUtil.read("#");
            TelnetUtil.sendCommand("ip nat inside");
            TelnetUtil.read("#");
            exit();
        }
        for(String outside : natConfig.getOutside()){
            String command = "int "+outside;
            TelnetUtil.sendCommand(command);
            TelnetUtil.read("#");
            TelnetUtil.sendCommand("ip nat outside");
            TelnetUtil.read("#");
            exit();
        }
    }


    public void configureNat(String ip, String netmask, String hostsNumber){
        String startIp = IpUtil.getStartIp(ip, netmask, Integer.parseInt(hostsNumber)).get(0);
        String endIp = IpUtil.getStartIp(ip, netmask, Integer.parseInt(hostsNumber)).get(1);
        String command1 = "ip nat pool gloabl "+startIp+" "+endIp+" netmask "+netmask;
        String command2 = "access-list 1 permit 10.0.0.0 0.0.0.255";
        String command3 = "ip nat inside source list 1  pool gloabl overload";
        try {
            TelnetUtil.sendCommand(command1);
            TelnetUtil.read("#");
            TelnetUtil.sendCommand(command2);
            TelnetUtil.read("#");
            TelnetUtil.sendCommand(command3);
            TelnetUtil.read("#");
        } catch (IOException e) {
            System.out.println("nat config failed");
            e.printStackTrace();
        }
    }

    public boolean checkNetwork(String ip, String netmask, String hostsNumber){
        return IpUtil.IsIpv4(ip) && IpUtil.IsSubnetMask(netmask) && IpUtil.getHostsNumber(netmask) <= Integer.parseInt(hostsNumber);
    }



    public void disconnect() throws IOException {
        TelnetUtil.disconnect();
    }

    public List<RouterInterface> getRouterInterface() throws IOException {
        List<RouterInterface> list = new LinkedList<>();
        String command = "show ip int b";
        TelnetUtil.sendCommand(command);
        String info = TelnetUtil.read("#");
        String[] items = info.split("\n");
        for(int i = 2; i < items.length -2; i++){
            if(items[i].trim().startsWith("NV")){
                continue;
            }
            String[] cols = items[i].trim().split("\\s+");
            RouterInterface routerInterface;
            //TODO netmask
            if (cols[4] .contains("up") ) {
                routerInterface = new RouterInterface(cols[0],cols[1],"","1");
            } else {
                routerInterface = new RouterInterface(cols[0],cols[1],"","0");
            }
            list.add(routerInterface);
        }
        list.sort(new Comparator<RouterInterface>() {
            @Override
            public int compare(RouterInterface o1, RouterInterface o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return list;
    }


    public boolean ping(String ip){
        String command = "ping "+ip;
        try {
            TelnetUtil.sendCommand(command);
            String ret = TelnetUtil.read("#");
            return ret.trim().contains("100 percent");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public void configureStaticRoute(String ip, String netmask, String nextHop) throws IOException {
        String command = "ip route "+ip+" "+netmask+" "+nextHop;
        TelnetUtil.sendCommand(command);
        TelnetUtil.read("#");

    }

    public List<NatTranslations> getNatTranslations() throws IOException {
        List<NatTranslations> natTranslations = new ArrayList<>();
        String command = "show ip nat translations";
        TelnetUtil.sendCommand(command);
        TelnetUtil.sendCommand("k");
        String ret = TelnetUtil.read("#");
        String[] items = ret.split("\n");
        for(int i = 2; i < items.length-2; i++){
            if(items[i].startsWith("--")){
                continue;
            }
            String[] cols = items[i].split("\\s+");
            NatTranslations translation = new NatTranslations(cols[0], cols[1], cols[2]);
            natTranslations.add(translation);
        }
        System.out.println(natTranslations);
        return natTranslations;
    }

    public void telnet(String ip) throws IOException {
        String command = "telnet "+ip;
        TelnetUtil.sendCommand(command);
        TelnetUtil.read("Password:");
    }

}
