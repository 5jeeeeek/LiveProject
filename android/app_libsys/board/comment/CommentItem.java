package com.example.app_libsys.board.comment;

public class CommentItem {
    private String content, nickname, date;
    private int check;

    public CommentItem(){
        this.content = null;
        this.nickname = null;
        this.date = null;
        this.check = check;
    }
    public void setContent(String c){
        this.content = c;
    }
    public String getContent(){
        return this.content;
    }
    public void setNickname(String c){
        this.nickname = c;
    }
    public String getNickname(){
        return this.nickname;
    }
    public void setDate(String c){
        this.date = c;
    }
    public String getDate(){
        return this.date;
    }
    public void setCheck(int check){
        this.check = check;
    }
    public int getCheck(){
        return check;
    }

}
