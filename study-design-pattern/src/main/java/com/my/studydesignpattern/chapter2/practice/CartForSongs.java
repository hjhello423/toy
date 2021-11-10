package com.my.studydesignpattern.chapter2.practice;

import java.util.ArrayList;
import java.util.List;

public class CartForSongs {

    private List<Song> cart = new ArrayList<>();

    public double calculateTotalPrice() {
        return cart.stream()
            .mapToDouble(Song::getPaymentAmount)
            .sum();
    }
    
}
