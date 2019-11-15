package org.onosproject.ldy.test1;

public class PingMessageResolver implements Resolver {

    @Override
    public boolean support(Message message) {
        return message.getMessageType().getValue()==MessageType.ODP_PING.getValue();
    }

    @Override
    public Message resolve(Message message) {
        System.out.println("message:"+message.getBody());
        String body="00000000";
        Message new_message=new Message(MessageType.ODP_PONG,body.length(),body);
        return new_message;
    }
}
