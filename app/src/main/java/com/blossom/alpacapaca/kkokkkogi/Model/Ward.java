package com.blossom.alpacapaca.kkokkkogi.Model;

import android.util.Log;

import java.util.ArrayList;

public class Ward {
    // 받을 정보
    private String id;
    private String loginEmail;
    private String loginPassword;
    private String parentId;
    private String nameForWard;
    private String nameForMe;
    private String born;    // 숫자만 + 6글자 제한두기
    private String imageURL;
    private static boolean isWard;

    // 약 배열
    ArrayList<Medicine> medicines;

    public Ward() {
        // 싹 밀어야 할 수도 있어
        this.id = getId();
        this.loginEmail = getLoginEmail();
        this.loginPassword = getLoginPassword();
        this.parentId = getParentId();
        this.nameForWard = getNameForWard();
        this.nameForMe = getNameForMe();
        this.born = getBorn();
        isWard = true;

        imageURL = "default";
        this.medicines = new ArrayList<>();
    }
    // 아이디(받기), 부모아이디(자동), 보여질 이름(받기), 내가 볼 이름(받기), 출생일
    public Ward(String id, String loginEmail, String loginPassword, String parentId, String nameForWard, String nameForMe, String born) {
        this.id = id;
        this.loginEmail = loginEmail;
        this.loginPassword = loginPassword;
        this.parentId = parentId;
        this.nameForWard = nameForWard;
        this.nameForMe = nameForMe;
        this.born = born;
        isWard = true;
        // this.wardName = name_for_me + " (" + born.substring(0, 2) + "년생)";
        this.imageURL = "default";
        this.medicines = new ArrayList<>();
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getLoginEmail() {
        return loginEmail;
    }

    public void setLoginEmail(String loginEmail) {
        this.loginEmail = loginEmail;
    }
    public String getLoginPassword() {
        return loginPassword;
    }
    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }
    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
    public String getNameForWard() {
        return nameForWard;
    }
    public void setNameForWard(String nameForWard) {
        this.nameForWard = nameForWard;
    }
    public String getNameForMe() {
        return nameForMe;
    }
    public void setNameForMe(String nameForMe) {
        this.nameForMe = nameForMe;
    }
    public String getBorn() {
        return born;
    }
    public void setBorn(String born) {
        this.born = born;
    }
    public String getImageURL() {
        return imageURL;
    }

    // 약 추가
    public void addMedicine(String str) {
        if (medicines != null) {
            medicines.add(new Medicine(str));
        }
        else {
            Log.d("Ward", "Empty!");
        }
    }
    public static boolean isWard() {
        return isWard;
    }
    public ArrayList<Medicine> getMedicineArray() { return medicines; }
    public Medicine getMedicine(int index) { return medicines.get(index); }
}

