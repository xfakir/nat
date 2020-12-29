package nju.group6.nat.service;


import nju.group6.nat.pojo.RouterInterface;
import nju.group6.nat.util.IpUtil;
import nju.group6.nat.util.TelnetUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class CommandService {

//    private TelnetClient telnetClient = null;

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
        } catch (IOException e) {
            System.out.println("exit failed");
            e.printStackTrace();
        }
    }

    public void enterInterface(String interfaceName){
        String command = "interface "+interfaceName;
        try {
            TelnetUtil.sendCommand(command);
            TelnetUtil.read("#");
        } catch (IOException e) {
            System.out.println("enter interface failed");
            e.printStackTrace();
        }
    }
    //configure interface ip
    public void confInterfaceIP(String interfaceName,String ip, String netmask, String status){
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

    public void disconnect() throws IOException {
        TelnetUtil.disconnect();
    }

    public List<RouterInterface> getRouterInterface() throws IOException {
        List<RouterInterface> list = new LinkedList<>();
        String command = "show ip int b";
        TelnetUtil.sendCommand(command);
        String info = TelnetUtil.read("#");
        String[] items = info.split("\n");
        System.out.println(items.length);
        for(int i = 0; i < items.length -1; i++){
            if(items[i].trim().length() < 20){
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

    public Map<String,String> getPCInfo(String pcName){
        Map<String, String> pcInfo = new HashMap<>();
        pcInfo.put("ip", IpUtil.getPCInfo("ipv4",34,4));
        pcInfo.put("netmask", IpUtil.getPCInfo("子网掩码",32,0));
        pcInfo.put("gateway", IpUtil.getPCInfo("默认网关",32,0));
        return pcInfo;
    }

}
