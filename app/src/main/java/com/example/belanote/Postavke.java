package com.example.belanote;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class Postavke {
    private static final String PREFERENCE = "postavke";
    private static final int mod = MODE_PRIVATE;
    private Context context;

    public Postavke(Context c) {
        this.context = c;
    }

    public void dodajGranicu(int granica){
        SharedPreferences postavke = context.getSharedPreferences(PREFERENCE, mod);

        SharedPreferences.Editor editor = postavke.edit();

        editor.putInt("granica", granica);
        editor.commit();
    }

    public int dohvatiGranicu(){
        SharedPreferences postavke = context.getSharedPreferences(PREFERENCE, mod);

        return postavke.getInt("granica", -1);
    }

}
