package nju.group6.nat.controller;

import nju.group6.nat.pojo.RouterInterface;
import nju.group6.nat.service.CommandService;
import nju.group6.nat.util.Result;
import nju.group6.nat.util.TelnetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/command")
public class CommandController {
    private static int login_passwd_error_count = 3;

    @Autowired
    private CommandService commandService;


    @RequestMapping("/test")
    public String test() {
        return "NAT";
    }

    @RequestMapping("/connectRouter")
    public Result connectRouter(@RequestParam String hostName, @RequestParam String port) throws IOException, InterruptedException {
        TelnetUtil.connect(hostName, port);
        TelnetUtil.read("Password:");
        return Result.success("connect succeed",null);
    }

    @RequestMapping("/password")
    public Result sendPassword(@RequestParam String password) throws IOException {
        TelnetUtil.sendCommand(password);
        String ret1 = TelnetUtil.read("Password:").trim();
        if(!ret1.endsWith(">") && login_passwd_error_count > 0){
           login_passwd_error_count--;
           return Result.failure("password incorrect," + "you can input "+login_passwd_error_count+" more times");
        }
        if(login_passwd_error_count == 0){
            TelnetUtil.disconnect();
            return Result.failure("telnet disconnect");
        }
        commandService.enable();
        String ret2 = TelnetUtil.read("Password");
        TelnetUtil.sendCommand("CISCO");
        Map<String, RouterInterface> data = commandService.getRouterInterface();
        commandService.configTerminal();
        return Result.success("success", data);
    }


  @GetMapping("/configInterface")
    public Result configureInterface(@RequestParam String interfaceName, @RequestParam String ip, @RequestParam String netmask, @RequestParam String status){
        commandService.enterInterface(interfaceName);
        commandService.confInterfaceIP(interfaceName, ip, netmask, status);
        commandService.exit();
        return Result.success("config interface succeed", null);
    }

    @RequestMapping("/configNat")
    public Result configureNat(@RequestParam String network,@RequestParam String netmask, @RequestParam String numbers, @RequestParam String inside, @RequestParam String outside){
        commandService.enterInterface(inside);
        commandService.configureSide(inside);
        commandService.enterInterface(outside);
        commandService.configureSide(outside);
        if(!commandService.checkNetwork(network, netmask, numbers)){
            return Result.failure("invalid data");
        }
        commandService.natConfigure(network, netmask,numbers);
        return Result.success("success", null);
    }

//    @RequestMapping("/username")
//    public void sendUsername(@RequestParam String username) throws Exception {
//        commandService.sendUsername(username);
//        TelnetUtil.read("Password:");
//    }
//
//    @RequestMapping("/password")
//    public void sendPassword(@RequestParam String password) throws Exception {
//        commandService.sendPassword(password);
////        TelnetUtil.sendPassword(password);
//        TelnetUtil.read("$");
//
//    }
//
//    @RequestMapping("/testCommand")
//    public void testCommand() throws IOException {
//
//    }

}
