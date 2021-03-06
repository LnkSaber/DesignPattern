

# 设计模式

**• 创建型模式：**

**– 单例模式、工厂模式、抽象工厂模式、建造者模式、原型模式。**
**• 结构型模式：**
**– 适配器模式、桥接模式、装饰模式、组合模式、外观模式、享元模式、代理模**
**式。**
**• 行为型模式：**
**– 模版方法模式、命令模式、迭代器模式、观察者模式、中介者模式、备忘录模**
**式、解释器模式、状态模式、策略模式、职责链模式、访问者模式。**



## 1.单例模式

### 核心作用：

– 保证一个类只有一个实例，并且提供一个访问该实例的全局访问点。

### 常见应用场景：

– Windows的Task Manager（任务管理器）就是很典型的单例模式
– windows的Recycle Bin（回收站）也是典型的单例应用。在整个系统运行过程中，回收站一直维护着仅有的一个实例。
– 项目中，读取配置文件的类，一般也只有一个对象。没有必要每次使用配置文件数据，每次new一个对象去读取。
– 网站的计数器，一般也是采用单例模式实现，否则难以同步。
– 应用程序的日志应用，一般都何用单例模式实现，这一般是由于共享的日志文件一直处于打开状态，因为只能有一个实例去操作
，否则内容不好追加。
– 数据库连接池的设计一般也是采用单例模式，因为数据库连接是一种数据库资源。
– 操作系统的文件系统，也是大的单例模式实现的具体例子，一个操作系统只能有一个文件系统。
– Application 也是单例的典型应用（Servlet编程中会涉及到）
– 在Spring中，每个Bean默认就是单例的，这样做的优点是Spring容器可以管理
– 在servlet编程中，每个Servlet也是单例
– 在spring MVC框架/struts1框架中，控制器对象也是单例

### 单例模式的优点：

– 由于单例模式只生成一个实例，减少了系统性能开销，当一个对象的产生需要
比较多的资源时，如读取配置、产生其他依赖对象时，则可以通过在应用启动
时直接产生一个单例对象，然后永久驻留内存的方式来解决
– 单例模式可以在系统设置全局的访问点，优化环共享资源访问，例如可以设计
一个单例类，负责所有数据表的映射处理

###  常见的五种单例模式实现方式：

– 主要：

• 饿汉式（线程安全，调用效率高。 但是，不能延时加载。）

• 懒汉式（线程安全，调用效率不高。 但是，可以延时加载。）

– 其他：

• 双重检测锁式（由于JVM底层内部模型原因，偶尔会出问题。不建议使用）

• 静态内部类式(线程安全，调用效率高。 但是，可以延时加载)

• 枚举式(线程安全，调用效率高，不能延时加载。并且可以天然的防止反射和反序列化漏洞！)

如何选用?
– 单例对象 占用 资源 少，不需要 延时加载：
• 枚举式 好于 饿汉式
– 单例对象 占用 资源 大，需要 延时加载：
• 静态内部类式 好于 懒汉式

#### 1.饿汉式实现（单例对象立即加载）

```java
public class SingletonDemo02 {
	private static  SingletonDemo02 s = new SingletonDemo02();
	private SingletonDemo02(){} //私有化构造器
	public static SingletonDemo02 getInstance(){
	return s;}
}

public class Client {
public static void main(String[] args) {
SingletonDemo02 s = SingletonDemo02.getInstance();
SingletonDemo02 s2 = SingletonDemo02.getInstance();
System.out.println(s==s2); //结果为true
}
}

```

饿汉式单例模式代码中，static变量会在类装载时初始化，此时也不会涉及多个线程对象访问该对象的问
题。

虚拟机保证只会装载一次该类，肯定不会发生并发访问的问题。因此，可以省略synchronized关键字。

• **问题：如果只是加载本类，而不是要调用getInstance()，甚至永远没有调用，则会造成资源浪费！**



#### 2.懒汉式实现（单例对象延迟加载）

