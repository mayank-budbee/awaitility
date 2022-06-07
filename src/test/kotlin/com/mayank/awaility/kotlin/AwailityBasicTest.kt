package com.mayank.awaility.kotlin

import org.awaitility.Awaitility
import org.junit.jupiter.api.Test
import java.util.concurrent.Callable
import java.util.logging.Logger

class AwailityBasicTest {
    var log: Logger = Logger.getLogger("KotlinTests")


    @Test
    fun basicWait() {
//        1. Basic await, default wait is 10 seconds, throws org.awaitility.core.ConditionTimeoutException after 10 seconds
        log.info("Waiting for completion")
        Awaitility.await().until(newEventPublished())
        log.info("completed")
    }

    private fun newEventPublished(): Callable<Boolean?>? {
        return Callable { //                System.out.println("Verifying: " + customSpringEventListener.getSpringEvent() );
            Thread.sleep(4000)
            true == true
        }
    }
}