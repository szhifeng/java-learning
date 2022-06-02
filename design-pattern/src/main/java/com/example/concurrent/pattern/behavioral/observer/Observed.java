package com.example.concurrent.pattern.behavioral.observer;

import java.util.concurrent.Future;

/**
 * @author szf
 * @describe: 行为模式（Behavioral Patterns）中的观察者模式（Observer Pattern）
 * 定义对象间的一种一对多的依赖关系，以便当一个对象的状态发生改变时，所有依赖于它的对象都得到通知并自动刷新。
 * （Define a one-to-many dependency between objects so that when one object changes state all its dependents are notified and updated automatically.）
 * @Date 2022/6/1 14:36
 *
 * <p>抽象被观察者接口:声明了添加、删除、通知观察者方法<p/>
 */
public interface Observed {

    Observed addListener(Observer<? extends Future<? super Void>> listener);

    Observed removeListener(Observer<? extends Future<? super Void>> listener);
}