```java
public class SingletonDemo01 {
private static SingletonDemo01 s;
private SingletonDemo01(){} //私有化构造器
public static synchronized SingletonDemo01 getInstance(){
if(s==null){
s = new SingletonDemo01();
}
return s;
}
}
```

要点：
**– lazy load! 延迟加载， 懒加载！ 真正用的时候才加载！**
**• 问题：**
**– 资源利用率高了。但是，每次调用getInstance()方法都要同步，并发**
**效率较低。**



#### 3.双重检测锁实现（了解即可）

```java
public class SingletonDemo03 {
private static SingletonDemo03 instance = null;
public static SingletonDemo03 getInstance() {
if (instance == null) {
SingletonDemo03 sc;
synchronized (SingletonDemo03.class) {
sc = instance;
if (sc == null) {
synchronized (SingletonDemo03.class) {
if(sc == null) {
sc = new SingletonDemo03();
}
}
instance = sc;
}
}
}
return instance;
}
private SingletonDemo03() {
}
}
```



这个模式将同步内容下方到if内部，提高了执行的效率
不必每次获取对象时都进行同步，只有第一次才同步
创建了以后就没必要了。

问题：
• **由于编译器优化原因和JVM底层内部模型原因，**
**偶尔会出问题。不建议使用。**



#### 4.静态内部类实现方式(也是一种懒加载方式)

```java
public class SingletonDemo04 {
private static class SingletonClassInstance {
private static final SingletonDemo04 instance = new SingletonDemo04();
}
public static SingletonDemo04 getInstance() {
return SingletonClassInstance.instance;
}
private SingletonDemo04() {
}
}
```



要点：
– 外部类没有static属性，则不会像饿汉式那样立即加载对象。
– 只有真正调用getInstance(),才会加载静态内部类。加载类时是线程 安全的。 instance是static final
类型，保证了内存中只有这样一个实例存在，而且只能被赋值一次，从而保证了线程安全性. – 兼备了并发高效调用和延迟加载的优势！



#### 5.使用枚举实现单例模式

```java
public enum SingletonDemo05 {
/**
* 定义一个枚举的元素，它就代表了Singleton的一个实例。
*/
INSTANCE;
/**
* 单例可以有自己的操作
*/
public void singletonOperation(){
//功能处理
}
}
```

优点：
**– 实现简单**
**– 枚举本身就是单例模式。由JVM从根本上提供保障！避免通过反射和反序列化的漏洞！**
**• 缺点：**
**– 无延迟加载**

### 问题：

– 反射可以破解上面几种(不包含枚举式)实现方式！（可以在构造方法中手动
抛出异常控制）
– 反序列化可以破解上面几种((不包含枚举式))实现方式！
• 可以通过定义readResolve()防止获得不同对象。
– 反序列化时，如果对象所在类定义了readResolve()，（实际是一种回调），
定义返回哪个对象。

```java
public class SingletonDemo01 implements Serializable {
private static SingletonDemo01 s;
private SingletonDemo01() throws Exception{
if(s!=null){
throw new Exception("只能创建一个对象");
//通过手动抛出异常，避免通过反射创建多个单例对象！
}
} //私有化构造器
public static synchronized SingletonDemo01 getInstance() throws Exception{
if(s==null){
s = new SingletonDemo01();
}
return s;
}
//反序列化时，如果对象所在类定义了readResolve()，（实际是一种回调），定义返回哪个对象。
private Object readResolve() throws ObjectStreamException {
return s;
}
}
```

CountDownLatch
– 同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一
个或多个线程一直等待。
• countDown() 当前线程调此方法，则计数减一(建议放在 finally里执行)
• await()， 调用此方法会一直阻塞当前线程，直到计时器的值为0



![1557502204356](assets/1557502204356.png)

## 2.工厂模式：实现了创建者和调用者的分离

### 核心本质：

– 实例化对象，用工厂方法代替new操作。
– 将选择实现类、创建对象统一管理和控制。从而将调用者跟我们的实现类解耦。

### 分类：

