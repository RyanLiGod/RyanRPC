package Client;

import Message.Request;
import Message.Response;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Ryan Li
 * @date 2019/07/18
 */
public class RPCClient {

    public Channel channel = null;
    public RPCClientHandler handler = new RPCClientHandler();

    public void start() {
        ClientBootstrap bootstrap = new ClientBootstrap();
        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();

        bootstrap.setFactory(new NioClientSocketChannelFactory(boss, worker));

        bootstrap.setPipelineFactory(() -> {
            ChannelPipeline pipeline = Channels.pipeline();
            pipeline.addLast("decoder", new ObjectDecoder(1024 * 2,
                    ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
            pipeline.addLast("encoder", new ObjectEncoder());
            pipeline.addLast("NettyServerHandler", handler);
            return pipeline;
        });

        ChannelFuture connect = bootstrap.connect(new InetSocketAddress("127.0.0.1", 10101));
        this.channel = connect.getChannel();
        System.out.println("Start connecting...");
    }

    public Response send(Request request) throws Exception {
        this.channel.write(request);
        handler.getCountDownLatch().await();
        return handler.getResponse();
    }

}
