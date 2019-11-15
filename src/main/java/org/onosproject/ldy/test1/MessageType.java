package org.onosproject.ldy.test1;

public enum  MessageType {

    ODP_HELLO(0,"Hello"),
    ODP_HELLO_REPLY(1,"HelloRely"),
    ODP_PING(2,"Ping"),
    ODP_PONG(3,"Pong"),
    ODP_TOPOLOGY_REQUEST(4,"TopoRequest"),
    ODP_TOPOLOGY_REPLY(5,"TopoReply"),
    ODP_FLOW_REQUEST(6,"FlowRequest"),
    ODP_FLOW_REPLY(7,"FlowReply"),
    ODP_EMPTY(8,"Empty");

    private int value;
    private String name;


    MessageType(int value,String name ){
        this.name=name;
        this.value=value;
    }

    public static MessageType valueof(int value) {
        switch (value){
            case 0:
                return ODP_HELLO;
            case 1:
                return ODP_HELLO_REPLY;
            case 2:
                return ODP_PING;
            case 3:
                return ODP_PONG;
            case 4:
                return ODP_TOPOLOGY_REQUEST;
            case 5:
                return ODP_TOPOLOGY_REPLY;
            case 6:
                return ODP_FLOW_REQUEST;
            case 7:
                return ODP_FLOW_REPLY;
            case 8:
                return ODP_EMPTY;
            default:
                throw new IllegalArgumentException("Illegal wire value for type ODPMessageType");
        }

    }
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