– 简单工厂模式
**• 用来生产同一等级结构中的任意产品。（对于增加新的产品，需要修改已**
**有代码）**
– 工厂方法模式
**• 用来生产同一等级结构中的固定产品。（支持增加任意产品）**
– 抽象工厂模式
**• 用来生产不同产品族的全部产品。（对于增加新的产品，无能为力；支持**
**增加产品族）**

### 应用场景

– JDK中Calendar的getInstance方法
– JDBC中Connection对象的获取
– Hibernate中SessionFactory创建Session
– spring中IOC容器创建管理bean对象
– XML解析时的DocumentBuilderFactory创建解析器对象
– 反射中Class对象的newInstance()

### 1.简单工厂模式

#### 1.1不使用简单工厂的情况

~~~java
public class Client01 { //调用者
public static void main(String[] args) {
Car c1 = new Audi();
Car c2 = new Byd();
c1.run();
c2.run();
}
}
~~~

![1557632420946](assets/1557632420946.png)

#### 1.2简单工厂

#### 要点：

– 简单工厂模式也叫静态工厂模式，就是工厂类一般是使用静态方法，
通过接收的参数的不同来返回不同的对象实例。
– 对于增加新产品无能为力！不修改代码的话，是无法扩展的

~~~java
public class CarFactory {
public static Car createCar(String type){
Car c = null;
if("奥迪".equals(type)){
c = new Audi();
}else if("奔驰".equals(type)){
c = new Benz();
}
return c;
}
}
~~~

~~~java
public class CarFactory {
public static Car createAudi(){
return new Audi();
}
public static Car createBenz(){
return new Benz();
}
}
~~~

![1557632498166](assets/1557632498166.png)

### 2.工厂方法模式

工厂方法模式要点：
– 为了避免简单工厂模式的缺点，不完全满足OCP。
– 工厂方法模式和简单工厂模式最大的不同在于，简单工厂模式只有一个（对于一个项目
或者一个独立模块而言）工厂类，而工厂方法模式有一组实现了相同接口的工厂类。

```java
public  class Client  {
	public static void main(String[] args) {
		Car c1 = new AudiFactory().createCar();
		Car c2 = new BydFactory().createCar();
		
		c1.run();
		c2.run();
	}
}
```

![1557632536788](assets/1557632536788.png)

![1557632549726](assets/1557632549726.png)



### 3.抽象工厂模式

抽象工厂模式
– 用来生产不同产品族的全部产品。（对于增加新的产品，无能为力；
支持增加产品族）
– 抽象工厂模式是工厂方法模式的升级版本，在有多个业务品种、业务
分类时，通过抽象工厂模式产生需要的对象是一种非常好的解决方式。

~~~java
public class Client {
    public static void main(String[] args) {
     CarFactory factory = new LuxuryCarFactory();
        Engine engine = factory.createEngine();
        engine.run();
        engine.start();
        factory.createXXX();
    }
}

~~~

![1557632602424](assets/1557632602424.png)

![1557632616590](assets/1557632616590.png)



## 3.建造者模式

##### 1、定义：将一个复杂对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示

##### 2、主要作用：在用户不知道对象的建造过程和细节的情况下就可以直接创建复杂的对象。

##### 3、如何使用：用户只需要给出指定复杂对象的类型和内容，建造者模式负责按顺序创建复杂对象（把内部的建造过程和细节隐藏起来）

##### 4、解决的问题：

​        （1）、方便用户创建复杂的对象（不需要知道实现过程）
​        （2）、代码复用性 & 封装性（将对象构建过程和细节进行封装 & 复用）

###### 5、注意事项：与工厂模式的区别是：建造者模式更加关注与零件装配的顺序，一般用来创建更为复杂的对象

### 实现方式

> （1）通过Client、Director、Builder和Product形成的建造者模式
>
> （2）通过静态内部类方式实现零件无序装配话构造



### 1.通过Client、Director、Builder和Product形成的建造者模式

一般有以下几个角色
抽象建造者（builder）：描述具体建造者的公共接口，一般用来定义建造细节的方法，并不涉及具体的对象部件的创建。

具体建造者（ConcreteBuilder）：描述具体建造者，并实现抽象建造者公共接口。

