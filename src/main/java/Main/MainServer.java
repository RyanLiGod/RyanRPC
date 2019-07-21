package Main;

import Register.ServiceRegister;
import Server.RPCServer;
import Server.RPCServerHandler;
import Service.HelloService;
import Service.HelloServiceImpl;

import java.net.InetAddress;

/**
 * @author Ryan Li
 * @date 2019/07/18
 */
public class MainServer {
    private static final Integer port = 10101;

    public static void main(String[] args) throws Exception {
        RPCServerHandler serverHandler = new RPCServerHandler();
        serverHandler.Register(HelloService.class.getName(), HelloServiceImpl.class);
        ServiceRegister serviceRegister = new ServiceRegister();
        serviceRegister.register(HelloService.class.getName(), InetAddress.getLocalHost().getHostAddress(), port);
        new RPCServer().start(serverHandler, port);
    }
}
