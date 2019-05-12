package com.saber.factory.simpleFactory;

public class CarFactory02 {
    public static Car createAudi(){
        return new Audi();
    }
    public static Car createByd(){
        return new Byd();
    }
}
