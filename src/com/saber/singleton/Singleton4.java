package com.saber.singleton;

/**
 * 测试静态内部类实现单例模式
 * 这种方式：线程安全，调用效率高，并且实现了延时加载！
 *
 */
public class Singleton4 {
    private static class SingletonClassInstance{
        private static final Singleton4 instance = new Singleton4();
    }
    //方法没有同步，调用效率高！
    public static Singleton4 getInstance(){
        return SingletonClassInstance.instance;
    }
    private Singleton4(){

    }
}
