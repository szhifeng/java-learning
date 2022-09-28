package com.example.concurrent.pattern;

import com.example.concurrent.pattern.behavioral.observer.DefaultObserved;
import com.example.concurrent.pattern.behavioral.observer.Observer;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Future;

/**
 * @author szf
 * @describe: 设计模式单元测试
 * @Date 2022/5/31 13:55
 */
public class PatternTest {

    @Test
    public void behavioralObserverTest() {
        
        DefaultObserved observed = new DefaultObserved();
        observed.addListener(new Observer<Future<? super Void>>() {
            @Override
            public void notify(String message) {
                System.out.println("测试数据！" + message);
            }
        });
    }
}
