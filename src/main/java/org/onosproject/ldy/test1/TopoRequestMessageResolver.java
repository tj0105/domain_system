package org.onosproject.ldy.test1;

public class TopoRequestMessageResolver implements Resolver {

    @Override
    public boolean support(Message message) {
        return message.getMessageType().getValue()==MessageType.ODP_TOPOLOGY_REQUEST.getValue();
    }

    @Override
    public Message resolve(Message message) {
        System.out.println("message:"+message.getBody());
        String body="1111111";
        Message new_message=new Message(MessageType.ODP_TOPOLOGY_REPLY,body.length(),body);
        return new_message;
    }
}
