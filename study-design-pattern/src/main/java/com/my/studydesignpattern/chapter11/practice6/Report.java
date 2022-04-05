package com.my.studydesignpattern.chapter11.practice6;

import java.util.List;

public abstract class Report {

    public void generate(List<Customer> customers) {
        System.out.println(statisticCustomer(customers));
        System.out.println(statisticCustomerPoint(customers));
    }

    public String statisticCustomer(List<Customer> customers) {
        return String.format("고객 수 : %d명입니다.", customers.size());
    }

    public abstract String statisticCustomerPoint(List<Customer> customers);

}
