package org.onosproject.ldy.test1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;

public class MessageEncoder extends MessageToByteEncoder<Message> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, ByteBuf byteBuf) throws Exception {
        if (message.getMessageType().getValue() != 8) {
            byteBuf.writeInt(message.getHeadData());
            byteBuf.writeInt(message.getMessageType().getValue());
            byteBuf.writeInt(message.getContentLength());
            byteBuf.writeCharSequence(message.getBody(), Charset.defaultCharset());
        }
    }
}
