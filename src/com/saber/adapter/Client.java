package com.saber.adapter;

public class Client {
    public void Test (Target t){
        t.handleReq();
    }

    public static void main(String[] args) {
        Client client = new Client();
        Adaptee adaptee = new Adaptee();

//        Target target = new Adaptor();

        Target target = new Adaptor2(adaptee);
        client.Test(target);
    }
}
