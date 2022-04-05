package com.my.studydesignpattern.chapter11.practice6;

import java.util.Arrays;
import java.util.List;

public class Client {

    public static void main(String[] args) {
        List<Customer> customers = Arrays.asList(
            new Customer("AA", 90),
            new Customer("BB", 100),
            new Customer("CC", 20),
            new Customer("CC", 130));

        Report simpleReport = new SimpleReportGenerator();
        simpleReport.generate(customers);

        Report complexReport = new ComplexReportGenerator();
        complexReport.generate(customers);
    }

}
