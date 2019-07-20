package Proxy;

import Client.RPCClient;
import Message.Request;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author Ryan Li
 * @date 2019/07/18
 */
public class RPCInvokeHandler implements InvocationHandler {

    private Class target;

    public RPCInvokeHandler(Class target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("invoke before");
        RPCClient client = new RPCClient();
        Request request = new Request(target.getName(), method.getName(), method.getParameterTypes(), args);
        try {
            System.out.println("Client 传递信息中...");
            client.send(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("invoke failed");
        return null;
    }
}
