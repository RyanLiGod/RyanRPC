# RyanRPC_V3

RPC实现，利用 JDK 反射、动态代理对客户端接口进行增强。使用 Netty 进行网络通讯，Zookeeper 进行服务注册和负载均衡。 

切换分支可查看不同版本。

V1：利用 JDK 反射、动态代理对客户端接口进行增强，使用 Java BIO 进行网络通讯。

V2：在 V1 基础上修改网络通讯方式为 Netty（NIO）。

V3：在 V2 基础上使用 Zookeeper 进行服务注册和负载均衡。

