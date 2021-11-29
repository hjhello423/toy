package com.my.studydesignpattern.chapter6.practice.practice_2;

public class Ticket {

    private static int serial = 0;

    public Ticket() {
        validate(serial + 1);
        serial++;
    }

    private void validate(int serialNo) {
        if (serialNo < 0 || serialNo == 0) {
            throw new RuntimeException("잘못된 시리얼");
        }
    }

    @Override
    public String toString() {
        return String.format("Ticket{%d}", serial);
    }

}
