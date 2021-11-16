package com.blossom.alpacapaca.kkokkkogi.Model;

public class User {
    private String id;
    private String username;
    private String imageURL;    // 이미지 url
    private int numWard;
    private boolean isWard;

    public User(String id, String username, String imageURL) {
        this.id = id;
        this.username = username;
        this.imageURL = imageURL;
        numWard = 0;
        isWard = false;
        //this.isWard = new IsWard(true);
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
    public int getNumWard() { return numWard; }
    // 늘리기
    public void addWard() { this.numWard++; }
    public void subtractWard() { this.numWard--; }
    public boolean isWard() {
        return this.isWard;
    }
}
