package concurrent.api.basics;

import org.junit.jupiter.api.Test;

public class LoadingTest {

    /**
     * 继承关系执行顺序：
     * 父类静态代码块加载；初始血量：1
     * 子类静态代码块和静态变量被初始化的顺序与代码先后顺序有关
     * 父类普通变量：2；父类普通代码块叠 buf：1
     * 父类构造方法执行：addHP:2;HP 4
     * 子类普通变量：8子类普通代码块修改父类静态普通变量：4
     * 子类构造方法：5
     */
    @Test
    public void test01() {
        Dog dog = new Dog();
    }

    static int num = 4;

    {
        num += 3;
        System.out.println("普通代码块;静态普通变量 num：" + num);           // 2. 普通代码块;静态普通变量 num：6
    }

    int size = 10;

    {
        System.out.println("普通代码块;普通变量 size：" + size++);         // 3. 普通代码块;普通变量 size：10
    }

    LoadingTest() {
        System.out.println("测试类构造方法");                          // 4. 测试类构造方法
    }

    static {
        System.out.println("静态代码块;静态普通变量 num：" + num--);        // 1. 静态代码块;静态普通变量 num：4
    }

    //  注意：静态方法调用才加载
    static void run() {
        System.out.println("静态方法；静态普通变量 num：" + num);            // 5. 静态方法；静态普通变量 num：6
    }

    /**
     * 无继承关系执行顺序：
     * 静态代码块;静态普通变量 num：4
     * 普通代码块;静态普通变量 num：6
     * 普通代码块;普通变量 size：10
     * 测试类构造方法
     * 静态方法；静态普通变量 num：6
     * <p>
     * 注意：最后静态方法调用才加载
     */
    @Test
    public void test02() {
        run();
    }
}
