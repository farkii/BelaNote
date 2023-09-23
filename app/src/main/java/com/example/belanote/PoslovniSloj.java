package com.example.belanote;

import static java.lang.System.in;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PoslovniSloj {
    private static final String TAG = "PoslovniSloj";

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

    public Bundle dohvatiRezultatePartija(){
        Bundle rezultati = new Bundle();
        int mi = 0, vi = 0;
        try {
            dbAdapter.open();
            Cursor cursor = dbAdapter.dohvatiPobjede();
            if (cursor.moveToFirst()) {
                do {
                    if(cursor.getInt(2) == 1){
                        mi++;
                    }else if (cursor.getInt(2) == 2){
                        vi++;
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
            dbAdapter.close();
        }catch (Exception e){
            Toast.makeText(context, "Pogreska kod dohvacanja zapisa", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        rezultati.putInt("mi", mi);
        rezultati.putInt("vi", vi);

        return rezultati;
    }

    public ArrayList<Zapis> dohvatiSveZapise(int partija){
        ArrayList<Zapis> listaZapisa = new ArrayList<Zapis>();

        try {
            dbAdapter.open();
            Cursor cursor = dbAdapter.dohvatiSveZapise(partija);
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

    public boolean obrisiSveZapise(){
        boolean uspjeh = false;
        try{
            dbAdapter.open();
            dbAdapter.obrisiSveZapise();
            dbAdapter.close();
            Toast.makeText(context, "Podaci su uspjesno obrisani", Toast.LENGTH_SHORT).show();
            uspjeh = true;
        }catch (Exception e){
            Toast.makeText(context, "Pogreska kod brisanja zapisa", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return uspjeh;
    }

    public List<String> dohvatiPartije(){
        List<String> partije = new ArrayList<String>();
        partije.add("SVE IGRE");

        try {
            dbAdapter.open();
            Cursor cursor = dbAdapter.dohvatiPartije();
            while(cursor.moveToNext()){
                partije.add(String.valueOf(cursor.getInt(1)) + ". IGRA");
            }
            cursor.close();
            dbAdapter.close();
        }catch (Exception e){
            Toast.makeText(context, "Pogreska kod dohvacanja partije", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        return partije;
    }

    public int dohvatiZadnjuPartiju(){
        int partija = -1;

        try {
            dbAdapter.open();
            Cursor cursor = dbAdapter.dohvatiPartije();
            if(cursor.moveToLast()){
                partija = cursor.getInt(1);
            }
            cursor.close();
            dbAdapter.close();
        }catch (Exception e){
            Toast.makeText(context, "Pogreska kod dohvacanja partije", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return partija;
    }

    public Bundle ukupniBodovi(int partija){
        ArrayList<Zapis> listaZapisa = new ArrayList<Zapis>();
        listaZapisa = dohvatiSveZapise(partija);

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

    private boolean pobjeda(int partija, int tim){
        boolean uspjeh = false;
        try {
            dbAdapter.open();
            uspjeh = dbAdapter.insertPobjednik(partija, tim);
            dbAdapter.close();
        }catch (Exception e){
            Toast.makeText(context, "Pogreska kod unosa pobjednika", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return uspjeh;
    }

    public int provjeraPobjede(int granica, int partija){
        int ukupnoMi  = ukupniBodovi(partija).getInt("mi", 0), ukupnoVi = ukupniBodovi(partija).getInt("vi", 0);
        if(ukupnoMi >= granica || ukupnoVi >= granica){
             if (ukupnoMi > ukupnoVi){
                 pobjeda(partija, 1);
                 Toast.makeText(context, "Pobjedili su MI", Toast.LENGTH_SHORT).show();
             }else if (ukupnoVi > ukupnoMi){
                 pobjeda(partija, 2);
                 Toast.makeText(context, "Pobjedili su VI", Toast.LENGTH_SHORT).show();
             }
        }

        return dohvatiZadnjuPartiju();
    }

    public Bundle izracunajStatistiku(List<Zapis> zapisi){
        Bundle podaci = new Bundle();
        int brojPartija = zapisi.size();
        Bundle zvanjaUkupno = dohvatiUkupnoZvanja(zapisi);
        Bundle zvanjaNajvece = dohvatiNajveceZvanje(zapisi);
        Bundle najcescaBoja = dohvatiNajcescuBoju(zapisi);
        Bundle brojZvanja = dohvatiBrojZvanja(zapisi);
        Bundle brojPadova = dohvatiBrojPadova(zapisi);

        podaci.putInt("brojPartija", brojPartija);
        podaci.putSerializable("najcescaBoja", (Boja) najcescaBoja.get("svi"));
        podaci.putInt("ukupnoZvanjaMi", zvanjaUkupno.getInt("mi"));
        podaci.putInt("ukupnoZvanjaVi", zvanjaUkupno.getInt("vi"));
        podaci.putInt("najveceZvanjeMi", zvanjaNajvece.getInt("mi"));
        podaci.putInt("najveceZvanjeVi", zvanjaNajvece.getInt("vi"));
        podaci.putSerializable("najcescaBojaMi", (Boja) najcescaBoja.get("mi"));
        podaci.putSerializable("najcescaBojaVi", (Boja) najcescaBoja.get("vi"));
        podaci.putInt("brojZvanjaMi", brojZvanja.getInt("mi"));
        podaci.putInt("brojZvanjaVi", brojZvanja.getInt("vi"));
        podaci.putInt("brojPadovaMi", brojPadova.getInt("mi"));
        podaci.putInt("brojPadovaVi", brojPadova.getInt("vi"));

        return podaci;
    }

    private Bundle dohvatiUkupnoZvanja(List<Zapis> zapisi){
        Bundle zvanja = new Bundle();
        int mi = 0, vi = 0;
        for (int i = 0; i < zapisi.size(); i++){
            Zapis zapis = zapisi.get(i);
            mi += zapis.getZvanjaMi();
            vi += zapis.getZvanjaVi();
        }
        zvanja.putInt("mi", mi);
        zvanja.putInt("vi", vi);
        return zvanja;
    }

    private Bundle dohvatiNajveceZvanje(List<Zapis> zapisi){
        Bundle zvanja = new Bundle();
        int mi = 0, vi = 0;
        for (int i = 0; i < zapisi.size(); i++){
            Zapis zapis = zapisi.get(i);
            if (zapis.getZvanjaMi() > mi){
                mi = zapis.getZvanjaMi();
            }
            if (zapis.getZvanjaVi() > vi){
                vi = zapis.getZvanjaVi();
            }
        }
        zvanja.putInt("mi", mi);
        zvanja.putInt("vi", vi);
        return zvanja;
    }

    private Bundle dohvatiNajcescuBoju(List<Zapis> zapisi){
        Bundle boja = new Bundle();
        int pikMi = 0, pikVi = 0, karoMi = 0, karoVi = 0, hercMi = 0, hercVi = 0, trefMi = 0, trefVi = 0;
        for (int i = 0; i < zapisi.size(); i++){
            Zapis zapis = zapisi.get(i);
            if (zapis.getZvao() == Tim.MI) {
                if (zapis.getBoja() == Boja.PIK) {
                    pikMi += 1;
                }
                if (zapis.getBoja() == Boja.KARO) {
                    karoMi += 1;
                }
                if (zapis.getBoja() == Boja.HERC) {
                    hercMi += 1;
                }
                if (zapis.getBoja() == Boja.TREF) {
                    trefMi += 1;
                }
            }

            if (zapis.getZvao() == Tim.VI) {
                if (zapis.getBoja() == Boja.PIK) {
                    pikVi += 1;
                }
                if (zapis.getBoja() == Boja.KARO) {
                    karoVi += 1;
                }
                if (zapis.getBoja() == Boja.HERC) {
                    hercVi += 1;
                }
                if (zapis.getBoja() == Boja.TREF) {
                    trefVi += 1;
                }
            }
        }

        int maxMi = Math.max(pikMi, Math.max(karoMi, Math.max(hercMi, trefMi)));
        int maxVi = Math.max(pikVi, Math.max(karoVi, Math.max(hercVi, trefVi)));
        int max = Math.max(pikVi+pikMi, Math.max(karoVi+karoMi, Math.max(hercVi+hercMi, trefVi+trefMi)));

        Boja bojaMi = null;
        Boja bojaVi = null;
        Boja bojaSvi = null;

        if(maxMi == pikMi){bojaMi = Boja.PIK;}
        else if(maxMi == karoMi){bojaMi = Boja.KARO;}
        else if(maxMi == hercMi){bojaMi = Boja.HERC;}
        else if(maxMi == trefMi){bojaMi = Boja.TREF;}

        if(maxVi == pikVi){bojaVi = Boja.PIK;}
        else if(maxVi == karoVi){bojaVi = Boja.KARO;}
        else if(maxVi == hercVi){bojaVi = Boja.HERC;}
        else if(maxVi == trefVi){bojaVi = Boja.TREF;}

        if(max == pikVi+pikMi){bojaSvi = Boja.PIK;}
        else if(max == karoVi+karoMi){bojaSvi = Boja.KARO;}
        else if(max == hercVi+hercMi){bojaSvi = Boja.HERC;}
        else if(max == trefVi+trefMi){bojaSvi = Boja.TREF;}

        boja.putSerializable("svi", bojaSvi);
        boja.putSerializable("mi", bojaMi);
        boja.putSerializable("vi", bojaVi);

        return boja;
    }

    public Bundle dohvatiBrojZvanja(List<Zapis> zapisi){
        Bundle brojZvanja = new Bundle();
        int mi = 0, vi = 0;
        for (int i = 0; i < zapisi.size(); i++){
            Zapis zapis = zapisi.get(i);
            if (zapis.getZvao() == Tim.MI){
                mi += 1;
            }
            if (zapis.getZvao() == Tim.VI){
                vi += 1;
            }
        }
        brojZvanja.putInt("mi", mi);
        brojZvanja.putInt("vi", vi);
        return brojZvanja;
    }

    public Bundle dohvatiBrojPadova(List<Zapis> zapisi){
        Bundle brojPadova = new Bundle();
        int mi = 0, vi = 0;
        for (int i = 0; i < zapisi.size(); i++){
            Zapis zapis = zapisi.get(i);
            if (zapis.getZvao() == Tim.MI && zapis.isTim_pao()){
                mi += 1;
            }
            if (zapis.getZvao() == Tim.VI && zapis.isTim_pao()){
                vi += 1;
            }
        }
        brojPadova.putInt("mi", mi);
        brojPadova.putInt("vi", vi);
        return brojPadova;
    }

}
