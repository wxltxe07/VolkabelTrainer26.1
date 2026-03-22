package com.example.vokabeltrainer261;

import android.content.Context;

public class Vocab {
    private int id;
    private String german;
    private String other;

    public Vocab(int id, String german, String other) {
        this.id = id;
        this.german = german;
        this.other = other;
    }

    public int getId() { return id; }
    public String getGerman() { return german; }
    public String getOther() { return other; }

    public void setGerman(String g) { german = g; }
    public void setOther(String o) { other = o; }
}