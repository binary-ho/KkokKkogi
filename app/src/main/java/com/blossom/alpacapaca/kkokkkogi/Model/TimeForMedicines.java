package com.blossom.alpacapaca.kkokkkogi.Model;

public class TimeForMedicines {
    String time;
    String hour;
    String min;
    String state;
    String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWardId() {
        return wardId;
    }

    public void setWardId(String wardId) {
        this.wardId = wardId;
    }

    String wardId;

    public TimeForMedicines(String time, String hour, String min, String user, String ward) {
        this.time = time;
        this.hour = hour;
        this.min = min;
        this.userId = user;
        this.wardId = ward;
        state = "false";
    }


    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
//    // 시간대가 비어있는지 확인 size()로 퉁치자 그냥
//    //public boolean isEmpty() { return this.times.size() == 0; }
//    public int sizeTimes() { return this.times.size(); }
//    public ArrayList<PairInt> getTimes() {
//        return times;
//    }
//    public void setTimes(ArrayList<PairInt> times) {
//        this.times = times;
//    }
//    // 시간대 추가
//    public void addTime(int hour, int min) {
//        this.times.add(new PairInt(hour, min));
//    }
//    // 삭제
//    public void deleteTime(int index) {
//        this.times.remove(index);
//    }
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
