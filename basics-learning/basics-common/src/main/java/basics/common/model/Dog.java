package basics.common.model;

/**
 * 测试类加载顺序
 * @date 2022-10-09
 * @author szf
 */
public class Dog extends Animal {

    static {
        System.out.println("子类静态代码块和静态变量被初始化的顺序与代码先后顺序有关");         // 2. 子类静态代码块和静态变量被初始化的顺序与代码先后顺序有关
    }
    int size = 8;

    {
        System.out.println("子类普通变量：" + size + "子类普通代码块修改父类静态普通变量：" + HP++);     // 5. 子类普通变量：8子类普通代码块修改父类静态普通变量：4
    }

    public Dog() {
        System.out.println("子类构造方法：" + HP);     // 6. 子类构造方法：5
    }
}
