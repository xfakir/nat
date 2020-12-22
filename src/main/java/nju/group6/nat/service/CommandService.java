package nju.group6.nat.service;

import nju.group6.nat.util.IOUtil;
import org.apache.commons.net.io.Util;
import org.apache.commons.net.telnet.TelnetClient;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class CommandService {
    private TelnetClient telnetClient = null;

    public void connect() throws IOException {
        if (telnetClient == null) {
            telnetClient = new TelnetClient();
        }
        telnetClient.connect("172.19.241.173", 23);
        Thread writer;
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
                }
            }
        };


        writer.setPriority(Thread.currentThread().getPriority() + 1);

        writer.start();

    }

    public void disconnect() throws IOException {
        telnetClient.disconnect();
    }


    public void sendUsername(String username) throws IOException {
        sendCommand(username);
    }

    public void sendPassword(String password) throws IOException {
        sendCommand(password);
    }

    public String sendCommand(String command) throws IOException {

        //加入换行符
        command = command + "\n";

        if (null == telnetClient) {
            return "连接已关闭";
        }
        OutputStream outputStream = telnetClient.getOutputStream();
        outputStream.write(command.getBytes());
        outputStream.flush();

        return "发送成功";
    }

    public void testCommand() throws IOException {
        if (telnetClient == null) {
            System.out.println("connection is closed");
        }
        OutputStream outputStream = telnetClient.getOutputStream();
        outputStream.write("echo \"test command\" > /root/b".getBytes());
        outputStream.flush();
    }

    public static void main(String[] args) {
        TelnetClient telnet;

        telnet = new TelnetClient();

        try
        {
            telnet.connect("172.19.241.173", 23);
        }
        catch (final IOException e)
        {
            e.printStackTrace();
            System.exit(1);
        }

        IOUtil.readWrite(telnet.getInputStream(), telnet.getOutputStream(),
                System.in, System.out);

        try
        {
            telnet.disconnect();
        }
        catch (final IOException e)
        {
            e.printStackTrace();
            System.exit(1);
        }

        System.exit(0);
    }
}
