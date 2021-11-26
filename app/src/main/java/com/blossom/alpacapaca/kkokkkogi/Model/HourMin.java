package com.blossom.alpacapaca.kkokkkogi.Model;

// 그냥 시간과 분만 갖고있다
public class HourMin {
    private int hour;
    private int min;


    String timeString;

    public HourMin() {}
    public HourMin(int hour, int min) {
        this.hour = hour;
        this.min = min;
        timeString = String.valueOf(hour) + String.valueOf(min);
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }
    public String getTimeString() {
        return timeString;
    }

}
