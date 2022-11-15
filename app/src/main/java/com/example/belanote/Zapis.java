package com.example.belanote;



public class Zapis {
    private int bodoviMi, bodoviVi, zvanjaMi, zvanjaVi, id_partija, id_boja, id_zvao;
    private boolean tim_pao;
    private enum Boja {
        TREF,
        PIK,
        HERC,
        KARO
    }
    private enum Tim {
        MI,
        VI
    }

    // TODO dodati jos podatke o tome tko je zvao, koji je adut bio itd.


    public Zapis(int bodoviMi, int bodoviVi, int zvanjaMi, int zvanjaVi, int id_partija, int id_boja, int id_zvao, boolean tim_pao) {
        this.bodoviMi = bodoviMi;
        this.bodoviVi = bodoviVi;
        this.zvanjaMi = zvanjaMi;
        this.zvanjaVi = zvanjaVi;
        this.id_partija = id_partija;
        this.id_boja = id_boja;
        this.id_zvao = id_zvao;
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
}
