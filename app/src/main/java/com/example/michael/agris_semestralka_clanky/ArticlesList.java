package com.example.michael.agris_semestralka_clanky;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 30.11.2014.
 */
public class ArticlesList{
    public static List<ArticleItem> ITEMS = new ArrayList<ArticleItem>();

    public static void addArticleItem(ArticleItem item) {
        ITEMS.add(item);
    }

    public static class ArticleItem{
        public String id;
        public String headline;
        public String perex;

        public ArticleItem(String id, String headline, String perex) {
            this.id = id;
            this.headline = headline;
            this.perex = perex;
        }

        @Override
        public String toString() {
            return headline;
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
    }
}
