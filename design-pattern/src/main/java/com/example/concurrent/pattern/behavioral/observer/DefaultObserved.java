package com.example.concurrent.pattern.behavioral.observer;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author szf
 * @describe: DOTO
 * @Date 2022/6/1 14:48
 * @Copyright: 2022 http://www.gzwison.com/ Inc. All rights reserved.
 * 注意：本内容仅限于广州智臣科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class DefaultObserved implements Observed {

    /**
     * 观察者集合
     */
    private List<Observer<? extends Future<?>>> observers;

    /**
     * 线程同步(这一点)，如果执行器发生变化，我们必须防止并发通知和FIFO侦听器通知。
     */
    private boolean notifyingListeners;

    private String message = "推送的消息！";

    @Override
    public Observed addListener(Observer<? extends Future<? super Void>> listener) {
        if (null == observers) {
            observers = Arrays.asList(listener);
        } else {
            observers.add(listener);
        }
        notifyObservers();
        return this;
    }

    @Override
    public Observed removeListener(Observer<? extends Future<? super Void>> listener) {
        if (!observers.isEmpty()) {
            observers.remove(listener);
        }
        return this;
    }


    protected void notifyObservers() {
        List<Observer<? extends Future<?>>> ob;
        synchronized (this) {
            if (notifyingListeners || this.observers == null) {
                return;
            }
            notifyingListeners = true;
            ob = this.observers;
            this.observers = null;
        }
        for (; ; ) {
            ob.forEach(v -> v.notify(message));
            synchronized (this) {
                if (this.observers == null) {
                    notifyingListeners = false;
                    return;
                }
                ob = this.observers;
                this.observers = null;
            }
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
