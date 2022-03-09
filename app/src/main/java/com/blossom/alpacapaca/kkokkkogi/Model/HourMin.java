package com.blossom.alpacapaca.kkokkkogi.Model;

// 그냥 시간과 분만 갖고있다
public class HourMin {
    private int hour;
    private int min;


    String timeString;
    String hourStr;
    String minStr;

    public HourMin() {}
    public HourMin(int hour, int min) {
        this.hour = hour;
        this.min = min;
        if(hour < 10) {
            hourStr = "0";
        } else {
            hourStr = "";
        }
        hourStr += String.valueOf(hour);
        if(min < 10) {
            minStr = "0";
        } else {
            minStr = "";
        }
        minStr += String.valueOf(min);
        timeString =  hourStr + minStr;
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
    public String getHourString() {
        return hourStr;
    }
    public String getMinString() {
        return minStr;
    }
}
