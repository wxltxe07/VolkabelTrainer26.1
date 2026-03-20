package com.example.vokabeltrainer261;


import android.content.Context;

public class Vocab {

    private String other;
    private String german;

    public Vocab(Context context, int id){
        DbHelper db = new DbHelper(context);
        other = db.readOther(id);
        german = db.readGerman(id);
    }

    public String getOther(){
        return other;
    }

    public String getGerman(){

        return german;
    }

    public void setGerman(String g){
        german = g;
    }

    public void setOther(String o){
        german = o;
    }


}
