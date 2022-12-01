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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";


    private static int granicaBodovi;
    private static int tekucaPartija;

    public RecyclerView recViewZapisi;
    public Button btnDodajZapis;
    public TextView txtUkupnoMi, txtUkupnoVi;
    public ArrayList<Zapis> zapisi;
    public Bundle ukupnoPodaci;
    PoslovniSloj poslovni;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        poslovni = PoslovniSloj.getInstance(this);

        zapisi = new ArrayList<Zapis>();

        Postavke postavke = new Postavke(this);
        if(postavke.dohvatiGranicu() == -1){
            postavke.dodajGranicu(1001);
        }

        txtUkupnoMi = this.findViewById(R.id.ukupno_mi_a1);
        txtUkupnoVi = this.findViewById(R.id.ukupno_vi_a1);

        recViewZapisi = this.findViewById(R.id.recViewRezultati);
        btnDodajZapis = this.findViewById(R.id.btnDodajZapis);

        btnDodajZapis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), DodajActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        Postavke postavke = new Postavke(this);
        granicaBodovi = postavke.dohvatiGranicu();
        azuriraj();
    }

    public void azuriraj(){
        tekucaPartija = poslovni.provjeraPobjede(granicaBodovi, tekucaPartija);

        zapisi = poslovni.dohvatiSveZapise(tekucaPartija);
        ukupnoPodaci = poslovni.ukupniBodovi(tekucaPartija);

        int ukupnoMi = ukupnoPodaci.getInt("mi", 0);
        int ukupnoVi = ukupnoPodaci.getInt("vi", 0);


        RedZapisaAdapter adapter = new RedZapisaAdapter(this, zapisi);
        recViewZapisi.setAdapter(adapter);
        recViewZapisi.setLayoutManager(new LinearLayoutManager(this));  // saznati kako se azuriraju podaci u rec viewu bez da se svaki put stvara novi


        txtUkupnoMi.setText(Integer.toString(ukupnoMi));
        txtUkupnoVi.setText(Integer.toString(ukupnoVi));

    }
}