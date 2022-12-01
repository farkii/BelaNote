package com.example.belanote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseAdapter {

    static final int DATABASE_VERSION = 1;
    static final String LOG_TAG = "DataBaseHelper";
    static final String IME_BAZE = "belanote_database";


    static final String ID_BOJA = "id_boja";
    static final String TABLICA_BOJA = "boja";
    static final String NAZIV_BOJE = "naziv_boje";

    static final String STVORI_TABLICU_BOJA = "create table " + TABLICA_BOJA + " (" + ID_BOJA + " integer primary key autoincrement, " +
            NAZIV_BOJE + " text not null);";

    private static final String TABLICA_TIM = "tim";
    private static final String NAZIV_TIMA = "naziv_tima";
    private static final String ID_TIM = "id_tim";
    static final String STVORI_TABLICU_TIM = "create table " + TABLICA_TIM + " (" + ID_TIM + " integer primary key autoincrement, " +
            NAZIV_TIMA + " text not null);";

    private static final String BROJ_PARTIJE = "broj_partije";
    private static final String TABLICA_PARTIJA = "partija";
    private static final String ID_PARTIJA = "id_partija";
    static final String STVORI_TABLICU_PARTIJA = "create table " + TABLICA_PARTIJA + " (" + ID_PARTIJA + " integer primary key autoincrement, " +
            BROJ_PARTIJE + " integer not null);";

    private static final String TABLICA_POBJEDNIK_PARTIJE = "pobjednik_partije";
    private static final String ID_POBJEDNIK = "id_pobjednik";
    private static final String FK_ID_PARTIJA_POBJEDNIK = "fk_id_partija_pobjednik";
    private static final String FK_ID_TIM_POBJEDNIK = "fk_id_tim_pobjednik";
    static final String STVORI_TABLICU_POBJEDNIK_PARTIJE = "create table " + TABLICA_POBJEDNIK_PARTIJE + " (" + ID_POBJEDNIK + " integer primary key autoincrement, " +
            FK_ID_PARTIJA_POBJEDNIK + " integer not null unique, " +
            FK_ID_TIM_POBJEDNIK + " integer not null, " +
            "foreign key (" + FK_ID_PARTIJA_POBJEDNIK + ") references " + TABLICA_PARTIJA + " (" + ID_PARTIJA + "), " +
            "foreign key (" + FK_ID_TIM_POBJEDNIK + ") references " + TABLICA_TIM + " (" + ID_TIM + "));";

    static final String TABLICA_ZAPISI = "zapisi";
    static final String ID_ZAPIS = "id_zapis";
    static final String BODOVIMI = "mi_bodovi";
    static final String BODOVIVI = "vi_bodovi";
    private static final String MI_ZVANJA = "mi_zvanja";
    private static final String TIM_PAO = "tim_pao";
    private static final String FK_ID_BOJA = "fk_id_boja";
    private static final String FK_ID_TIM_ZVAO = "fk_id_tim_zvao";
    private static final String FK_ID_PARTIJA = "fk_id_partija";
    private static final String VI_ZVANJA = "vi_zvanja";
    static final String STVORI_TABLICU_ZAPIS = "create table " + TABLICA_ZAPISI + " (" + ID_ZAPIS + " integer primary key autoincrement, " +
            BODOVIMI + " integer, " + BODOVIVI + " integer, " +
            MI_ZVANJA + " integer, " + VI_ZVANJA + " integer, " +
            TIM_PAO + " integer, " +
            FK_ID_BOJA + " integer not null, " +
            FK_ID_TIM_ZVAO + " integer not null, " +
            FK_ID_PARTIJA + " integer not null, " +
            "foreign key (" + FK_ID_BOJA + ") references " + TABLICA_BOJA + "(" + ID_BOJA + ")," +
            "foreign key (" + FK_ID_TIM_ZVAO + ") references " + TABLICA_TIM + " (" + ID_TIM + "), " +
            "foreign key (" + FK_ID_PARTIJA + ") references " + TABLICA_PARTIJA + " (" + ID_PARTIJA + "));"; // tim_pao treba pretvoriti u boolean


    static final String DODAJ_TREF = "insert into " + TABLICA_BOJA + "(" + ID_BOJA + ", " + NAZIV_BOJE + ")" + " values (1, 'Tref');";
    static final String DODAJ_PIK = "insert into " + TABLICA_BOJA + "(" + ID_BOJA + ", " + NAZIV_BOJE + ")" +  " values (2, 'Pik');";
    static final String DODAJ_HERC = "insert into " + TABLICA_BOJA + "(" + ID_BOJA + ", " + NAZIV_BOJE + ")" +  " values (3, 'Herc');";
    static final String DODAJ_KARO = "insert into " + TABLICA_BOJA + "(" + ID_BOJA + ", " + NAZIV_BOJE + ")" +  " values (4, 'Karo');";

    static final String DODAJ_MI = "insert into " + TABLICA_TIM +  "(" + ID_TIM + ", " + NAZIV_TIMA + ")" + " values (1, 'Mi');";
    static final String DODAJ_VI = "insert into " + TABLICA_TIM +  "(" + ID_TIM + ", " + NAZIV_TIMA + ")" + " values (2, 'Vi');";

    static final String DODAJ_PARTIJU = "insert into " + TABLICA_PARTIJA +  "(" + ID_PARTIJA + ", " + BROJ_PARTIJE + ")" +  " values (1, 1);";


    private SQLiteDatabase db;

    public class DataBaseHelper extends SQLiteOpenHelper {

        public DataBaseHelper(Context context) {
            super(context, IME_BAZE, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase baza) {
            try{
                baza.execSQL(STVORI_TABLICU_BOJA);
                baza.execSQL(STVORI_TABLICU_TIM);
                baza.execSQL(STVORI_TABLICU_PARTIJA);
                baza.execSQL(STVORI_TABLICU_POBJEDNIK_PARTIJE);
                baza.execSQL(STVORI_TABLICU_ZAPIS);

                baza.execSQL(DODAJ_TREF);
                baza.execSQL(DODAJ_PIK);
                baza.execSQL(DODAJ_HERC);
                baza.execSQL(DODAJ_KARO);

                baza.execSQL(DODAJ_MI);
                baza.execSQL(DODAJ_VI);

                baza.execSQL(DODAJ_PARTIJU);

            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase baza, int i, int i1) {
            Log.w(LOG_TAG, "Upgrade iz verzije " + i + " u " + i1);

            baza.execSQL("DROP TABLE IF EXISTS " + TABLICA_BOJA);
            baza.execSQL("DROP TABLE IF EXISTS " + TABLICA_TIM);
            baza.execSQL("DROP TABLE IF EXISTS " + TABLICA_PARTIJA);
            baza.execSQL("DROP TABLE IF EXISTS " + TABLICA_POBJEDNIK_PARTIJE);
            baza.execSQL("DROP TABLE IF EXISTS " + TABLICA_ZAPISI);

            onCreate(baza);
        }

    }

    private final Context context;
    private DataBaseHelper dbHelper;

    public DataBaseAdapter(Context context) {
        this.context = context;
        dbHelper = new DataBaseHelper(context);
    }

    public DataBaseAdapter open() throws SQLException{
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public long insertZapis(int bodoviMi, int bodoviVi, int zvanjaMi, int zvanjaVi, int boja, int zvao, boolean pao, int partija){
        ContentValues vrijednosti = new ContentValues();
        vrijednosti.put(BODOVIMI, bodoviMi);
        vrijednosti.put(BODOVIVI, bodoviVi);
        vrijednosti.put(MI_ZVANJA, zvanjaMi);
        vrijednosti.put(VI_ZVANJA, zvanjaVi);
        vrijednosti.put(FK_ID_BOJA, boja);
        vrijednosti.put(FK_ID_TIM_ZVAO, zvao);
        vrijednosti.put(TIM_PAO, pao?1:0);
        vrijednosti.put(FK_ID_PARTIJA, partija);

        return db.insert(TABLICA_ZAPISI, null, vrijednosti);
    }

    public boolean updateZapis(int id, int bodoviMi, int bodoviVi, int zvanjaMi, int zvanjaVi, int boja, int zvao, boolean pao, int partija){
        ContentValues vrijednosti = new ContentValues();
        vrijednosti.put(BODOVIMI, bodoviMi);
        vrijednosti.put(BODOVIVI, bodoviVi);
        vrijednosti.put(MI_ZVANJA, zvanjaMi);
        vrijednosti.put(VI_ZVANJA, zvanjaVi);
        vrijednosti.put(FK_ID_BOJA, boja);
        vrijednosti.put(FK_ID_TIM_ZVAO, zvao);
        vrijednosti.put(TIM_PAO, pao?1:0);
        vrijednosti.put(FK_ID_PARTIJA, partija);

        return db.update(TABLICA_ZAPISI, vrijednosti, ID_ZAPIS + "=?", new String[]{Integer.toString(id)}) > 0;
    }

    public Cursor dohvatiSveZapise(int partija){
        return db.rawQuery("SELECT * FROM " + TABLICA_ZAPISI + " WHERE " + FK_ID_PARTIJA + " = ?", new String[]{Integer.toString(partija)});
    }


    public void obrisiSveZapise(){
        return;
    }

    public Cursor dohvatiPartije(){
        return db.rawQuery("SELECT * FROM " + TABLICA_PARTIJA, null);
    }

    public boolean insertPobjednik(int partija, int tim){
        boolean uspjeh = false;

        ContentValues vrijednosti = new ContentValues();
        vrijednosti.put(FK_ID_PARTIJA_POBJEDNIK, partija);
        vrijednosti.put(FK_ID_TIM_POBJEDNIK, tim);

        if(novaPartija(partija)){
            uspjeh = db.insert(TABLICA_POBJEDNIK_PARTIJE, null, vrijednosti) > 0;
        }

        return uspjeh;
    }

    private boolean novaPartija(int tekuca){
        ContentValues vrijednosti = new ContentValues();
        vrijednosti.put(BROJ_PARTIJE, tekuca+1);

        return db.insert(TABLICA_PARTIJA, null, vrijednosti) > 0;
    }
}
