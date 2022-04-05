package com.my.studydesignpattern.chapter12.practice1.practice1_5;

public class MotorFactory {

    public static Motor getMotor(MotorType type) {
        Motor motor = null;
        switch (type) {
            case LG:
                motor = new LGMotor();
                break;
            case HYUNDAI:
                motor = new HyundaiMotor();
                break;
        }

        return motor;
    }

}
