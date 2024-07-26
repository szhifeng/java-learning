package com.example.concurrent.pattern.behavioral.responsibilitychain;

/**
 * @author szf
 * @describe: 行为模式（Behavioral Patterns）中的责任链（Chain of Responsibility）模式 <p>
 * 把请求从链中的一个对象传到下一个对象，直到请求被响应为止。通过这种方式去除对象之间的耦合。
 * （Define a one-to-many dependency between objects so that when one object changes state all its dependents are notified and updated automatically.）
 * @Date 2022/10/10
 *
 */
public class LoginHandler extends Handler{
    @Override
    public void doHandler(Member member) {
        System.out.println("登录成功！");
        next.doHandler(member);
    }
}
