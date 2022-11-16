package com.example.belanote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

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

        btnDodajZapis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), DodajActivity.class);
                startActivity(intent);
            }
        });

        azuriraj();
    }

    @Override
    protected void onStart() {
        super.onStart();
        azuriraj();
    }

    public void azuriraj(){
        zapisi = poslovni.dohvatiSveZapise();
        if(zapisi.toArray().length > 0) {
            RedZapisaAdapter adapter = new RedZapisaAdapter(zapisi);
            recViewZapisi.setAdapter(adapter);
            recViewZapisi.setLayoutManager(new LinearLayoutManager(this));  // saznati kako se azuriraju podaci u rec viewu bez da se svaki put stvara novi
        }
    }
}