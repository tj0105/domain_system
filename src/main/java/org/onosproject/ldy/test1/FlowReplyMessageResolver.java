package org.onosproject.ldy.test1;

public class FlowReplyMessageResolver implements Resolver{

    @Override
    public boolean support(Message message) {
        return message.getMessageType().getValue()==MessageType.ODP_FLOW_REPLY.getValue();
    }

    @Override
    public Message resolve(Message message) {
        System.out.println("message:"+message.getBody());
        Message new_message=new Message(MessageType.ODP_EMPTY,0,"0");
        return new_message;
    }
}
