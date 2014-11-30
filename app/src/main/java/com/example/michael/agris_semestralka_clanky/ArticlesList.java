package com.example.michael.agris_semestralka_clanky;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Michael on 30.11.2014.
 */
public class ArticlesList extends ArrayList<ArticleItem> implements Parcelable{

    public ArticlesList(){
    }

    public ArticlesList(Parcel in){
        readFromParcel(in);
    }

    @SuppressWarnings("unchecked")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ArticlesList createFromParcel(Parcel in) {
            return new ArticlesList(in);
        }
        public Object[] newArray(int arg0) {
            return null;
        }
    };

    private void readFromParcel(Parcel in) {
        this.clear();
        //First we have to read the list size
        int size = in.readInt();
        //Reading remember that we wrote first the Name and later the Phone Number.
        //Order is fundamental
        for (int i = 0; i < size; i++) {
            ArticleItem article = new ArticleItem();
            article.setId(in.readString());
            article.setHeadline(in.readString());
            article.setPerex(in.readString());
            this.add(article);
        }
    }

    public int describeContents() {
       return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        int size = this.size();
        //We have to write the list size, we need him recreating the list
        dest.writeInt(size);
        //We decided arbitrarily to write first the Name and later the Phone Number.
        for (int i = 0; i < size; i++) {

            ArticleItem article = this.get(i);

            dest.writeString(article.getId());
            dest.writeString(article.getHeadline());
            dest.writeString(article.getPerex());
        }
    }
}
