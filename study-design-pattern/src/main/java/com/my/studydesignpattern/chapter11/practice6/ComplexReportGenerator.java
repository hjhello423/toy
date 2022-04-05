package com.my.studydesignpattern.chapter11.practice6;

import java.util.List;

public class ComplexReportGenerator extends Report {

    @Override
    public String statisticCustomerPoint(List<Customer> customers) {
        StringBuilder builder = new StringBuilder();

        customers.stream()
            .filter(it -> it.isOverLimitPoint(100))
            .forEach(it -> {
                builder.append(String.format("이름: %s, 점수: %d \n", it.getName(), it.getPoint()));
            });

        return builder.toString();
    }

    @Override
    public String statisticCustomer(List<Customer> customers) {
        long count = customers.stream()
            .filter(it -> it.isOverLimitPoint(100))
            .count();
        return String.format("고객 수 : %d명입니다.", count);
    }

}
