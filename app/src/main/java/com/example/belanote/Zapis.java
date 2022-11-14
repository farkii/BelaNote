package com.example.belanote;

public class Zapis {
    private int bodoviMi, bodoviVi;

    // TODO dodati jos podatke o tome tko je zvao, koji je adut bio itd.

    public Zapis(int bodoviMi, int bodoviVi) {
        this.bodoviMi = bodoviMi;
        this.bodoviVi = bodoviVi;
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
