package com.example.belanote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public RecyclerView recViewZapisi;
    public Button btnDodajZapis;
    public ArrayList<Zapis> zapisi;
    public DataBaseAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbAdapter = new DataBaseAdapter(this);
        zapisi = new ArrayList<Zapis>();
        kreirajZapise();

        recViewZapisi = this.findViewById(R.id.recViewRezultati);
        btnDodajZapis = this.findViewById(R.id.btnDodajZapis);

        azuriraj();
    }

    public void azuriraj(){
        dbAdapter.open();
        Cursor cursor = dbAdapter.ispisiSveZapise();
         // bilo bi dobro da je sav kod vezan uz dbAdapter u posebnom sloju
        if(cursor.moveToFirst()){
            do{
                //Zapis zapis = new Zapis(cursor.getInt(1), cursor.getInt(2));
                //zapisi.add(zapis);
            }while(cursor.moveToNext());
        }
        cursor.close();
        dbAdapter.close();

        RedZapisaAdapter adapter = new RedZapisaAdapter(zapisi);
        recViewZapisi.setAdapter(adapter);
        recViewZapisi.setLayoutManager(new LinearLayoutManager(this));  // saznati kako se azuriraju podaci u rec viewu bez da se svaki put stvara novi
    }

    public void kreirajZapise(){
        Random random = new Random();
        dbAdapter.open();
        for (int i = 0; i < 20; i++){
            //dbAdapter.insertZapis(random.nextInt(200), random.nextInt(200));
        }
        dbAdapter.close();
    }
}