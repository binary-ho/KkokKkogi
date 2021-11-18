package com.blossom.alpacapaca.kkokkkogi.Model;

public class ChatPreview {

    //private String nameForWard;
    private String nameForMe;
    private String imageURL;

    public ChatPreview() {
        //this.nameForWard = getNameForWard();
        this.nameForMe = getNameForMe();
        imageURL = "default";
    }
    public ChatPreview(String nameForMe) {
        //this.nameForWard = nameForWard;
        this.nameForMe = nameForMe;
        this.imageURL = "default";
    }
    public String getNameForMe() {
        return nameForMe;
    }
    public void setNameForMe(String nameForMe) {
        this.nameForMe = nameForMe;
    }
    public String getImageURL() {
        return imageURL;
    }
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}

