package Server;

import Message.Request;
import Register.ZKUtil;
import Service.HelloService;
import org.jboss.netty.channel.*;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

/**
 * @author Ryan Li
 * @date 2019/07/18
 */
public class RPCServerHandler extends SimpleChannelHandler {

    private HashMap<String, Class> serviceRegistry = new HashMap<>();

    public void register(String name, Class c, Integer port) throws Exception {
        if (!serviceRegistry.containsKey(name)) {
            serviceRegistry.put(name, c);
        }
        ZKUtil zkUtil = new ZKUtil();
        zkUtil.zkRegister(c.getName(), InetAddress.getLocalHost().getHostAddress(), port);
    }

    private Object requestHandler(Request request) throws Exception {
        Class serverClass = serviceRegistry.get(request.getClassName());
        Method method = serverClass.getMethod(request.getMethodName(), request.getTypeParameters());
        return method.invoke(serverClass.getDeclaredConstructor().newInstance(), request.getParametersVal());
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        System.out.println("Server: Message received");

        Request request = (Request) e.getMessage();
        Object result = requestHandler(request);

        // 回写数据
        ctx.getChannel().write(result);

        super.messageReceived(ctx, e);
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("Server: Channel connected");
        super.channelConnected(ctx, e);
    }

    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("Server: channel disconnected");
        super.channelDisconnected(ctx, e);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        System.out.println("Server: Exception caught");
        super.exceptionCaught(ctx, e);
    }

    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("Server: Channel closed");
        super.channelClosed(ctx, e);
    }
}
