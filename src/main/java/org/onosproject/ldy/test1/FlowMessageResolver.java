package org.onosproject.ldy.test1;

public class FlowMessageResolver implements Resolver {

    @Override
    public boolean support(Message message) {
        return message.getMessageType().getValue()==MessageType.ODP_FLOW_REQUEST.getValue();
    }

    @Override
    public Message resolve(Message message) {
        System.out.println("message:"+message.getBody());
        String body="123456789";
        Message new_message=new Message(MessageType.ODP_FLOW_REPLY,body.length(),body);
        return new_message;
    }
}
