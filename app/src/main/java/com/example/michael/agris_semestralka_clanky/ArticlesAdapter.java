package com.example.michael.agris_semestralka_clanky;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 30.11.2014.
 */
public class ArticlesAdapter extends ArrayAdapter<ArticleItem>{
    private ArrayList<ArticleItem> objects;
    public ArticlesAdapter(Context context,int textViewResourceId, List<ArticleItem> objects) {
        super(context, textViewResourceId, objects);
    }
 /// dopsat adapter!!!!
}
