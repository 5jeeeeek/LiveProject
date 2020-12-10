package com.example.app_libsys.book.review;

public class ReviewItem {
    private String text, nickname, date;
    private float rating;

    public ReviewItem(){
        this.text = null;
        this.nickname = null;
        this.rating = rating;
        this.date = null;
    }
    public void setText(String c){
        this.text = c;
    }
    public String getText(){
        return this.text;
    }
    public void setNickname(String c){
        this.nickname = c;
    }
    public String getNickname(){
        return this.nickname;
    }
    public void setRating(Float rating){
        this.rating = rating;
    }
    public Float getRating(){
        return rating;
    }
    public void setDate(String c){
        this.date = c;
    }
    public String getDate(){
        return this.date;
    }
}
