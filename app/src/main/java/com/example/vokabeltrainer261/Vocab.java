package com.example.vokabeltrainer261;

import android.content.Context;

public class Vocab {
    private int id;
    private String german;
    private String other;

    private int score;



    public Vocab(int id, String german, String other, Context context) {
        DbHelper db = new DbHelper(context);
        this.id = id;
        this.german = german;
        this.other = other;
        this.score = db.readScore(id);
    }

    public int getId() {
        return id;
    }
    public String getGerman() {
        return german;
    }
    public String getOther() {
        return other;
    }

    public int getScore() {
        return score;
    }

    public void setGerman(String g) {
        german = g;
    }
    public void setOther(String o) {
        other = o;
    }
}