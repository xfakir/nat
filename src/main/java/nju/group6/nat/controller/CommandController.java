package nju.group6.nat.controller;

import nju.group6.nat.service.CommandService;
import nju.group6.nat.util.Result;

import nju.group6.nat.util.TelnetUtil;

import org.apache.commons.net.telnet.TelnetClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/command")
public class CommandController {

    @Autowired
    private CommandService commandService;


    @RequestMapping("/test")
    public String test() {
        return "NAT";
    }

    @RequestMapping("/connect")
    public Result connect() throws IOException, InterruptedException {
        commandService.connect();
        TelnetUtil.read("login:");
        return Result.success("connect succeed",null);
    }

    @RequestMapping("/username")
    public void sendUsername(@RequestParam String username) throws Exception {
        System.out.println(username);
        commandService.sendUsername(username);
        TelnetUtil.read("Password:");
    }

    @RequestMapping("/password")
    public void sendPassword(@RequestParam String password) throws Exception {
        ;
        System.out.println(password);
        commandService.sendPassword(password);
//        TelnetUtil.sendPassword(password);
        TelnetUtil.read("$");

    }



    @RequestMapping("/testCommand")
    public void testCommand() throws IOException {

    }
}
