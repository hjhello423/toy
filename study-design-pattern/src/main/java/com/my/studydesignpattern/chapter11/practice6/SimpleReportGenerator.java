package com.my.studydesignpattern.chapter11.practice6;

import java.util.List;

public class SimpleReportGenerator extends Report {


    @Override
    public String statisticCustomerPoint(List<Customer> customers) {
        StringBuilder builder = new StringBuilder();

        for (Customer customer : customers) {
            builder.append(String.format("이름: %s, 점수: %d \n", customer.getName(), customer.getPoint()));
        }

        return builder.toString();
    }

}
