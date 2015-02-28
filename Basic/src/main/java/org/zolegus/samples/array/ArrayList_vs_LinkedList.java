package org.zolegus.samples.array;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author oleg.zherebkin
 */
public class ArrayList_vs_LinkedList {
    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        LinkedList<Integer> linkedList = new LinkedList<Integer>();
        int number = 100000;

// ArrayList add
        long startTime = System.nanoTime();

        for (int i = 0; i < number; i++) {
            arrayList.add(i);
        }
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("ArrayList add:  " + duration);

// LinkedList add
        startTime = System.nanoTime();

        for (int i = 0; i < number; i++) {
            linkedList.add(i);
        }
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("LinkedList add: " + duration);

// ArrayList get
        startTime = System.nanoTime();

        for (int i = 0; i < number; i++) {
            arrayList.get(1);
        }
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("ArrayList get:  " + duration);

// LinkedList get
        startTime = System.nanoTime();

        for (int i = 0; i < number; i++) {
            linkedList.get(1);
        }
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("LinkedList get: " + duration);



// ArrayList remove
        startTime = System.nanoTime();

//        for (int i = number-1; i >=0; i--) {
//            arrayList.remove(i);
//        }
        //Альтернативный метод удаления первого элемента
        Iterator<Integer> iter = arrayList.iterator();
        while (iter.hasNext()) {
            Integer i = iter.next();
            if (i > -1) {
                iter.remove();
            }
        }

        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("ArrayList remove:  " + duration);




// LinkedList remove
        startTime = System.nanoTime();

//        for (int i = number-1; i >=0; i--) {
//            linkedList.remove(i);
//        }
        Iterator<Integer> iter2 = linkedList.iterator();
        while (iter2.hasNext()) {
            Integer i = iter2.next();
            if (i > -1) {
                iter2.remove();
            }
        }

        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("LinkedList remove: " + duration);


        System.out.println("ArrayList size = " + arrayList.size());
        System.out.println("LinkedList size = " + linkedList.size());
        System.out.println("--------------------------------------");
        //////////////////////////////////////////////////////////////

        number = 100000;
        ArrayList<SimpleObject> arraySOL = new ArrayList<>();
        LinkedList<SimpleObject> linkedSOL = new LinkedList<>();


        startTime = System.nanoTime();
        for (int i = 0; i < number; i++) {
            arraySOL.add(new SimpleObject());
        }
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("arraySOL  add:  " + duration);

        startTime = System.nanoTime();
        for (int i = 0; i < number; i++) {
            linkedSOL.add(new SimpleObject());
        }
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("linkedSOL add:  " + duration);


//        startTime = System.nanoTime();
//        Iterator<SimpleObject> iterator = arraySOL.iterator();
//        while (iterator.hasNext()) {
//            SimpleObject so = iterator.next();
//            iterator.remove();
//        }
//        endTime = System.nanoTime();
//        duration = endTime - startTime;
//        System.out.println("arraySOL  put:  " + duration);
//
//        startTime = System.nanoTime();
//        Iterator<SimpleObject> iterator2 = linkedSOL.iterator();
//        while (iterator2.hasNext()) {
//            SimpleObject so = iterator2.next();
//            iterator2.remove();
//        }
//        endTime = System.nanoTime();
//        duration = endTime - startTime;
//        System.out.println("linkedSOL put:  " + duration);

        startTime = System.nanoTime();
        Iterator<SimpleObject> iterator = arraySOL.iterator();
        int n = 0;
        while (iterator.hasNext()) {
            SimpleObject so = iterator.next();
            if (n >= number/2)
                iterator.remove();
            n++;
        }
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("arraySOL  multi remove:  " + duration);

        startTime = System.nanoTime();
        Iterator<SimpleObject> iterator2 = linkedSOL.iterator();
        n = 0;
        while (iterator2.hasNext()) {
            SimpleObject so = iterator2.next();
            if (n >= number/2)
                iterator2.remove();
            n++;
        }
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("linkedSOL multi remove:  " + duration);

        System.out.println("arraySOL size = " + arraySOL.size());
        System.out.println("linkedSOL size = " + linkedSOL.size());
        System.out.println("--------------------------------------");




    }

    static class SimpleObject {
        private long idL = System.currentTimeMillis();
        private String idS = String.valueOf(idL);
    }
}
