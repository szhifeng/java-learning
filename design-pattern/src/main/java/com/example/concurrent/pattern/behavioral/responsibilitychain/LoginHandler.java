package com.example.concurrent.pattern.behavioral.responsibilitychain;

public class LoginHandler extends Handler{
    @Override
    public void doHandler(Member member) {
        System.out.println("登录成功！");
        next.doHandler(member);
    }
}
