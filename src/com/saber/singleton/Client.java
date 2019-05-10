package com.saber.singleton;

public class Client {
	
	public static void main(String[] args) {
		Singleton4 s1 = Singleton4.getInstance();
		Singleton4 s2 = Singleton4.getInstance();
		
		System.out.println(s1);
		System.out.println(s2);
		
		System.out.println(Singleton5.INSTANCE==Singleton5.INSTANCE);
		
		
	}
}
