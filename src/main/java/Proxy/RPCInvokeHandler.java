package Proxy;

import Client.RPCClient;
import Message.Request;
import Message.Response;
import Register.ServiceRegister;
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
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("invoke before");
        RPCClient client = new RPCClient();
        Request request = new Request(target.getName(), method.getName(), method.getParameterTypes(), args);
        try {
            ServiceRegister serviceRegister = new ServiceRegister();
            List<String> serverList = serviceRegister.getService(HelloService.class.getName());
            System.out.println("获取到服务供给者：" + serverList);
            String[] serverInfo = serverList.get(0).split(",");
            client.start(serverInfo[0], Integer.parseInt(serverInfo[1]));
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