指挥者（Director）：调用具体建造者来创建复杂对象（产品）的各个部分，并按照一定顺序（流程）来建造复杂对象。

产品（Product）：描述一个由一系列部件组成较为复杂的对象。

（2）举个例子
既然是建造者模式，那么就继续造火箭吧

假设造房简化为如下步骤：（1）轨道舱（2）发动机（3）逃逸塔

“如果”要造一艘火箭，首先要找航天部的科学家（指挥者）。承包商指挥工人（具体建造者）过来造火箭（产品），最后验收。

（3）具体步骤
1、创建抽象建造者定义造火箭步骤

2、创建工人具体实现造火箭步骤

3、创建承包商指挥工人施工

4、验收，检查是否建造完成



![1557734487873](assets/1557734487873.png)

（2）通过静态内部类方式实现零件无序装配话构造/通过构造函数装配特定属性，返回指定熟悉类(已加载属性)

Eg：Swagger2的 ApiInfo类的装配过程

~~~java
public class ApiInfoBuilder {
  private String title;
  private String description;
  private String termsOfServiceUrl;
  private Contact contact;
  private String license;
  private String licenseUrl;
  private String version;
  private List<VendorExtension> vendorExtensions = newArrayList();

  public ApiInfoBuilder title(String title) {
    this.title = title;
    return this;
  }

  public ApiInfoBuilder description(String description) {
    this.description = description;
    return this;
  }

  public ApiInfoBuilder termsOfServiceUrl(String termsOfServiceUrl) {
    this.termsOfServiceUrl = termsOfServiceUrl;
    return this;
  }

  public ApiInfoBuilder version(String version) {
    this.version = version;
    return this;
  }

  @Deprecated
  public ApiInfoBuilder contact(String contact) {
    this.contact = new Contact(contact, "", "");
    return this;
  }
  public ApiInfoBuilder contact(Contact contact) {
    this.contact = contact;
    return this;
  }

  public ApiInfoBuilder license(String license) {
    this.license = license;
    return this;
  }

  public ApiInfoBuilder licenseUrl(String licenseUrl) {
    this.licenseUrl = licenseUrl;
    return this;
  }

  public ApiInfoBuilder extensions(List<VendorExtension> extensions) {
    this.vendorExtensions.addAll(nullToEmptyList(extensions));
    return this;
  }

  public ApiInfo build() {
    return new ApiInfo(title, description, version, termsOfServiceUrl, contact, license, licenseUrl, vendorExtensions);
  }
}
~~~

### 总结

（1）优点
1、产品的建造和表示分离，实现了解耦。

2、将复杂产品的创建步骤分解在不同的方法中，使得创建过程更加清晰

3、增加新的具体建造者无需修改原有类库的代码，易于拓展，符合“开闭原则“。

（2）缺点
1、产品必须有共同点，限制了使用范围。

 2、如内部变化复杂，会有很多的建造类，难以维护。

（3）应用场景
1、需要生成的产品对象有复杂的内部结构，这些产品对象具备共性；

2、隔离复杂对象的创建和使用，并使得相同的创建过程可以创建不同的产品。

3、需要生成的对象内部属性本身相互依赖。

4、适合于一个具有较多的零件（属性）的产品（对象）的创建过程。



## 4.原型模式：**使用原型实例指定待创建对象的类型，并且通过复制这个原型来创建新的对象**

原型模式：
– 通过new产生一个对象需要非常繁琐的数据准备或访问权限，则可以使用原型模式。
– 就是java中的克隆技术，以某个对象为原型，复制出新的对象。显然，新的对象具备原型对象的特点
– 优势有：效率高(直接克隆，避免了重新执行构造过程步骤) 。
– 克隆类似于new，但是不同于new。new创建新的对象属性采用的是默认值。克隆出的
对象的属性值完全和原型对象相同。并且克隆出的新对象改变不会影响原型对象。然后，
再修改克隆对象的值。
• 原型模式实现：
– Cloneable接口和clone方法
– Prototype模式中实现起来最困难的地方就是内存复制操作，所幸在Java中提供了
clone()方法替我们做了绝大部分事情





