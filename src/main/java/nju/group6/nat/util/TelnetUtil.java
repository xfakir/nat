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

    public  static void connect() throws IOException {
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
            System.out.println(sb.toString());
            return sb.toString();
        }



   /* public static String receive() throws IOException {
        InputStream inputStream = telnetClient.getInputStream();
        StringBuilder result = new StringBuilder();
        char[] buffer = new char[1024];
        Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        for (;;) {
            System.out.println("before read");
            int len = reader.read(buffer,0,buffer.length);
            System.out.println("after read");
            if (len < 0) {
                break;
            }
            result.append(buffer,0,len);
            System.out.println(result.toString());
            System.out.println("==========================");
        }
        return result.toString();
    }

<<<<<<< HEAD

=======
    public static String test() throws IOException {
        InputStream inputStream = telnetClient.getInputStream();
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
            System.out.println(result.toString());
        }
        return result.toString("UTF-8");
    }*/

//
//    public static String receive() throws IOException, InterruptedException {
//        InputStream inputStream = telnetClient.getInputStream();
//        int count = inputStream.available();
//        while (count == 0) {
//            Thread.sleep(1000);
//            count = inputStream.available();
//        }
//        //ByteArrayOutputStream result = new ByteArrayOutputStream();
//
//        byte[] buffer = new byte[count];
//        if (count != 0) {
//            int readCount = 0;
//            while(readCount < count) {
//                readCount += inputStream.read(buffer, readCount, count - readCount);
//            }
//        }
//        return new String(buffer);
//    }

}
