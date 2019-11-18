package com.james.message_queue_test.chapter1.helloworld;

public class Test1 {
    public void getString(Test t){
        System.out.println(t.handle("g"));
    }

    public static void main(String[] args) {
        Test1 t = new Test1();
        t.getString(s -> s.toString()+"good");
    }
}
