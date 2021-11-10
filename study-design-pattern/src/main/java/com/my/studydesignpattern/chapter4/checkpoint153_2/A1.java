package com.my.studydesignpattern.chapter4.checkpoint153_2;

public class A1 {

    public void doA1() {
        A2 a2 = new A2();
        a2.doA2(this);
    }

    public void doIt(A3 a3) {
        a3.doIt();
    }

}
