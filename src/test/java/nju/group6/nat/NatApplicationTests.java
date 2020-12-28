package nju.group6.nat;


import nju.group6.nat.service.CommandService;
import nju.group6.nat.util.TelnetUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NatApplicationTests {
    @Autowired
    CommandService commandService ;

    @Test
    void contextLoads() {
    }


    @Test
    public void test() throws Exception {
//        TelnetUtil.connect("10.0.0.1", "23");
//        TelnetUtil.read("Password:");
//        TelnetUtil.sendCommand("CISCO");
//        TelnetUtil.read(">");
//        commandService.enable();
//        TelnetUtil.read("Password:");
//        TelnetUtil.sendCommand("CISCO");
//        TelnetUtil.read("#");
//       Map<String, RouterInterface> map =  commandService.getRouterInterface();
//        for(Map.Entry<String, RouterInterface> entry : map.entrySet()){
//            System.out.println(entry.getKey()+" "+entry.getValue().toString());
//        }
//        commandService.configTerminal();
//        TelnetUtil.read("#");
//        commandService.enterInterface("f0/1");
//        TelnetUtil.read("#");
//        commandService.confInterfaceIP("f0/1","10.0.0.3","255.0.0.0","1" );
          TelnetUtil.connect("10.0.0.3","23");
        TelnetUtil.read(":");
        TelnetUtil.sendCommand("fgg");
        TelnetUtil.read(":");
        TelnetUtil.sendCommand("123");
        TelnetUtil.read(">");

    }

}
