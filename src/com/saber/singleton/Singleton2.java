package com.saber.singleton;

public class Singleton2 {
    private static Singleton2 s;
    private Singleton2(){

    }
    //类初始化时，不初始化这个对象（延时加载，真正用的时候再创建）。
    //方法同步，调用效率低！
    public static synchronized Singleton2 getInstance(){
        if (s == null) {
            s = new Singleton2();
        }
        return s;
    }
}
