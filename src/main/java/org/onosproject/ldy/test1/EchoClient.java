package org.onosproject.ldy.test1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;


import java.net.InetSocketAddress;


public class EchoClient {
    private final String host;
    private final int port;
    public ChannelFuture channelFuture;
    public EchoClient(String host,int port){
        this.host=host;
        this.port=port;
    }
    public void start() throws Exception{
        EventLoopGroup group=new NioEventLoopGroup();
        try {
            Bootstrap b=new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,Boolean.TRUE)
                    .remoteAddress(new InetSocketAddress(host,port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel)
                            throws Exception{
                            ChannelPipeline pipeline=socketChannel.pipeline();
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(1024,0,4,0,4));
                            pipeline.addLast(new LengthFieldPrepender(4));
//                            pipeline.addLast(new IdleStateHandler(5,10,0));
                            pipeline.addLast(new MessageEncoder());
                            pipeline.addLast(new MessageDecoder());
                            pipeline.addLast(new EchoClientHandler());
                        }
                    });
            channelFuture=b.connect().sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) {
        String host="192.168.109.224";
        int port=6689;
        EchoClient client=new EchoClient(host,port);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client.start();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    new Thread(new Runnable() {
        @Override
        public void run() {
            while(true){
                try {
                    Thread.sleep(5000);
                    String body="1";
                    Message message=new Message(MessageType.ODP_HELLO,body.length(),body);
                client.channelFuture.channel().writeAndFlush(message);
                    System.out.println("ssssssssssssssss");
                }catch (Exception e){
                    System.out.println("Got an exception");
                }

            }
        }
    }).start();
    }
}
