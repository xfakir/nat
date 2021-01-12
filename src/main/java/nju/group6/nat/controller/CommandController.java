package nju.group6.nat.controller;

import nju.group6.nat.pojo.*;
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
//    private static int login_passwd_error_count = 3;

    @Autowired
    private CommandService commandService;


    @RequestMapping("/test")
    public String test() {
        return "NAT";
    }

    @RequestMapping("/connectRouter")
    public Result connectRouter(@RequestParam String hostName, @RequestParam String port) throws IOException {
        TelnetUtil.connect(hostName, port);
        TelnetUtil.read("Password:");
        return Result.success("connect succeed",null);
    }

    @RequestMapping("/password")
    public Result sendPassword(@RequestParam String password) throws IOException {
        TelnetUtil.sendCommand(password);
        TelnetUtil.read(">");
//        if(!ret1.endsWith(">") && login_passwd_error_count > 0){
//           login_passwd_error_count--;
//           return Result.failure("password incorrect" + "you can input "+login_passwd_error_count+" more times");
//        }
//        if(login_passwd_error_count == 0){
//            TelnetUtil.disconnect();
//            return Result.failure("telnet disconnect");
//        }
        commandService.enable();
        TelnetUtil.sendCommand("CISCO");
        TelnetUtil.read("#");
        List<RouterInterface> routerInterface = commandService.getRouterInterface();
        return Result.success("success", routerInterface);
    }


    @RequestMapping("/interfaceInfo")
    public Result getInterfaceInfo() throws IOException {
        commandService.exit();
        List<RouterInterface> routerInterface = commandService.getRouterInterface();
        return Result.success("success", routerInterface);
    }

    @RequestMapping("/configInterface")
    public Result configureInterface(@RequestBody RouterInterface routerInterface){
        commandService.configTerminal();
        commandService.enterInterface(routerInterface.getName());
        commandService.configureInterfaceIP(routerInterface.getName(), routerInterface.getAddress(),
                routerInterface.getNetmask(), routerInterface.getState());
        commandService.exit();
        return Result.success("config ineterface succeed", null);
    }

    @RequestMapping("/configNat")
    public Result configureNat(@RequestBody NatConfig natConfig) throws IOException {
        commandService.configTerminal();
        commandService.configureSide(natConfig);
       /* if(!commandService.checkNetwork(natConfig.getNetwork(), natConfig.getNetmask(), natConfig.getTotal())){
            return Result.failure("invalid data");
        }*/
        commandService.configureNat(natConfig.getNetwork(), natConfig.getNetmask(), natConfig.getTotal());
        commandService.exit();
        return Result.success("success", null);
    }

    @RequestMapping("/ping")
    public Result ping(@RequestParam String ip){
        if(commandService.ping(ip)){
            return Result.success("success",null);
        }else{
            return Result.failure("failed");
        }
    }

    @RequestMapping("/configRoute")
    public Result configureRoute(@RequestBody RouteConfig routeConfig) throws IOException {
        commandService.configTerminal();
        commandService.configureStaticRoute(routeConfig.getNetwork(), routeConfig.getMask(), routeConfig.getNextHop());
        commandService.exit();
        return Result.success("success", null);
    }

    @RequestMapping("/natTranslations")
    public Result displayNatTranslations() throws IOException {
        List<NatTranslations> translations = commandService.getNatTranslations();
        return Result.success("success", translations);
    }

    @RequestMapping("/telnet")
    public Result telnet(@RequestParam String hostName) throws IOException {
        commandService.telnet(hostName);
        return Result.success("success", null);
    }

    @RequestMapping("/routeTable")
    public Result getRouteTable() throws IOException {
        List<RouteItem> routeItems = commandService.getRouteTable();
        return  Result.success("success", routeItems);
    }

    @RequestMapping("/disconnect")
    public Result disconnect() throws IOException {
        commandService.disconnect();
        return Result.success("success", null);
    }

}
