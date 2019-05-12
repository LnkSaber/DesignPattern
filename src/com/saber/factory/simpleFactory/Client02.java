package com.saber.factory.simpleFactory;

/**
 * 简单工厂情况下
 *
 */
public class Client02 {   //调用者
	
	public static void main(String[] args) {
//		Car c1 =CarFactory01.createCar("奥迪");
//		Car c2 = CarFactory01.createCar("比亚迪");

		Car c1 =CarFactory02.createAudi();
		Car c2 = CarFactory02.createByd();

		c1.run();
		c2.run();
		
	}
}
