package com.example.concurrent.pattern;

import com.example.concurrent.pattern.behavioral.observer.Sheep;
import com.example.concurrent.pattern.behavioral.observer.Wolf;
import org.junit.jupiter.api.Test;

/**
 * @author szf
 * @describe: 设计模式单元测试
 * @Date 2022/5/31 13:55
 */
public class PatternTest {

    /**
     * 观察者模式适合以下几种情形：<p>
     * 对象间存在一对多关系，一个对象的状态发生改变会影响其他对象；<p>
     * 当一个抽象模型有两个方面，其中一个方面依赖于另一方面时，可将这二者封装在独立的对象中以使它们可以各自独立地改变和复用；<p>
     * 实现类似广播机制的功能，不需要知道具体收听者，只需分发广播，系统中感兴趣的对象会自动接收该广播；<p>
     * 多层级嵌套使用，形成一种链式触发机制，使得事件具备跨域（跨越两种观察者类型）通知。
     */
    @Test
    public void behavioralObserverTest() {
        Wolf wolf = new Wolf("灰太狼");
        wolf.addObserver(new Sheep("喜羊羊"));
        wolf.addObserver(new Sheep("懒羊羊"));
        wolf.addObserver(new Sheep("美羊羊"));
        wolf.addObserver(new Sheep("沸羊羊"));
        wolf.coming("hungry");
    }
}
