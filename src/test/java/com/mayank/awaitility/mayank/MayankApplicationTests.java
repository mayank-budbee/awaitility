package com.mayank.awaitility.mayank;

import org.awaitility.core.ConditionEvaluationLogger;
import org.awaitility.core.ConditionTimeoutException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.awaitility.Awaitility.*;

import static java.util.concurrent.TimeUnit.*;
import static org.awaitility.Durations.FIVE_SECONDS;
import static org.hamcrest.Matchers.*;
import static org.awaitility.proxy.AwaitilityClassProxy.*;


//import static com.jayway.awaitility.Awaitlity.*;
//import static        com.jayway.awaitility.Duration.*;
import static java.util.concurrent.TimeUnit.*;
import static org.hamcrest.Matchers.*;
import static org.assertj.core.api.Assertions.*;
import org.awaitility.pollinterval.*;
import static org.awaitility.pollinterval.FibonacciPollInterval.*;
import static org.awaitility.pollinterval.IterativePollInterval.*;

@SpringBootTest
public class MayankApplicationTests {
    Logger log = Logger.getLogger("MayankApplicationTests");

    @Autowired
    CustomSpringEventPublisher customSpringEventPublisher;

    @Autowired
    ApplicationEventMulticaster applicationEventMulticaster;

    @Autowired
    CustomSpringEventListener customSpringEventListener;

    @Test
    public void synchronousMessageListener() {
        customSpringEventPublisher.doStuffAndPublishAnEvent("My message");
        System.out.println(customSpringEventListener.toString());
    }

    @Test
    public void basicWait() {
        applicationEventMulticaster.multicastEvent(new CustomSpringEvent(this, "Asynch message 2"));

        CustomSpringEvent customSpringEvent = new CustomSpringEvent(this, "Asynch message");
        applicationEventMulticaster.multicastEvent(customSpringEvent);


//        1. Basic await, default wait is 10 seconds, throws org.awaitility.core.ConditionTimeoutException after 10 seconds
        await().until(newEventPublished());
    }

    private Callable<Boolean> newEventPublished() {
        return new Callable<Boolean>() {
            public Boolean call() throws Exception {
//                System.out.println("Verifying: " + customSpringEventListener.getSpringEvent() );

                log.info("Verifying: " + customSpringEventListener.getSpringEvent());
                // The condition that must be fulfilled
                return customSpringEventListener.getSpringEvent() != null && customSpringEventListener.getSpringEvent().getMessage().equals("Asynch message");
            }
        };
    }

    @Test
    public void asynchronousMessageListener() {
        applicationEventMulticaster.multicastEvent(new CustomSpringEvent(this, "Asynch message 2"));

        CustomSpringEvent customSpringEvent = new CustomSpringEvent(this, "Asynch message");
        applicationEventMulticaster.multicastEvent(customSpringEvent);


//        1. Basic await, default wait is 10 seconds, throws org.awaitility.core.ConditionTimeoutException after 10 seconds
//        await().until(newEventPublished());

//        2. Changing default timeout
//        await().atMost(5, SECONDS).until(newEventPublished());

//        3. Better reuse
//        await().until( isEventSameAsMessage(), equalTo("Asynch message") );

//        4. Using proxy's, we don't need to create callable, it need additional maven dependency  awaitility-proxy
//        'to' is the main method
//        await().untilCall( to(customSpringEventListener).getSpringEvent(), notNullValue() );

//        5. Fields, static fields don't work
//        await().until( fieldIn(customSpringEventListener).ofType(CustomSpringEvent.class), notNullValue() );
//          or
//        await().until( fieldIn(customSpringEventListener).ofType(CustomSpringEvent.class).andWithName("instanceEvent"), notNullValue() );

//        6. Atomic variables
//        customSpringEventListener.setAtomicInteger(new AtomicInteger(0));
//        applicationEventMulticaster.multicastEvent(new CustomSpringEvent(this, "int"));
//        await().untilAtomic(customSpringEventListener.getAtomicInteger(), equalTo(1));

//        7. Advanced
//        We can choose non fixed poll intervals as well.
//        log.info("Waiting now");
//        with().pollInterval(ONE_HUNDRED_MILLISECONDS).and().with().pollDelay(40, MILLISECONDS).await("event found").until(
//                newEventPublished(), equalTo("Asynch message"));
        log.info("Message received: " + customSpringEventListener.getSpringEvent().getMessage());
    }

