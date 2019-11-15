package org.onosproject.ldy.test1;

public class TopoReplyMessageResolver implements Resolver{

    @Override
    public boolean support(Message message) {
        return message.getMessageType().getValue()==MessageType.ODP_TOPOLOGY_REPLY.getValue();
    }

    @Override
    public Message resolve(Message message) {
        System.out.println("message:"+message.getBody());
        Message new_message=new Message(MessageType.ODP_EMPTY,0,"0");
        return new_message;
    }
}
