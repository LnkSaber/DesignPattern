package com.saber.adapter;

public class Adaptor2 implements Target{
    private Adaptee adaptee;
    @Override
    public void handleReq() {
        adaptee.request();
    }

    public Adaptor2(Adaptee adaptee) {
        this.adaptee = adaptee;
    }
}
