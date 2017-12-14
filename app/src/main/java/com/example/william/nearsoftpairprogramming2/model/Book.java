package com.example.william.nearsoftpairprogramming2.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by William on 12/13/2017.
 */


public class Book implements Parcelable {

    public Book(){}

    public static Creator<Book> getCREATOR() {
        return CREATOR;
    }

    public String getId() {
        return id;
    }

    public VolumenInfo getVolumeInfo() {
        return volumeInfo;
    }

    protected Book(Parcel in) {
        id = in.readString();
        volumeInfo = in.readParcelable(VolumenInfo.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeParcelable(volumeInfo, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public void setId(String id) {
        this.id = id;
    }

    public void setVolumeInfo(VolumenInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

    private String id;
    private VolumenInfo volumeInfo;






}
