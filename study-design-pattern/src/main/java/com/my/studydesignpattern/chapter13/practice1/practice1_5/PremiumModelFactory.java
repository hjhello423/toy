package com.my.studydesignpattern.chapter13.practice1.practice1_5;

import com.my.studydesignpattern.chapter13.practice1.practice1_5.abstract_product.GPS;
import com.my.studydesignpattern.chapter13.practice1.practice1_5.abstract_product.Map;
import com.my.studydesignpattern.chapter13.practice1.practice1_5.abstract_product.PathFinder;
import com.my.studydesignpattern.chapter13.practice1.practice1_5.abstract_product.Screen;
import com.my.studydesignpattern.chapter13.practice1.practice1_5.concrete_product.ExpensiveGPS;
import com.my.studydesignpattern.chapter13.practice1.practice1_5.concrete_product.FastPathFinder;
import com.my.studydesignpattern.chapter13.practice1.practice1_5.concrete_product.HDScreen;
import com.my.studydesignpattern.chapter13.practice1.practice1_5.concrete_product.LargeMap;

public class PremiumModelFactory extends Factory {

    @Override
    GPS createGPS() {
        return new ExpensiveGPS();
    }

    @Override
    Map createMap() {
        return new LargeMap();
    }

    @Override
    Screen createScreen() {
        return new HDScreen();
    }

    @Override
    PathFinder createPathFinder() {
        return new FastPathFinder();
    }
}
