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

    public ArrayList<Zapis> dohvatiSveZapise(){
        ArrayList<Zapis> listaZapisa = new ArrayList<Zapis>();

        dbAdapter.open();
        Cursor cursor = dbAdapter.dohvatiSveZapise();
        if(cursor.moveToFirst()){
            do {
                Zapis zapis = new Zapis(cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4),cursor.getInt(8), cursor.getInt(6), cursor.getInt(7), cursor.getInt(5) == 1?true:false);
                listaZapisa.add(zapis);
            }while (cursor.moveToNext());
        }
        cursor.close();
        dbAdapter.close();

        return listaZapisa;
    }

    public boolean unesiZapis(Zapis zapis){
        dbAdapter.open();
        long uspjeh = dbAdapter.insertZapis(zapis.getBodoviMi(),zapis.getBodoviVi(),zapis.getZvanjaMi(),zapis.getZvanjaVi(),zapis.getId_boja(),zapis.getId_zvao(),zapis.isTim_pao(),zapis.getId_partija());
        dbAdapter.close();

        if(uspjeh == -1){
            return false;
        }
        return true;
    }

}
