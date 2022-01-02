package com.my.studydesignpattern.chapter9.practice1;

public class Client {

    public static void main(String[] args) {
        Battery battery = new Battery();

        BatteryLevelDisplay batteryLevelDisplay = new BatteryLevelDisplay(battery);
        LowBatteryWarning lowBatteryWarning = new LowBatteryWarning(battery);

        battery.attach(batteryLevelDisplay);
        battery.attach(lowBatteryWarning);

        battery.consume(10);
        battery.consume(30);
        battery.consume(50);
    }

}
