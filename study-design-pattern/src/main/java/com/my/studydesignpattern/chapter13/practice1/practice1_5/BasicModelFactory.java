package com.my.studydesignpattern.chapter13.practice1.practice1_5;

import com.my.studydesignpattern.chapter13.practice1.practice1_5.abstract_product.GPS;
import com.my.studydesignpattern.chapter13.practice1.practice1_5.abstract_product.Map;
import com.my.studydesignpattern.chapter13.practice1.practice1_5.abstract_product.PathFinder;
import com.my.studydesignpattern.chapter13.practice1.practice1_5.abstract_product.Screen;
import com.my.studydesignpattern.chapter13.practice1.practice1_5.concrete_product.SDScreen;
import com.my.studydesignpattern.chapter13.practice1.practice1_5.concrete_product.SlowPathFinder;
import com.my.studydesignpattern.chapter13.practice1.practice1_5.concrete_product.SmallMap;

public class BasicModelFactory extends Factory {

    @Override
    GPS createGPS() {
        return new CheapGPS();
    }

    @Override
    Map createMap() {
        return new SmallMap();
    }

    @Override
    Screen createScreen() {
        return new SDScreen();
    }

    @Override
    PathFinder createPathFinder() {
        return new SlowPathFinder();
    }
}
