package org.onosproject.ldy.test1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MessageDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int head=byteBuf.readInt();
//        System.out.println(head);
         MessageType type=MessageType.valueof(byteBuf.readInt());
//        System.out.println(type);
         int contentlength=byteBuf.readInt();
//        System.out.println(contentlength);
       CharSequence charSequence=byteBuf.readCharSequence(contentlength, StandardCharsets.UTF_8);
         Message message=new Message(type,contentlength,charSequence.toString());
         list.add(message);
    }

}
