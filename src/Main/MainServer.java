package Main;

import Server.RPCServer;
import Server.RPCServerHandler;
import Service.HelloService;
import Service.HelloServiceImpl;

/**
 * @author Ryan Li
 * @date 2019/07/18
 */
public class MainServer {

    public static void main(String[] args) {
        RPCServerHandler serverHandler = new RPCServerHandler();
        serverHandler.Register(HelloService.class.getName(), HelloServiceImpl.class);
        new RPCServer().start(serverHandler);
    }
}
