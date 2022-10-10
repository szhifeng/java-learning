package com.example.concurrent.pattern.behavioral.responsibilitychain;

public abstract class Handler<T> {

    protected Handler next;

    public void next(Handler next){ this.next = next;}

    public abstract void doHandler(Member member);
}
