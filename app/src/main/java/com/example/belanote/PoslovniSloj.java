package com.example.belanote;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;

public class PoslovniSloj {
    private static PoslovniSloj instance;

    private static DataBaseAdapter dbAdapter;

    private PoslovniSloj(){
    }

    // static block initialization for exception handling
    static {
        try {
            instance = new PoslovniSloj();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static PoslovniSloj getInstance(Context context) {
        dbAdapter = new DataBaseAdapter(context);
        return instance;
    }

    public ArrayList<Zapis> dohvatiSveZapise(Context context){
        ArrayList<Zapis> listaZapisa = new ArrayList<Zapis>();

        dbAdapter.open();
        Cursor cursor = dbAdapter.dohvatiSveZapise();
        if(cursor.moveToFirst()){
            do {
                //Zapis zapis = new Zapis();
            }while (cursor.moveToNext());
        }
        cursor.close();
        dbAdapter.close();

        return listaZapisa;
    }

}
