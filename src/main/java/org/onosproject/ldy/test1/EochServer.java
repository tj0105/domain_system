package org.onosproject.ldy.test1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;


public class EochServer {
    private  final int port=6689;
    ChannelFuture future;
    public static void main(String[] args) {
        EochServer eochServer=new EochServer();

        System.out.println("ss:"+ClientChannelHanderContextMap.clientMap);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    eochServer.start();
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).start();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true){
//                    try {
//                        Thread.sleep(5000);
//                    }
//                    catch (InterruptedException e){
//                        e.printStackTrace();
//                    }
//                    System.out.println(ClientChannelHanderContextMap.clientMap);
//                }
//            }
//        }).start();
    }

    public void start() throws Exception{
        EventLoopGroup group=new NioEventLoopGroup();
        EventLoopGroup workergroup=new NioEventLoopGroup();
        final EochServerHander serverHander=new EochServerHander();

        try {
            ServerBootstrap b=new ServerBootstrap();
            b.group(group,workergroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel)
                            throws  Exception{
                            ChannelPipeline pipeline=socketChannel.pipeline();
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(1024,0,4,0,4));
                            pipeline.addLast(new LengthFieldPrepender(4));
                            pipeline.addLast(new MessageEncoder());
                            pipeline.addLast(new MessageDecoder());
                            pipeline.addLast(serverHander);
                        }
                    });
            future=b.bind().sync();
            future.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully().sync();
            workergroup.shutdownGracefully().sync();
        }
    }
}
