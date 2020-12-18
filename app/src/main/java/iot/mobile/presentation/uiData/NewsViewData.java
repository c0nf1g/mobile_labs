package iot.mobile.presentation.uiData;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsViewData implements Parcelable {
    private String author;
    private String title;
    private String description;
    private String content;

    private String photoUrl;

    public NewsViewData(String author, String title, String description,
                        String photoUrl, String content) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.content = content;
        this.photoUrl = photoUrl;
    }

    public NewsViewData(Parcel in) {

        author = in.readString();
        title = in.readString();
        description = in.readString();
        content = in.readString();
        photoUrl = in.readString();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public static final Creator<NewsViewData> CREATOR = new Parcelable.Creator<NewsViewData>() {
        @Override
        public NewsViewData createFromParcel(Parcel in) {
            return new NewsViewData(in);
        }

        @Override
        public NewsViewData[] newArray(int size) {
            return new NewsViewData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(content);
        dest.writeString(photoUrl);
    }
}
