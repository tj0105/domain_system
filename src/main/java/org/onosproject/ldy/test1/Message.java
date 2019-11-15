package org.onosproject.ldy.test1;




public class Message {
    private final int HeadData=0x80;/* this is a head of the Message*/
    private MessageType messageType;
    private int contentLength;
    private String body;

    public Message( MessageType messageType, int contentLength, String body) {
        this.messageType = messageType;
        this.contentLength = contentLength;
        this.body = body;
    }


    public int getHeadData() {
        return HeadData;
    }


    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
       return "message:"+"[head_data="+HeadData+",type="+
               messageType.getName()+",contentlength=" +
               "="+contentLength+",content="+body+"]";
    }
}
