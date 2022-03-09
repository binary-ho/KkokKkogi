package com.blossom.alpacapaca.kkokkkogi.Model;

public class User {
    private String id;
    private String username;
    private String imageURL;    // 이미지 url
    private Boolean online;

    private String loginEmail;
    private String loginPassword;

    private String startHour;
    private String startMin;
    private String endHour;
    private String endMin;

    //private int numWard;
    private static boolean isWard;

    public User(String id, String username, String loginEmail, String loginPassword, String imageURL, Boolean online) {
        this.id = id;
        this.username = username;
        this.imageURL = imageURL;
        //numWard = 0;
        this.loginEmail = loginEmail;
        this.loginPassword = loginPassword;
        isWard = false;
        //this.isWard = new IsWard(true);
        this.online = online;
        this.startHour = "09";
        this.startMin = "00";
        this.endHour = "18";
        this.endMin = "00";
    }
    public User() {}
    public String getId() {
        return id;
    }
    public void setId (String id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getImageURL(){
        return imageURL;
    }
    public void setImageURL(String url) {
        this.imageURL = url;
    }
//    public int getNumWard() { return numWard; }
//    // 늘리기
//    public void addWard() { this.numWard++; }
//    public void subtractWard() { this.numWard--; }
    public static boolean isWard() {
        return isWard;
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

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }
    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getStartMin() {
        return startMin;
    }

    public void setStartMin(String startMin) {
        this.startMin = startMin;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public String getEndMin() {
        return endMin;
    }

    public void setEndMin(String endMin) {
        this.endMin = endMin;
    }

}
