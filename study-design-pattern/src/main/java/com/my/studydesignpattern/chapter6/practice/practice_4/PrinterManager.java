package com.my.studydesignpattern.chapter6.practice.practice_4;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class PrinterManager {

    private static List<Printer> printers;

    private PrinterManager() {
        this.printers = Collections.unmodifiableList(
            Arrays.asList(new Printer(), new Printer(), new Printer()));
    }

    public static PrinterManager getInstance() {
        return PrinterManagerClazz.INSTANCE;
    }

    public Printer getPrinter() {
        for (Printer printer: printers) {
            if (printer.isAvailable()) {
                printer.unavailable();
                return printer;
            }
        }
        throw new RuntimeException("사용가능 프린터 없음");
    }

    private static class PrinterManagerClazz {

        private static final PrinterManager INSTANCE = new PrinterManager();

    }


}
