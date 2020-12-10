package com.example.app_libsys.book;

import android.graphics.drawable.Drawable;

public class BookListViewItem {
    private Drawable bookImage;
    private String bookName;
    private String bookText;
    private String bookPublisher;

    public void setBookImage(Drawable icon) {
        bookImage = icon ;
    }
    public void setBookName(String name) {
        bookName = name ;
    }
    public void setBookText(String text) {
        bookText = text ;
    }
    public void setBookPublisher(String publisher) {
        bookPublisher = publisher ;
    }

    public Drawable getBookImage() {
        return this.bookImage;
    }
    public String getBookName() {
        return this.bookName;
    }
    public String getBookText() {
        return this.bookText;
    }
    public String getBookPublisher() {
        return this.bookPublisher;
    }
}