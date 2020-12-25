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
    private static final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    public static void connect() throws IOException {
        //connect("172.19.240.101", "23");
        connect("172.19.241.173", "23");
    }
    public static void connect(String ip, String port) throws IOException {
        if (telnetClient == null) {
            telnetClient = new TelnetClient();
        }

        telnetClient.connect(ip, Integer.parseInt(port));

        /*Thread writer;
        writer = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    Util.copyStream(telnetClient.getInputStream(), System.out);
                }
                catch (final IOException e)
                {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        };


        writer.setPriority(Thread.currentThread().getPriority() + 1);

        writer.start();*/
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


    public static String receive() throws IOException, InterruptedException {
        InputStream inputStream = telnetClient.getInputStream();
        int count = inputStream.available();
        while (count == 0) {
            Thread.sleep(1000);
            count = inputStream.available();
        }
        //ByteArrayOutputStream result = new ByteArrayOutputStream();

        byte[] buffer = new byte[count];
        if (count != 0) {
            int readCount = 0;
            while(readCount < count) {
                readCount += inputStream.read(buffer, readCount, count - readCount);
            }
        }
        return new String(buffer);
    }

}
