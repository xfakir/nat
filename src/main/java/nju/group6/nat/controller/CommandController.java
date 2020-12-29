package nju.group6.nat.controller;

import nju.group6.nat.pojo.NatConfig;
import nju.group6.nat.pojo.RouterInterface;
import nju.group6.nat.service.CommandService;
import nju.group6.nat.util.Result;
import nju.group6.nat.util.TelnetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
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
    public Result sendPassword(@RequestParam String password) throws IOException, InterruptedException {
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
        List<RouterInterface> routerInterface = commandService.getRouterInterface();
        commandService.configTerminal();
       /* System.out.println(password);
        Thread.sleep(1000);
        List<RouterInterface> routerInterface = new LinkedList<>();
        RouterInterface r1 = new RouterInterface("f0/0","10.0.0.1","10.0.0.0","1");
        RouterInterface r2 = new RouterInterface("f0/1","10.0.0.1","10.0.0.0","0");
        RouterInterface r3 = new RouterInterface("s0/0/0/0","10.0.0.1","10.0.0.0","1");
        RouterInterface r4 = new RouterInterface("s0/0/0/1","10.0.0.1","10.0.0.0","0");
        routerInterface.add(r1);
        routerInterface.add(r2);
        routerInterface.add(r3);
        routerInterface.add(r4);*/
        return Result.success("success", routerInterface);
    }


    @RequestMapping("/configInterface")
    public Result configureInterface(@RequestBody RouterInterface routerInterface){
        commandService.enterInterface(routerInterface.getName());
        commandService.confInterfaceIP(routerInterface.getName(), routerInterface.getAddress(),
                routerInterface.getNetmask(), routerInterface.getState());
        commandService.exit();
        return Result.success("config ineterface succeed", null);
    }

    @RequestMapping("/configNat")
    public Result configureNat(@RequestBody NatConfig natConfig){
        commandService.enterInterface(natConfig.getInside());
        commandService.configureSide(natConfig.getInside());
        commandService.enterInterface(natConfig.getOutside());
        commandService.configureSide(natConfig.getOutside());
        if(!commandService.checkNetwork(natConfig.getNetwork(), natConfig.getNetmask(), natConfig.getTotal())){
            return Result.failure("invalid data");
        }
        commandService.natConfigure(natConfig.getNetwork(), natConfig.getNetmask(), natConfig.getTotal());
        return Result.success("success", null);
    }

    @RequestMapping("/disconnect")
    public Result disconnect() throws IOException {
        commandService.disconnect();
        return Result.success("success", null);
    }


}
