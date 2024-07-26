package com.example.concurrent.pattern.behavioral.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * @author szf
 * @describe: 行为模式（Behavioral Patterns）中的观察者模式（Observer Pattern）
 * 定义对象间的一种一对多的依赖关系，以便当一个对象的状态发生改变时，所有依赖于它的对象都得到通知并自动刷新。
 * （Define a one-to-many dependency between objects so that when one object changes state all its dependents are notified and updated automatically.）
 * @Date 2022/10/1 14:36
 *
 * <p>使用 java 自带的观察者接口<p/>
 */
public class Sheep implements Observer {

    private String name;
    private String state = "eating";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Sheep(String name) {
        this.name = name;
    }

    /**
     * `观察者`刷新自身状态动作
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     *            method.
     */
    @Override
    public void update(Observable o, Object arg) {
        Wolf wolf = (Wolf) o;
        setState("running");
        System.out.println(wolf.getName() + " coming and " + arg + " ，" + this.getName() + " " + this.getState());

    }
}
