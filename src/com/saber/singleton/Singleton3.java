package com.saber.singleton;

/**
 * 双重检查锁实现单例模式
 *
 */
public class Singleton3  {
    private static Singleton3 instance = null;
    public static Singleton3 getInstance() {
        if (instance == null) {
            Singleton3 sc;
            synchronized (Singleton3.class) {
                sc = instance;
                if (sc == null) {
                    synchronized (Singleton3.class) {
                        if(sc == null) {
                            sc = new Singleton3();
                        }
                    }
                    instance = sc;
                }
            }
        }
        return instance;
    }
    private Singleton3() {
    }
}
