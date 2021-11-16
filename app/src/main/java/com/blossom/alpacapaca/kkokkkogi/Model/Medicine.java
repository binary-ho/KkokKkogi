package com.blossom.alpacapaca.kkokkkogi.Model;

import java.util.ArrayList;

public class Medicine {
    String name;
    // 2개의 인트 hour, minute
    ArrayList<PairInt> times;

    public Medicine(String name) {
        this.name = name;
        this.times = new ArrayList<>();
    }

    public Medicine(String name, int hour, int minute) {
        this.name = name;
        this.times = new ArrayList<>();
        this.addTime(hour, minute);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    // 시간대가 비어있는지 확인 size()로 퉁치자 그냥
    //public boolean isEmpty() { return this.times.size() == 0; }
    public int sizeTime() { return this.times.size(); }
    public ArrayList<PairInt> getTimes() {
        return times;
    }
    public void setTimes(ArrayList<PairInt> times) {
        this.times = times;
    }
    // 시간대 추가
    public void addTime(int hour, int min) {
        this.times.add(new PairInt(hour, min));
    }
    // 삭제
    public void deleteTime(int index) {
        this.times.remove(index);
    }
}