![t01091269adf9ca549c](assets/t01091269adf9ca549c.gif)

多重影分身非常合适这个概念，鸣人本体是原型对象！通过“多重影分身术”也就是原型模式（自己本身使用忍术进行创建），进行分身！创建新对象，（分身）。

需要注意的是，创建新“分身”的人就是鸣人！这个意思就是说原型对象自己不仅是个对象还是个工厂！并且通过克隆方式创建的对象是全新的对象，它们都是有自己的新的地址，通常对克隆模式所产生的新对象（影分身）进行修改（攻击）是不会对原型对象（鸣人）造成任何影响的！，每一个克隆对象都是相对独立的，通过不同的方式对克隆对象进行修改后，可以的到一系列相似但不完全相同的对象。（参考多重影分身之色诱术）。



浅克隆存在的问题
– 被复制的对象的所有变量都含有与原来的对象相同的值，而所有的对其他对象的引用都
仍然指向原来的对象。
• 深克隆如何实现?
– 深克隆把引用的变量指向复制过的新对象，而不是原有的被引用的对象。
– 深克隆：让已实现Clonable接口的类中的属性也实现Clonable接口
– 基本数据类型和String能够自动实现深度克隆（值的复制）

1.**浅克隆**

在浅克隆中，如果原型对象的成员变量是值类型（八大基本类型，byte,short,int,long,char,double,float,boolean）.那么就直接复制，如果是复杂的类型，（枚举，String,对象）就只复制对应的内存地址。

![1557796756474](assets/1557796756474.png)

修改了克隆对象的原型对象也会变。他们是共用的。而值类型不是共用的！

2.深克隆

全部复制，然后各自独立。你修改克隆对象对于原型对象没有丝毫影响，完全的影分身！



![1557797152594](assets/1557797152594.png)



需要克隆的类

~~~java
public class Sheep implements Cloneable,Serializable
~~~

关键点在于，实现cloneable接口以及用object的clone方法。

浅克隆

~~~java
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Object obj = super.clone();  //直接调用object对象的clone()方法！
		return obj;
	}

~~~



深克隆

~~~java
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Object obj = super.clone();  //直接调用object对象的clone()方法！
		
		
		//添加如下代码实现深复制(deep Clone)
		Sheep2 s = (Sheep2) obj;
		s.birthday = (Date) this.birthday.clone();  //把属性也进行克隆！
		
		return obj;
	}

~~~



利用序列化和反序列化技术实现深克隆

~~~java
//使用序列化和反序列化实现深复制
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream    oos = new ObjectOutputStream(bos);
		oos.writeObject(s1);
		byte[] bytes = bos.toByteArray();
		
		ByteArrayInputStream  bis = new ByteArrayInputStream(bytes);
		ObjectInputStream	  ois = new ObjectInputStream(bis);
		
		Sheep s2 = (Sheep) ois.readObject();   //克隆好的对象！
~~~



应用场景

短时间大量创建对象时，原型模式和普通new方式效率测试
• 开发中的应用场景
– 原型模式很少单独出现，一般是和工厂方法模式一起出现，通过clone
的方法创建一个对象，然后由工厂方法提供给调用者。
• spring中bean的创建实际就是两种：单例模式和原型模式。（当然，原型
模式需要和工厂模式搭配起来）

## 创建型模式

• 创建型模式：都是用来帮助我们创建对象的！
– 单例模式
• 保证一个类只有一个实例，并且提供一个访问该实例的全局访问点。
– 工厂模式
• 简单工厂模式
– 用来生产同一等级结构中的任意产品。（对于增加新的产品，需要修改已有代码）
• 工厂方法模式
– 用来生产同一等级结构中的固定产品。（支持增加任意产品）
• 抽象工厂模式
– 用来生产不同产品族的全部产品。（对于增加新的产品，无能为力；支持增加产品族）
– 建造者模式
• 分离了对象子组件的单独构造(由Builder来负责)和装配(由Director负责)。 从而可
以构造出复杂的对象。
– 原型模式
• 通过new产生一个对象需要非常繁琐的数据准备或访问权限，则可以使用原型模式



