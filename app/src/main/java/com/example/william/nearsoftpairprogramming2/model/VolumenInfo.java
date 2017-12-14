package com.example.william.nearsoftpairprogramming2.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by William on 12/13/2017.
 */

public class VolumenInfo implements Parcelable {

    private  String title;
    private  String description;
    private  String publishedDate;
    private ImageLink imageLinks;
    private List<String> authors;

    public VolumenInfo()
    {

    }

    protected VolumenInfo(Parcel in) {
        title = in.readString();
        description = in.readString();
        publishedDate = in.readString();
        imageLinks = in.readParcelable(ImageLink.class.getClassLoader());
        authors = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(publishedDate);
        dest.writeParcelable(imageLinks, flags);
        dest.writeStringList(authors);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VolumenInfo> CREATOR = new Creator<VolumenInfo>() {
        @Override
        public VolumenInfo createFromParcel(Parcel in) {
            return new VolumenInfo(in);
        }

        @Override
        public VolumenInfo[] newArray(int size) {
            return new VolumenInfo[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public ImageLink getImageLinks() {
        return imageLinks;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setImageLinks(ImageLink imageLinks) {
        this.imageLinks = imageLinks;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }
}
