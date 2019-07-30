package Proxy;

import Client.RPCClient;
import Message.Request;
import Message.Response;
import Register.ZKUtil;
import Service.HelloService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

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
    public Object invoke(Object proxy, Method method, Object[] args) {
        System.out.println("invoke before");
        RPCClient client = new RPCClient();
        Request request = new Request(target.getName(), method.getName(), method.getParameterTypes(), args);
        ZKUtil zkUtil = new ZKUtil();
        String server = null;
        try {
            server = zkUtil.getService(target.getName());
            String[] serverInfo = server.split(":");
            client.start(serverInfo[0], Integer.parseInt(serverInfo[1]));
            System.out.println("Client 传递信息中...");
            Response response = client.send(request);
            return response.getResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            zkUtil.releaseService(target.getName(), server);
        }
        System.out.println("invoke failed");
        return null;
    }
}
