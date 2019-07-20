package Client;

import Message.Response;
import org.jboss.netty.channel.*;

import java.util.concurrent.CountDownLatch;

/**
 * @author Ryan Li
 * @date 2019/07/19
 */
public class RPCClientHandler extends SimpleChannelHandler {

    private final CountDownLatch countDownLatch = new CountDownLatch(1);
    private Response response = new Response(null, null);

    public Response getResponse() {
        return this.response;
    }

    public CountDownLatch getCountDownLatch(){
        return countDownLatch;
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        System.out.println("Client: Message received");

        this.response = new Response(e.getMessage(), true);
        this.countDownLatch.countDown();

        super.messageReceived(ctx, e);
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("Client: Channel connected");
        super.channelConnected(ctx, e);
    }

    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("Client: Channel disconnected");
        super.channelDisconnected(ctx, e);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        System.out.println("Client: Exception caught");
        super.exceptionCaught(ctx, e);
    }

    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("Client: Channel closed");
        super.channelClosed(ctx, e);
    }
}
