package com.blossom.alpacapaca.kkokkkogi.Model;

public class MedicineBox {
    String name;
    String hour;
    String min;
    String time;
    String state;

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

    public String getTime() {
        if(this.time != null) {
            return this.time;
        } else {
            this.time = hour + ":" + min;
            return this.time;
        }
    }

    public void setTime(String time) {
        this.time = time;
    }



    public MedicineBox(String name, String hour, String min) {
        this.name = name;
        this.hour = hour;
        this.min = min;
        time = hour + ":" + min;
        this.state = "false";
    }
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void stateTrue() {
        this.state = "true";
    }

    public void stateFalse() {
        this.state = "false";
    }
}
