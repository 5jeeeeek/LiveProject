package com.example.app_libsys.note;

public class MessageListViewItem {
    private String titleStr ;
    private String descStr ;
    private String userIdStr ;

    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setDesc(String desc) {
        descStr = desc ;
    }
    public void setUserId(String userId) {
        userIdStr = userId ;
    }

    public String getTitle() {
        return this.titleStr ;
    }
    public String getDesc() {
        return this.descStr ;
    }
    public String getUserId() {
        return this.userIdStr ;
    }
}
