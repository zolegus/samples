package org.zolegus.samples.object;

import junit.framework.TestCase;

import java.lang.reflect.Type;

public class ResponseTest extends TestCase {
    public class User {
        public int a = 1;
        public double b = 2.2;
    }

    public void testGetResponse() throws Exception {
        User[] users = new User[2];
        users[0] = new User();
        users[1] = new User();
        users[1].a = 11;
        users[1].b = 22.22;

        Response response = new Response();
        response.setType(Type.class.cast(User.class));
        response.setObjects(users);
        User[] users2 = (User[])response.getObjects();
    }
}