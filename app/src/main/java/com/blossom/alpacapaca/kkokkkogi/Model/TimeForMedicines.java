package com.blossom.alpacapaca.kkokkkogi.Model;

public class TimeForMedicines {
    String time;

    public TimeForMedicines(String time) {
        this.time = time;
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
}
