package org.javahispano.javacup.model.util;

public interface Agent {

    public byte[] execute(Object l, Object v) throws Exception;
    
    public boolean isTactic(Class<?> t) throws Exception;
}