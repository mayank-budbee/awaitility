package com.mayank.awaitility.mayank;

import org.awaitility.core.ConditionEvaluationLogger;
import org.awaitility.core.EvaluatedCondition;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class LoggerWithLogging extends ConditionEvaluationLogger {
    private Logger log;

    public LoggerWithLogging() {
        super();
    }

    public LoggerWithLogging(Logger log) {
        super();
        this.log = log;
    }

    @Override
    public void conditionEvaluated(EvaluatedCondition<Object> condition) {
//        super.conditionEvaluated(condition);
//        log.info(condition.getDescription()+" (elapsed time "+condition.getElapsedTimeInMS()+", remaining time "+condition.getRemainingTimeInMS()+")");
        log.info("(elapsed time "+condition.getElapsedTimeInMS()+", remaining time "+condition.getRemainingTimeInMS()+")");
//        System.out.println("--------------------------------");
    }

    public LoggerWithLogging(TimeUnit timeUnit) {
        super(timeUnit);
    }


}
