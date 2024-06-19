package concurrent.api.basics;

import java.io.Serializable;

/**
 * 测试类加载顺序
 * @date 2022-10-09
 * @author szf
 */
public class Animal implements Serializable {

    static int HP = 1;

    static {
        System.out.println("父类静态代码块加载；初始血量："+ HP);          // 1. 父类静态代码块加载；初始血量：1
    }

    int addHP = 2;

    {
        System.out.println("父类普通变量：" + addHP + "；父类普通代码块叠 buf：" + HP++);    // 3.父类普通变量：2；父类普通代码块叠 buf：1
    }

    class Mosquito {
        public Mosquito(){
            addHP--;
            System.out.println("内部类 Mosquito 执行！");     //  没地方调用，不加载
        }
    }

    public Animal() {
        HP = addHP + HP;
        System.out.println("父类构造方法执行：addHP:"+ addHP + ";HP " + HP);     //  4. 父类构造方法执行：addHP:2;HP 4
    }

    public static int getHP() {
        return HP;
    }

    public static void setHP(int HP) {
        Animal.HP = HP;
    }

    public int getAddHP() {
        return addHP;
    }

    public void setAddHP(int addHP) {
        this.addHP = addHP;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "addHP=" + addHP +
                '}';
    }
}

