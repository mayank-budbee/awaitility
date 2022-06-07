package com.mayank.awaitility.mayank;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;


@Component
public class CustomSpringEventListener implements ApplicationListener<CustomSpringEvent> {
    private static CustomSpringEvent springEvent;
    private CustomSpringEvent instanceEvent;
    private AtomicInteger atomicInteger;
    private boolean eventReceived;

    public boolean isEventReceived() {
        return eventReceived;
    }

    @Override
    public void onApplicationEvent(CustomSpringEvent event) {
        System.out.println("Received spring custom event - " + event.getMessage());
        springEvent = event;
        this.instanceEvent = event;
        this.eventReceived = false;
        if(event.getMessage().equals("int")){
            this.atomicInteger.set(1);
        }
    }

    public CustomSpringEvent getSpringEvent() {
        return springEvent;
    }

    public CustomSpringEvent getInstanceEvent() {
        return instanceEvent;
    }

    public AtomicInteger getAtomicInteger() {
        return atomicInteger;
    }

    public void setAtomicInteger(AtomicInteger atomicInteger) {
        this.atomicInteger = atomicInteger;
    }

    public boolean thisCanThrowException() throws Exception {
        if(false == eventReceived){
            throw new Exception("Feed me some exceptions.");
        }else{
            return true;
        }
    }

//    method returns  string,exception,string
    private Integer i = 0;
    public boolean testForReconnection() throws Exception {
        Thread.sleep(3000);
        i++;
        switch (i){
            case 1:
                return false;
            case 2:
                throw new Exception("dummy exception");
            case 3:
                return true;
            default:
                break;
        }
        return true;
    }
}

