package nju.group6.nat.util;




import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author jerry
 * @create 2020-12-23-17:09
 */
public class IpUtil {

    public static int getNetmask(String netmask){
        String[] masks = netmask.split("\\.");
        int ans = 0;
        for(int i = 0; i < 4; i++){
            int a = Integer.parseInt(masks[i]);
            int shift = a << (32-8*(i+1));
            ans |= shift;
        }
        return ans;
    }


    //掩码是否是连续1判断
    public static boolean IsSubnetMask(String netmask) {
        int mask = getNetmask(netmask);
        mask = ~mask + 1;
        return (mask & (mask - 1)) == 0;
    }

    //是否是有效的IP地址
    public static boolean IsIpv4(String ipv4) {
        if (ipv4 == null || ipv4.length() == 0) {
            return false;
        }
        String[] parts = ipv4.split("\\.");
        if (parts.length != 4) {
            return false;
        }
        for (int i = 0; i < parts.length; ++i) {
            try {
                int n = Integer.parseInt(parts[i]);
                if (n >= 0 && n <= 255) continue;
                return false;
            }
            catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    public static int getHostsNumber(String netmask){
        int mask = getNetmask(netmask);
        return ~mask - 1;
    }

    public static List<String> getStartIp(String ip, String netmask, int number){
        String network = getNetwork(ip, netmask);
//        System.out.println(network);
        int mask = getNetmask(network);
        int startIp = mask | 1;
        int endIp = mask | number;
        return Arrays.asList(int2String(startIp), int2String(endIp));
    }

    public static String getNetwork(String ip, String netmask){
        StringBuilder network = new StringBuilder();
        String[] addresses = ip.split("\\.");
        String[] masks = netmask.split("\\.");
        for(int i = 0; i < 4; i++) {
            int netsegment = Integer.parseInt(addresses[i]) & Integer.parseInt(masks[i]);
            network.append(netsegment).append(".");
        }
        network.deleteCharAt(network.length()-1);
        return network.toString();
    }


    public static String int2String(int ip){
        StringBuilder ipstr = new StringBuilder();
        for(int i = 0; i < 4; i++){
            int addr = ip & 0xff000000;
            addr = addr >>> 24;
            ipstr.append(addr).append(".");
            ip = ip << 8;
        }
        ipstr.deleteCharAt(ipstr.length()-1);
        return ipstr.toString();
    }



    public static String getPCInfo(String query, int start, int len){
        String info = "";
        try {
            Process pro = Runtime.getRuntime().exec("cmd /c ipconfig /all | find /I \""+query+"\"");
            InputStreamReader isr = new InputStreamReader(pro.getInputStream(), "GBK");//需要转换字符编码
            BufferedReader br = new BufferedReader(isr);
            String str = br.readLine();
            while (str != null) {
                System.out.println(str.trim());
                System.out.println(str.trim().length());
                info = str.trim().substring(start,str.trim().length()-len);
                System.out.println(info);
                str = br.readLine();
            }
            br.close();
            isr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return info;
    }


}
