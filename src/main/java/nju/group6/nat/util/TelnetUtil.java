package nju.group6.nat.util;

import org.apache.commons.net.io.Util;
import org.apache.commons.net.telnet.TelnetClient;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author jerry
 * @create 2020-12-23-9:10
 */
@Component
public class TelnetUtil {
    private static TelnetClient telnetClient = null;

    public static void connect() throws IOException {
        //connect("172.19.240.101", "23");
        connect("172.19.241.173", "23");
    }
    public static void connect(String ip, String port) throws IOException {
        if (telnetClient == null) {
            telnetClient = new TelnetClient();
        }

        telnetClient.connect(ip, Integer.parseInt(port));

    }

    public static void disconnect() throws IOException {
        telnetClient.disconnect();
    }


    public static void sendCommand(String command) throws IOException {

        //加入换行符
        command = command + "\n";

        if(telnetClient != null){
            OutputStream outputStream = telnetClient.getOutputStream();
            outputStream.write(command.getBytes());
            outputStream.flush();
        }

    }

    public static String receive() throws IOException {
        InputStream inputStream = telnetClient.getInputStream();
        StringBuilder result = new StringBuilder();
        char[] buffer = new char[1024];
        Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        for (;;) {
            int len = reader.read(buffer,0,buffer.length);
            if (len < 0) {
                break;
            }
            result.append(buffer,0,len);
        }
        return result.toString();
    }


}
