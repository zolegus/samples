package org.zolegus.samples.random;

import java.util.UUID;

/**
 * @author oleg.zherebkin
 */
public class GenerateID {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(UUID.randomUUID().toString());
        }


    }
}
