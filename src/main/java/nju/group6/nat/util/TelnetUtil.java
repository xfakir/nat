package nju.group6.nat.util;

import org.apache.commons.net.telnet.TelnetClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * @author jerry
 * @create 2020-12-23-9:10
 */
@Component
public class TelnetUtil {
    private static TelnetClient telnetClient = null;

    private static InputStream inputStream = null;
    private static OutputStream outputStream = null;

    public  static void  connect(String ip, String port) throws IOException {
        if (telnetClient == null) {
            telnetClient = new TelnetClient();
        }
        telnetClient.connect(ip, Integer.parseInt(port));
        inputStream = telnetClient.getInputStream();
        outputStream = telnetClient.getOutputStream();
    }


    public static void disconnect() throws IOException {
        telnetClient.disconnect();
    }

    public static void sendCommand(String command) throws IOException {

        //加入换行符
        command = command +"\n";

        if(telnetClient != null){
            outputStream.write(command.getBytes());
            outputStream.flush();
        }

    }
        public static String read(String pattern) throws IOException {
            int len = -1;
            byte[] buffer = new byte[1024];
            StringBuilder sb = new StringBuilder();
            while((len = inputStream.read(buffer)) != -1){
                sb.append(new String(buffer, 0, len));
                if(sb.toString().trim().endsWith(pattern)){
                    break;
                }
            }
//            System.out.println("======================================");
            System.out.println(sb.toString());
//            System.out.println("======================================");
            return sb.toString();
        }

    public static InputStream getInputStream() {
        return inputStream;
    }

    public static OutputStream getOutputStream() {
        return outputStream;
    }

}
