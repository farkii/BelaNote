package com.example.belanote;

import static java.lang.System.in;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

import java.util.ArrayList;

public class PoslovniSloj {
    private static PoslovniSloj instance;

    private static Context context;

    private static DataBaseAdapter dbAdapter;


    private PoslovniSloj(){
    }

    static {
        try {
            instance = new PoslovniSloj();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static PoslovniSloj getInstance(Context c) {
        context = c;
        dbAdapter = new DataBaseAdapter(c);
        return instance;
    }

    public ArrayList<Zapis> dohvatiSveZapise(){
        ArrayList<Zapis> listaZapisa = new ArrayList<Zapis>();

        try {
            dbAdapter.open();
            Cursor cursor = dbAdapter.dohvatiSveZapise();
            if (cursor.moveToFirst()) {
                do {
                    Zapis zapis = new Zapis(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(8), cursor.getInt(6), cursor.getInt(7));
                    listaZapisa.add(zapis);
                } while (cursor.moveToNext());
            }
            cursor.close();
            dbAdapter.close();
        }catch (Exception e){
            Toast.makeText(context, "Pogreska kod dohvacanja zapisa", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        return listaZapisa;
    }

    public boolean unesiZapis(int bodoviMi, int bodoviVi, int zvanjaMi, int zvanjaVi, int boja, int zvao, boolean pao, int partija){
        long uspjeh = -1;
        try {
            dbAdapter.open();
            uspjeh = dbAdapter.insertZapis(bodoviMi, bodoviVi, zvanjaMi, zvanjaVi, boja, zvao, pao, partija);
            dbAdapter.close();
        }catch (Exception e){
            Toast.makeText(context, "Pogreska kod unosa zapisa", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return uspjeh != -1;
    }

    public boolean azurirajZapis(Zapis zapis){
        boolean uspjeh = false;
        try {
            dbAdapter.open();
            uspjeh = dbAdapter.updateZapis(zapis.getIdZapis(), zapis.getBodoviMi(), zapis.getBodoviVi(), zapis.getZvanjaMi(), zapis.getZvanjaVi(), zapis.getId_boja(), zapis.getId_zvao(), zapis.isTim_pao(), zapis.getId_partija());
            dbAdapter.close();
        }catch (Exception e){
            Toast.makeText(context, "Pogreska kod azuriranja zapisa", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return uspjeh;
    }

    public Bundle ukupniBodovi(){
        ArrayList<Zapis> listaZapisa = new ArrayList<Zapis>();
        listaZapisa = dohvatiSveZapise();

        int sumMi = 0, sumVi = 0, razlika;
        //TODO dodati jos koliko bodova fali za svaki tim do pobjede nakon sto implementiras postavke


        for(Zapis zapis : listaZapisa){
            sumMi += zapis.getUkupnoMi();
            sumVi += zapis.getUkupnoVi();
        }
        razlika = sumMi - sumVi;
        Bundle ukupnoPodaci = new Bundle();
        ukupnoPodaci.putInt("mi", sumMi);
        ukupnoPodaci.putInt("vi", sumVi);
        ukupnoPodaci.putInt("razlika", razlika < 0 ? razlika*(-1) : razlika);

        return ukupnoPodaci;
    }

}
