package Client;

import RPCServer.HelloService;
import Proxy.MyInvokeHandler;

import java.lang.reflect.Proxy;

/**
 * @author Ryan Li
 * @date 2019/07/18
 */
public class RPCClient {

    public static void main(String[] args) {
        MyInvokeHandler invokeHandler = new MyInvokeHandler(HelloService.class);
        HelloService helloService = (HelloService) Proxy.newProxyInstance(HelloService.class.getClassLoader(), new Class<?>[]{HelloService.class}, invokeHandler);
        String result = helloService.sayHi("RPC");
        System.out.println(result);
    }

}
