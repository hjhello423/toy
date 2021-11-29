package com.my.studydesignpattern.chapter6.practice.practice_2;

import org.junit.jupiter.api.Test;

class TicketThreadTest {

    @Test
    void run() {
        for (int i = 0; i < 10; i++) {
            Thread t = new TicketThread(i);
            t.start();
        }
    }

}