    @Test
    public void testWithJava7And8() {
        applicationEventMulticaster.multicastEvent(new CustomSpringEvent(this, "Asynch message 2"));

//        1. Using Lambda expressions
//        await().atMost(5, SECONDS).until(() -> customSpringEventListener.getInstanceEvent().getMessage().equals("Asynch message 2"));

//        2. Method references
//        await().atMost(5, SECONDS).until(customSpringEventListener::isEventReceived);

//        3. combination of method references and Hamcrest matchers
//        await().atMost(5, SECONDS).until(customSpringEventListener::isEventReceived, is(true));

//        4. Using predicate
//        await().atMost(5, SECONDS).until(customSpringEventListener::isEventReceived, isEventReceived -> isEventReceived == true);

//        5. Using assertJ, untilAsserted
//        await().atMost(5, SECONDS).untilAsserted(() -> "s".equals("s"));
//        await().atMost(5, SECONDS).untilAsserted(() -> customSpringEventListener.getInstanceEvent().getMessage().equals("Asynch message 2"));
//        await().atMost(5, SECONDS).untilAsserted(() -> assertThat(customSpringEventListener.getInstanceEvent().getMessage()).isEqualTo("Asynch message 2"));

//        6. Ignoring exceptions
//        customSpringEventListener.getInstanceEvent().getMessage(); This gives null pointer exception
//        given().ignoreExceptions().await().until(() -> customSpringEventListener.getInstanceEvent() != null);

//        7. Ignoring specific exceptions
//        given().ignoreException(IllegalStateException.class).await().until(() -> customSpringEventListener.getInstanceEvent() != null);

//        8. Even runtime exceptions
//        given().ignoreExceptionsMatching(instanceOf(RuntimeException.class)).await().until(() -> customSpringEventListener.getInstanceEvent() != null);

//        9. Using predicate with exceptions
//        given().ignoreExceptionsMatching(e -> e.getMessage().startsWith("Could not find an available")).await().until(newEventPublished());

//        10. Wait until with checked exceptions
//        This throws exception, but code below handles it gracefully
//        try{
//            customSpringEventListener.thisCanThrowException();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        await().untilAsserted(() -> {
//            try {
//                customSpringEventListener.thisCanThrowException();
//            } catch(Exception e) {
//                // Handle exception
//                e.printStackTrace();
//            }
//        });

        try {
        await()
                .atMost(FIVE_SECONDS)
                .until(() -> {
            try {
                    log.info("Executing test for reconnection");
                    return customSpringEventListener.testForReconnection();
                } catch(Exception e) {
                    // Handle exception
                    e.printStackTrace();
                    return false;
                }
            });
        } catch (ConditionTimeoutException ex) {
            throw new RuntimeException("Job failed due to condition timeout.");
        }

        log.info("Message received: " + customSpringEventListener.getInstanceEvent().getMessage());
    }

    @Test
    public void testMoreCode01(){
        applicationEventMulticaster.multicastEvent(new CustomSpringEvent(this, "MyMessage"));
//        await().until( isEventSameAsMessage(), equalTo("MyMessage") );

//        2. Atleast, In case the condition is fulfilled before the duration specified by atLeast an exception is thrown indicating that
//              the condition shouldn't be completed earlier than the specified duration.
//        This will throw exception
//        await().atLeast(1, SECONDS).and().atMost(3, SECONDS).until(isEventSameAsMessage(), equalTo("MyMessage"));
//        This won't throw an exception
//        await().atLeast(1, MICROSECONDS).and().atMost(1, SECONDS).until(isEventSameAsMessage(), equalTo("MyMessage"));

//          3. No catching uncaught exceptions e.g. Thread.sleep from other code
//        await().dontCatchUncaughtExceptions()
//                .atMost(100, MILLISECONDS)
//                .pollInterval(10, MILLISECONDS)
//                .until(customSpringEventListener::isEventReceived);

//        4. Using threads, Note that this is an advanced feature and should be used sparingly.
//        given().pollThread(Thread::new).await().atMost(1000, MILLISECONDS).until(isEventSameAsMessage(), equalTo("MyMessage"));

//        5. Using the same thread
//        This is an advanced feature and you should be careful when combining "pollInSameThread"
//          with conditions that wait forever (or a long time) since Awaitility cannot interrupt
//          the thread when it's using the same thread as the test.
//        with().pollInSameThread().await().atMost(1000, MILLISECONDS).until(isEventSameAsMessage(), equalTo("MyMessage"));

//        6. Using fixed poll interval
        with().pollDelay(100, MILLISECONDS).and().pollInterval(200, MILLISECONDS).await().until(isEventSameAsMessage(), equalTo("MyMessage"));

//        7. Fabonacci poll interval
//        with().pollInterval(fibonacci().with().timeUnit(SECONDS)).await().until(isEventSameAsMessage(), equalTo("MyMessage"));
//        or
//        with().pollInterval(fibonacci()).await().until(isEventSameAsMessage(), equalTo("MyMessage"));
//        or
//        with().pollInterval(fibonacci(SECONDS)).await().until(isEventSameAsMessage(), equalTo("MyMessage"));

//        8. Iterative polling interval
//        await().with().pollInterval(iterative(duration -> duration.multiply(2)).with().startDuration(FIVE_HUNDRED_MILLISECONDS)
//        ).until(isEventSameAsMessage(), equalTo("MyMessage"));

//        9. Custom poll interval
//        await().with().pollInterval((__, previous) -> previous.multiply(2).plus(1)).until(isEventSameAsMessage(), equalTo("MyMessage"));

//        10. Conditional evaluation listener, for logging purpose:
//        with().conditionEvaluationListener(condition ->
//                log.info(condition.getDescription()+" (elapsed time "+condition.getElapsedTimeInMS()+", remaining time "+condition.getRemainingTimeInMS()+")")).
//                await().atMost(Duration.TEN_SECONDS).until(isEventSameAsMessage(), equalTo("MyMessage"));
//        or
//        with().conditionEvaluationListener(new ConditionEvaluationLogger()).await().until(isEventSameAsMessage(), equalTo("MyMessage"));
        with().conditionEvaluationListener(new LoggerWithLogging(log)).await().until(isEventSameAsMessage(), equalTo("MyMessage"));

        log.info("Message received: " + customSpringEventListener.getInstanceEvent().getMessage());
    }

    private Callable<String> isEventSameAsMessage() {
        return new Callable<String>() {
            public String call() throws Exception {
                if (customSpringEventListener.getSpringEvent() != null) {
                    log.info("Msg '" + customSpringEventListener.getSpringEvent().getMessage() + "'");
                    return customSpringEventListener.getSpringEvent().getMessage();
                } else {
                    return "";
                }
            }
        };
    }
}
