package com.example.belanote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseAdapter {

    static final String IME_BAZE = "belanote_database";
    static final String TABLICA_ZAPISI = "zapisi";
    static final String ID = "id";
    static final String BODOVIMI = "bodovi_mi";
    static final String BODOVIVI = "bodovi_vi";
    static final int DATABASE_VERSION = 1;

    static final String LOG_TAG = "DataBaseHelper";

    static final String STVORI_TABLICU = "create table " + TABLICA_ZAPISI + " (" + ID + " integer primary key autoincrement, " + BODOVIMI + " integer, " + BODOVIVI + " integer);";

    private SQLiteDatabase db;

    public class DataBaseHelper extends SQLiteOpenHelper {

        public DataBaseHelper(Context context) {
            super(context, IME_BAZE, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase baza) {
            try{
                baza.execSQL(STVORI_TABLICU);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase baza, int i, int i1) {
            Log.w(LOG_TAG, "Upgrade iz verzije " + i + " u " + i1);

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

    public long insertZapis(int bodoviMi, int bodoviVi){
        ContentValues vrijednosti = new ContentValues();
        vrijednosti.put(BODOVIMI, bodoviMi);
        vrijednosti.put(BODOVIVI, bodoviVi);

        return db.insert(TABLICA_ZAPISI, null, vrijednosti);
    }

    public boolean updateZapis(int id, int bodoviMi, int bodoviVi){ // TODO Implementirati logiku azuriranja postojecih podataka
        return false;
    }

    public Cursor ispisiSveZapise(){
        return db.rawQuery("SELECT * FROM " + TABLICA_ZAPISI, null);
    }
}
