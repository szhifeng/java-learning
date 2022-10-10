package com.example.concurrent.pattern.behavioral.observer;

import java.util.EventObject;
import java.util.Observable;

/**
 * @author szf
 * @describe: 行为模式（Behavioral Patterns）中的观察者模式（Observer Pattern）
 * 定义对象间的一种一对多的依赖关系，以便当一个对象的状态发生改变时，所有依赖于它的对象都得到通知并自动刷新。
 * （Define a one-to-many dependency between objects so that when one object changes state all its dependents are notified and updated automatically.）
 * @Date 2022/10/1 14:36
 *
 * <p>使用 java 自带的被观察者接口<p/>
 */
public class Wolf extends Observable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Wolf(String name) {
        this.name = name;
    }

    /**
     * 狼来了，警惕所有🐏
     * `被观察者`状态发生改变，通知依赖于它的`观察者`
     */
    public void coming(String state) {
        System.out.println("大灰狼来了！");
        this.setChanged();
        this.notifyObservers(state);
    }
}
