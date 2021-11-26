package com.blossom.alpacapaca.kkokkkogi.Model;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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
    private Boolean online;

    private static boolean isWard;

    // fuck
    private HashMap<String, Chat> chats;

    // 약 배열
    ArrayList<TimeForMedicines> times;

    public Ward() {
        // 싹 밀어야 할 수도 있어
        this.id = getId();
        this.loginEmail = getLoginEmail();
        this.loginPassword = getLoginPassword();
        this.parentId = getParentId();
        this.nameForWard = getNameForWard();
        this.nameForMe = getNameForMe();
        this.born = getBorn();
        //this.chats = new ArrayList<>();
        this.chats = new HashMap<String, Chat>();
        isWard = true;
        this.online = false;

        imageURL = "default";
        this.times = new ArrayList<>();
    }
    // 아이디(받기), 부모아이디(자동), 보여질 이름(받기), 내가 볼 이름(받기), 출생일
    public Ward(String id, String loginEmail, String loginPassword, String parentId, String nameForWard, String nameForMe, String born, HashMap<String, Chat> chats, Boolean online) {
        this.id = id;
        this.loginEmail = loginEmail;
        this.loginPassword = loginPassword;
        this.parentId = parentId;
        this.nameForWard = nameForWard;
        this.nameForMe = nameForMe;
        this.born = born;
        isWard = true;
        this.online = online;
        this.chats = chats;
        // this.wardName = name_for_me + " (" + born.substring(0, 2) + "년생)";
        this.imageURL = "default";
        this.times = new ArrayList<>();
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
        if (times != null) {
            times.add(new TimeForMedicines(str));
        }
        else {
            Log.d("Ward", "Empty!");
        }
    }
    public static boolean isWard() {
        return isWard;
    }
    public ArrayList<TimeForMedicines> getMedicineArray() { return times; }
    public TimeForMedicines getMedicine(int index) { return times.get(index); }

    public int chatsSize() {
        if(this.chats == null) {
            return 0;
        }
        else {
            return this.chats.size();
        }
    }
    public HashMap<String, Chat> getChats() {
        return this.chats;
    }
    public String lastMessage() {
        String key = "";
        HashMap<String, Chat> chatss = getChats();
        if(chatss != null) {
            Iterator<String> iterator = chatss.keySet().iterator();
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                //Log.d("Ward", "laseMessage() 호출: " + chatss.get(key).getMessage());
            }
        }
        String str = chatss.get(key).getMessage();
        //Log.d("Ward", "laseMessage() 호출: " + str);
        return str;
    }
    public void setChats(HashMap<String, Chat> chats) {
        this.chats = chats;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }
}

