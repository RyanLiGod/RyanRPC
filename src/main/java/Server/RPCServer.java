package Server;

import org.jboss.netty.bootstrap.ServerBootstrap;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Ryan Li
 * @date 2019/07/18
 */
public class RPCServer {

    public void start(RPCServerHandler serverHandler, Integer port) throws Exception {
        // 服务类
        ServerBootstrap bootstrap = new ServerBootstrap();

        // boos和worker分别分配select，boss监听端口，worker负责读写
        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();

        // 设置NioServerSocket工厂
        bootstrap.setFactory(new NioServerSocketChannelFactory(boss, worker));

        bootstrap.setPipelineFactory(() -> {
            ChannelPipeline pipeline = Channels.pipeline();
            // 上行触发decoder
            pipeline.addLast("decoder", new ObjectDecoder(1024 * 1024,
                    ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
            // 下行触发encoder
            pipeline.addLast("encoder", new ObjectEncoder());
            pipeline.addLast("NettyServerHandler", serverHandler);
            return pipeline;
        });

        bootstrap.bind(new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(), port));
        System.out.println("Server start!");
    }

}


