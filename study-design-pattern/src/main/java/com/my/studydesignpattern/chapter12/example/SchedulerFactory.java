package com.my.studydesignpattern.chapter12.example;

import com.my.studydesignpattern.chapter12.example.Step3.ElevatorScheduler;
import com.my.studydesignpattern.chapter12.example.Step3.ResponseTimeScheduler;
import com.my.studydesignpattern.chapter12.example.Step3.SchedulerStrategyId;
import com.my.studydesignpattern.chapter12.example.Step3.ThroughputScheduler;

public class SchedulerFactory {

    public static ElevatorScheduler getScheduler(SchedulerStrategyId strategyId) {
        ElevatorScheduler scheduler = null;

        switch (strategyId) {
            case RESPONSE_TIME:
                scheduler = new ResponseTimeScheduler();
                break;

            case THROUGHPUT:
                scheduler = new ThroughputScheduler();
                break;
        }
        return scheduler;
    }

}
