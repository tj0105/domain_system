package org.onosproject.ldy.test1;

import org.glassfish.hk2.utilities.reflection.MethodWrapper;

public class HellowReplyMessageResolver implements Resolver{

    @Override
    public boolean support(Message message) {
        return message.getMessageType().getValue()==MessageType.ODP_HELLO_REPLY.getValue();
    }

    @Override
    public Message resolve(Message message) {
        System.out.println("message:"+message.getBody());
        String body="111111";
        Message new_message=new Message(MessageType.ODP_EMPTY,body.length(),body);
        return new_message;
    }
}
