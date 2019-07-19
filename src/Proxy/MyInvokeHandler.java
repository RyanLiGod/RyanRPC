package Proxy;

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
public class MyInvokeHandler implements InvocationHandler {

    private Class target;

    public MyInvokeHandler(Class target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("invoke before");
        ObjectOutputStream output = null;
        ObjectInputStream input = null;
        Socket socket = null;
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress("localhost", 8088));
            output = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Client 传递信息中...");

            output.writeUTF(target.getName());
            output.writeUTF(method.getName());
            output.writeObject(method.getParameterTypes());
            output.writeObject(args);

            input = new ObjectInputStream(socket.getInputStream());
            return input.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (output != null) output.close();
            if (input != null) input.close();
            if (socket != null) socket.close();
        }
        System.out.println("invoke end");
        return null;
    }
}
