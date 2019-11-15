package org.onosproject.ldy.test1;

import com.ctc.wstx.util.InternCache;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;

public class ClientChannelHanderContextMap {
    public static Map<Integer, ChannelHandlerContext> clientMap=new HashMap<Integer, ChannelHandlerContext>();

    public static void add(Integer domainID,ChannelHandlerContext ctx){
        clientMap.put(domainID,ctx);
    }
    public static void remove(Integer domainID){
        clientMap.remove(domainID);
    }

    public static ChannelHandlerContext getContext(Integer domainID){
        return clientMap.get(domainID);
    }
}
