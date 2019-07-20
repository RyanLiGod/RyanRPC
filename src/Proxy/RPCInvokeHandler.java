package Proxy;

import Client.RPCClient;
import Message.Request;
import Message.Response;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

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
            client.start();
            System.out.println("Client 传递信息中...");
            Response response = client.send(request);
            return response.getResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("invoke failed");
        return null;
    }
}
