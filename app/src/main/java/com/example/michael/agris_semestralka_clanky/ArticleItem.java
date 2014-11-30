package com.example.michael.agris_semestralka_clanky;

/**
 * Created by Michael on 30.11.2014.
 */
public class ArticleItem{
    public String id;
    public String headline;
    public String perex;

    public ArticleItem(){
    }

    public ArticleItem(String id, String headline, String perex) {
        this.id = id;
        this.headline = headline;
        this.perex = perex;
    }

    public String getId() {
        return id;
    }

    public String getHeadline() {
        return headline;
    }

    public String getPerex() {
        return perex;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public void setPerex(String perex) {
        this.perex = perex;
    }
@Override
        public String toString(){
        return headline;
    }
}