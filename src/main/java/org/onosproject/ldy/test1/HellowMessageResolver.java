package org.onosproject.ldy.test1;

public class HellowMessageResolver implements Resolver {

    @Override
    public boolean support(Message message) {
        return message.getMessageType().getValue()==MessageType.ODP_HELLO.getValue();
    }

    @Override
    public Message resolve(Message message) {
        System.out.println("message:"+message.getBody());
        String body="1";
        Message new_message=new Message(MessageType.ODP_HELLO_REPLY,body.length(),body);
        return new_message;
    }
}
