package org.onosproject.ldy.test1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class EchoClientHandler extends EochServerHander {
    private ExecutorService executorService= Executors.newSingleThreadExecutor();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
//        executorService.execute(new MessageSender(ctx));
        String body="1";
        Message message=new Message(MessageType.ODP_HELLO,body.length(),body);
        ctx.writeAndFlush(message);
    }
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.READER_IDLE) {
                String ping_body="000000000000";
                Message messgage = new Message(MessageType.ODP_PING, ping_body.length(), ping_body);
                ctx.writeAndFlush(messgage);
            } else if (state== IdleState.WRITER_IDLE) {
                // 如果当前服务在指定时间内没有写入消息到管道，则关闭当前管道
                ctx.close();
            }
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


    private static final class MessageSender implements Runnable{
        Scanner scanner=new Scanner(System.in);
        private volatile ChannelHandlerContext ctx;
        public MessageSender(ChannelHandlerContext ctx){
            this.ctx=ctx;
        }
        @Override
        public void run() {
            while(true){
                int value=scanner.nextInt();
                System.out.println("Message_type:"+MessageType.valueof(value));
                System.out.println("input length:");
                int length=scanner.nextInt();
                System.out.println("input body");
                String body=scanner.next();

                Message message=new Message(MessageType.valueof(value),length,body);
                System.out.println(message.getBody());
                ctx.writeAndFlush(message);
            }
        }
    }
}
