package com.example.william.nearsoftpairprogramming2.model;

import android.arch.persistence.room.Entity;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by William on 12/13/2017.
 */


public class ImageLink implements Parcelable {


    private String thumbnail;

    public ImageLink(String thumbnail)
    {
      this.thumbnail = thumbnail;
    }

    protected ImageLink(Parcel in) {
        thumbnail = in.readString();
    }

    public static final Creator<ImageLink> CREATOR = new Creator<ImageLink>() {
        @Override
        public ImageLink createFromParcel(Parcel in) {
            return new ImageLink(in);
        }

        @Override
        public ImageLink[] newArray(int size) {
            return new ImageLink[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(thumbnail);
    }

    public String getThumbnail() {
        return thumbnail;
    }


}
