package com.my.studydesignpattern.chapter13.practice1.practice1_5;

import com.my.studydesignpattern.chapter13.practice1.practice1_5.abstract_product.GPS;
import com.my.studydesignpattern.chapter13.practice1.practice1_5.abstract_product.Map;
import com.my.studydesignpattern.chapter13.practice1.practice1_5.abstract_product.PathFinder;
import com.my.studydesignpattern.chapter13.practice1.practice1_5.abstract_product.Screen;

public abstract class Factory {

    abstract GPS createGPS();

    abstract Map createMap();

    abstract Screen createScreen();

    abstract PathFinder createPathFinder();
}

