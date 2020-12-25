package nju.group6.nat.util;

import org.apache.commons.net.io.Util;
import java.lang.*;
import org.apache.commons.net.telnet.TelnetClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * @author jerry
 * @create 2020-12-23-9:10
 */
@Component
public class TelnetUtil {
    private static TelnetClient telnetClient = null;
    private static InputStream inputStream = null;
    private static OutputStream outputStream = null;

    public static void connect() throws IOException {
        if (telnetClient == null) {
            telnetClient = new TelnetClient();

        }

        try {
            telnetClient.connect("192.168.206.142", 23);
//            telnetClient.connect("172.19.240.101", 23);
        } catch (IOException e) {
            System.out.println("connect failed");
            e.printStackTrace();
        }
//        Thread writer;
//        writer = new Thread()
//        {
//            @Override
//            public void run()
//            {
//                try
//                {
//                    Util.copyStream(telnetClient.getInputStream(), System.out);
//                }
//                catch (final IOException e)
//                {
//                    e.printStackTrace();
//                }
//            }
//        };
//
//
//        writer.setPriority(Thread.currentThread().getPriority() + 1);
//
//        writer.start();
        inputStream = telnetClient.getInputStream();
        outputStream = telnetClient.getOutputStream();
//        outputStream = new PrintStream(telnetClient.getOutputStream());

        TelnetUtil.read("login:");

    }
    public static void connect(String ip, String port) throws IOException {
        if (telnetClient == null) {
            telnetClient = new TelnetClient();
        }

        telnetClient.connect(ip, Integer.parseInt(port));


        TelnetUtil.read("login:");
    }

    public static void disconnect() throws IOException {
        telnetClient.disconnect();
    }


    public static void sendUsername(String username) throws IOException {
        sendCommand(username);
    }

    public static void sendPassword(String password) throws IOException {
        sendCommand(password);
    }

    public static void sendCommand(String command) throws IOException {

        //加入换行符
        command = command +"\n";

        if(telnetClient != null){
            outputStream.write(command.getBytes());
            outputStream.flush();
        }

    }

    public static void testCommand() throws IOException {
        if (telnetClient == null) {
            System.out.println("connection is closed");
        }
        OutputStream outputStream = telnetClient.getOutputStream();
        outputStream.write("echo \"test command\" > /root/b".getBytes());
        outputStream.flush();
    }

    public static void read(String pattern) throws IOException {
        int len = -1;
        byte[] buffer = new byte[1024];
        StringBuilder sb = new StringBuilder();
        while((len = inputStream.read(buffer)) != -1){
            sb.append(new String(buffer, 0, len));
            if(sb.toString().trim().endsWith(pattern)){
                break;
            }
        }
        System.out.println(sb.toString());
    }
}
