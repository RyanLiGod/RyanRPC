package Server;

import Service.HelloService;
import Service.HelloServiceImpl;

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
                e.printStackTrace();
            }
        }).start();
    }

}
