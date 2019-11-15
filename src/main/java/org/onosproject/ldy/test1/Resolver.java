package org.onosproject.ldy.test1;

public interface Resolver {
    boolean support(Message message);
    Message resolve(Message message);
}
