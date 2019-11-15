package org.onosproject.ldy.test1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;

import java.util.Date;


@ChannelHandler.Sharable
public class EochServerHander extends SimpleChannelInboundHandler<Message> {
//    private final Logger log = LoggerFactory.getLogger(getClass());
    private ChannelHandlerContext ctx;
    private MessageResolverFactory resolveFactory=MessageResolverFactory.getInstance();


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
        super.channelInactive(ctx);

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx=ctx;
        super.channelActive(ctx);
        System.out.println("have a client join in :"+ctx.channel().remoteAddress().toString());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        if(message.getMessageType().getValue()==0){
            ClientChannelHanderContextMap.add(Integer.valueOf(message.getBody()),ctx);
        }
        Resolver resolver= (Resolver) resolveFactory.getMessageResolver(message);
        Message result=resolver.resolve(message);
        channelHandlerContext.writeAndFlush(result);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        resolveFactory.registerResolver(new HellowMessageResolver());
        resolveFactory.registerResolver(new HellowReplyMessageResolver());
        resolveFactory.registerResolver(new FlowMessageResolver());
        resolveFactory.registerResolver(new FlowReplyMessageResolver());
        resolveFactory.registerResolver(new PingMessageResolver());
        resolveFactory.registerResolver(new PongMessageResolver());
        resolveFactory.registerResolver(new TopoRequestMessageResolver());
        resolveFactory.registerResolver(new TopoReplyMessageResolver());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