## 5.适配器模式

![1557821244805](assets/1557821244805.png)

什么是适配器模式?
– 将一个类的接口转换成客户希望的另外一个接口。Adapter模式使得原
本由于接口不兼容而不能一起工作的那些类可以在一起工作。
• 模式中的角色
– 目标接口（Target）：客户所期待的接口。目标可以是具体的或抽象
的类，也可以是接口。
– 需要适配的类（Adaptee）：需要适配的类或适配者类。
– 适配器（Adapter）：通过包装一个需要适配的对象，把原接口转换成
目标接口。



![1557821296375](assets/1557821296375.png)

![1557821314096](assets/1557821314096.png)



![1557821345475](assets/1557821345475.png)

![1557821374126](assets/1557821374126.png)

工作中的场景
– 经常用来做旧系统改造和升级
– 如果我们的系统开发之后再也不需要维护，那么很多模式都是没必要
的，但是不幸的是，事实却是维护一个系统的代价往往是开发一个系
统的数倍。
• 学习中见过的场景
– java.io.InputStreamReader(InputStream) – java.io.OutputStreamWriter(OutputStream)



## 6.代理模式

核心作用：
• 通过代理，控制对对象的访问！
可以详细控制访问某个（某类）对象的方法，在调用这个方法前做前置处理，调用这个方法后
做后置处理。（即：AOP的微观实现！）
– AOP(Aspect Oriented Programming面向切面编程)的核心实现机制！

![1557919403471](assets/1557919403471.png)



核心角色：
• 抽象角色
– 定义代理角色和真实角色的公共对外方法
• 真实角色
– 实现抽象角色，定义真实角色所要实现的业务逻辑，
供代理角色调用。
– 关注真正的业务逻辑！
• 代理角色
– 实现抽象角色，是真实角色的代理，通过真实角色
的业务逻辑方法来实现抽象方法，并可以附加
自己的操作。
– 将统一的流程控制放到代理角色中处理！



应用场景：
– 安全代理：屏蔽对真实角色的直接访问。
– 远程代理：通过代理类处理远程方法调用(RMI) – 延迟加载：先加载轻量级的代理对象，真正需要再加载真实对象。
• 比如要开发一个大文档查看软件，大文档中有大的图片，有可能一个图片有100MB，在打开文件时不可能将所有的图片都显示出来，这样就可以
使用代理模式，当需要查看图片时，用proxy来进行大图片的打开。 • 分类：
– 静态代理(静态定义代理类) – 动态代理(动态生成代理类)
• JDK自带的动态代理
• javaassist字节码操作库实现
• CGLIB
• ASM(底层使用指令，可维护性较差)

#### 静态代理

![1557922426048](assets/1557922426048.png)



#### 动态代理

动态代理(动态生成代理类)
• JDK自带的动态代理
• javaassist字节码操作库实现
• CGLIB
• ASM(底层使用指令，可维护性较差)



动态代理相比于静态代理的优点
– 抽象角色中(接口)声明的所以方法都被转移到调用处理器一个集中的方
法中处理，这样，我们可以更加灵活和统一的处理众多的方法。



 JDK自带的动态代理
– java.lang.reflect.Proxy
• 作用：动态生成代理类和对象
– java.lang.reflect.InvocationHandler(处理器接口)
• 可以通过invoke方法实现对真实角色的代理访问。
• 每次通过Proxy生成代理类对象对象时都要指定对应的处理器对象
Star realStar = new RealStar();
StarHandler handler = new StarHandler(realStar);
Star proxy = (Star) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),new
Class[]{Star.class},handler);
proxy.sing();



开发框架中应用场景：
– struts2中拦截器的实现
– 数据库连接池关闭处理
– Hibernate中延时加载的实现
– mybatis中实现拦截器插件
– AspectJ的实现
– spring中AOP的实现
• 日志拦截
• 声明式事务处理
– web service
– RMI远程方法调用