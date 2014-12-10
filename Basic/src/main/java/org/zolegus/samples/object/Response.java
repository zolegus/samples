package org.zolegus.samples.object;

import java.lang.reflect.Type;

/**
 * @author oleg.zherebkin
 */
public class Response {
    Type type;
    Object[] objects;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Object[] getObjects() {
        return objects;
    }

    public void setObjects(Object[] objects) {
        this.objects = objects;
    }
}
