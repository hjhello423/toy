package com.my.studydesignpattern.chapter12.practice2.practice2_7;

public class CarStateFactory {

    private Integer a;

    public static CarState getCarState(Type type) {
        CarState carState = null;

        switch (type) {
            case NORMAL:
                carState = new NormalState();
                break;
            case LIMP:
                carState = new LimpState();
                break;
        }

        return carState;
    }


    public enum Type {
        NORMAL, LIMP
    }

}
