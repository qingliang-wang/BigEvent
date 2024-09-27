package com.itheima;

import org.junit.jupiter.api.Test;

public class ThreadLocalTest {
    @Test
    public void testThreadLocalSetAndGet(){
        ThreadLocal tl= new ThreadLocal();
        // 开启两个线程

        new Thread(()->{
            tl.set("晓燕");
            System.out.println(Thread.currentThread().getName()+":" +tl.get());
            System.out.println(Thread.currentThread().getName()+":" +tl.get());
            System.out.println(Thread.currentThread().getName()+":" +tl.get());
            },"蓝色").start();

        new Thread(()->{
            tl.set("姚晨");
            System.out.println(Thread.currentThread().getName()+":" +tl.get());
            System.out.println(Thread.currentThread().getName()+":" +tl.get());
            System.out.println(Thread.currentThread().getName()+":" +tl.get());
        },"绿色").start();


    }
}
