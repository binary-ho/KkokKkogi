package com.blossom.alpacapaca.kkokkkogi.Model;

public class PairInt {

    private int first;
    private int second;

    public PairInt(int first, int second) {
        this.first = first;
        this.second = second;
    }
    // 얘가 있어서 오히려 헷갈릴 수도 있어.
//    public IntPair() {
//        this.first = 0;
//        this.second = 0;
//    }
    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }
}
