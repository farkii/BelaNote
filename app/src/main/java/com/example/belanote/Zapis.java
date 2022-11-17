package com.example.belanote;


import android.util.Log;

public class Zapis {
    private static final String TAG = "Zapis";

    private int idZapis, bodoviMi, bodoviVi, zvanjaMi, zvanjaVi, id_partija, id_boja, id_zvao, ukupnoMi, ukupnoVi;
    private boolean tim_pao;

    private Boja boja;
    private Tim zvao;

    // TODO dodati jos podatke o tome tko je zvao, koji je adut bio itd.


    public Zapis(int idZapis, int bodoviMi, int bodoviVi, int zvanjaMi, int zvanjaVi, int id_partija, int id_boja, int id_zvao, boolean tim_pao) {
        this.idZapis = idZapis;
        this.bodoviMi = bodoviMi;
        this.bodoviVi = bodoviVi;
        this.zvanjaMi = zvanjaMi;
        this.zvanjaVi = zvanjaVi;
        this.id_partija = id_partija;
        this.id_boja = id_boja;
        this.id_zvao = id_zvao;
        this.tim_pao = tim_pao;
        this.ukupnoMi = bodoviMi + zvanjaMi;
        this.ukupnoVi = bodoviVi + zvanjaVi;

        switch(id_boja){
            case 1:{
                boja = Boja.TREF;
                break;
            }
            case 2:{
                boja = Boja.PIK;
                break;
            }
            case 3:{
                boja = Boja.HERC;
                break;
            }
            case 4:{
                boja = Boja.KARO;
                break;
            }
            default:{
                Log.w(TAG, "BOJA: id_boja se nije preslikala ni u jedan enum");
                break;
            }
        }

        switch(id_zvao){
            case 1:{
                zvao = Tim.MI;
                break;
            }
            case 2:{
                zvao = Tim.VI;
                break;
            }
            default:{
                Log.w(TAG, "ZVAO: id_zvao se nije preslikao ni u jedan enum");
                break;
            }
        }
    }

    public int getZvanjaMi() {
        return zvanjaMi;
    }

    public void setZvanjaMi(int zvanjaMi) {
        this.zvanjaMi = zvanjaMi;
    }

    public int getZvanjaVi() {
        return zvanjaVi;
    }

    public void setZvanjaVi(int zvanjaVi) {
        this.zvanjaVi = zvanjaVi;
    }

    public int getId_partija() {
        return id_partija;
    }

    public void setId_partija(int id_partija) {
        this.id_partija = id_partija;
    }

    public boolean isTim_pao() {
        return tim_pao;
    }

    public void setTim_pao(boolean tim_pao) {
        this.tim_pao = tim_pao;
    }

    public int getBodoviMi() {
        return bodoviMi;
    }

    public void setBodoviMi(int bodoviMi) {
        this.bodoviMi = bodoviMi;
    }

    public int getBodoviVi() {
        return bodoviVi;
    }

    public void setBodoviVi(int bodoviVi) {
        this.bodoviVi = bodoviVi;
    }

    public Boja getBoja() {
        return boja;
    }

    public void setBoja(Boja boja) {
        this.boja = boja;
    }

    public Tim getZvao() {
        return zvao;
    }

    public void setZvao(Tim zvao) {
        this.zvao = zvao;
    }

    public int getId_boja() {
        return id_boja;
    }

    public void setId_boja(int id_boja) {
        this.id_boja = id_boja;
    }

    public int getId_zvao() {
        return id_zvao;
    }

    public void setId_zvao(int id_zvao) {
        this.id_zvao = id_zvao;
    }

    public int getUkupnoMi() {
        return ukupnoMi;
    }

    public int getUkupnoVi() {
        return ukupnoVi;
    }

    public int getIdZapis() {
        return idZapis;
    }
}
