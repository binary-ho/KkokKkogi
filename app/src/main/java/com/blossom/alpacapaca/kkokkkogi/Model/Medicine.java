package com.blossom.alpacapaca.kkokkkogi.Model;

public class Medicine {

    String name;
    String hour;
    String min;

    public Medicine(String name, String hour, String min) {
        this.name = name;
        this.hour =hour;
        this.min = min;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }
}
