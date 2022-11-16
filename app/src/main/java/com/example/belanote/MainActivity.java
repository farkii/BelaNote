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
    PoslovniSloj poslovni;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        poslovni = PoslovniSloj.getInstance(this);

        zapisi = new ArrayList<Zapis>();


        recViewZapisi = this.findViewById(R.id.recViewRezultati);
        btnDodajZapis = this.findViewById(R.id.btnDodajZapis);

        azuriraj();
    }

    public void azuriraj(){
        zapisi = poslovni.dohvatiSveZapise();
        RedZapisaAdapter adapter = new RedZapisaAdapter(zapisi);
        recViewZapisi.setAdapter(adapter);
        recViewZapisi.setLayoutManager(new LinearLayoutManager(this));  // saznati kako se azuriraju podaci u rec viewu bez da se svaki put stvara novi
    }
}