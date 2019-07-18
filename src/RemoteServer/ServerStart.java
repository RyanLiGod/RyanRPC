package RemoteServer;

import RPCServer.HelloService;
import RPCServer.HelloServiceImpl;

/**
 * @author Ryan Li
 * @date 2019/07/18
 */
public class ServerStart {
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Server server = new Server();
                server.register(HelloService.class, HelloServiceImpl.class);
                server.start();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }).start();
    }
}
