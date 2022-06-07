package com.mayank.awaitility.mayank;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public CustomSpringEventPublisher customSpringEventPublisher() {
        return new CustomSpringEventPublisher();
    }

    @Bean(name = "applicationEventMulticaster")
    public ApplicationEventMulticaster simpleApplicationEventMulticaster() {
        SimpleApplicationEventMulticaster eventMulticaster
                = new SimpleApplicationEventMulticaster();

        eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return eventMulticaster;
    }

//    @Bean
//    CustomSpringEventListener customSpringEventListener() {
//        return new CustomSpringEventListener();
//    }

}
