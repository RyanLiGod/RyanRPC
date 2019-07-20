package Main;

import java.lang.reflect.Proxy;

import Proxy.RPCInvokeHandler;
import Service.HelloService;

/**
 * @author Ryan Li
 * @date 2019/07/18
 */
public class MainClient {

    public static void main(String[] args) {
        RPCInvokeHandler invokeHandler = new RPCInvokeHandler(HelloService.class);
        HelloService helloService = (HelloService) Proxy.newProxyInstance(HelloService.class.getClassLoader(), new Class<?>[]{HelloService.class}, invokeHandler);
        String result = helloService.sayHello("RPC!");
        System.out.println("结果：" + result);
    }
}
