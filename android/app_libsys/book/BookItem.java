package com.example.app_libsys.book;

import android.os.Parcel;
import android.os.Parcelable;

public class BookItem implements Parcelable {
    private String name;
    private String cate;
    private String publisher;
    private String image;
    private String keyword;
    private String text;
    private String date;
    private int icode;
    private int count;
    private int reviewavg;
    private float rating;


    protected BookItem(Parcel in) {
        name = in.readString();
        cate = in.readString();
        publisher = in.readString();
        image = in.readString();
        keyword = in.readString();
        text = in.readString();
        date = in.readString();
        icode = in.readInt();
        count = in.readInt();
        reviewavg = in.readInt();
        rating = in.readFloat();
    }

    public static final Creator<BookItem> CREATOR = new Creator<BookItem>() {
        @Override
        public BookItem createFromParcel(Parcel in) {
            return new BookItem(in);
        }

        @Override
        public BookItem[] newArray(int size) {
            return new BookItem[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getCate() {
        return cate;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getImage() {
        return image;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public int getIcode() {
        return icode;
    }

    public int getCount() {
        return count;
    }

    public int getReviewavg() {
        return reviewavg;
    }

    public float getRating() {
        return rating;
    }


    public BookItem(String name, String cate, String publisher, String image, String keyword, String text,
                    String date, int icode, int count, int reviewavg, float rating) {
        this.name = name;
        this.cate = cate;
        this.publisher = publisher;
        this.image = image;
        this.keyword = keyword;
        this.text = text;
        this.date = date;
        this.icode = icode;
        this.count = count;
        this.reviewavg = reviewavg;
        this.rating = rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(cate);
        dest.writeString(publisher);
        dest.writeString(image);
        dest.writeString(keyword);
        dest.writeString(text);
        dest.writeString(date);
        dest.writeInt(icode);
        dest.writeInt(count);
        dest.writeInt(reviewavg);
        dest.writeFloat(rating);
    }
}