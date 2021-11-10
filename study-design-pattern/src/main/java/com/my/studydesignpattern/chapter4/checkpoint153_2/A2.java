package com.my.studydesignpattern.chapter4.checkpoint153_2;

public class A2 {

    private A3 a3;

    public void doA2(A1 a1) {
        A3 a3 = new A3();
        a1.doIt(a3);
    }

//    private boolean doIt(A3 a3) {
//        return true;
//    }

}
