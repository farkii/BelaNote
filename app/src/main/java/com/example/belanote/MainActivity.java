package com.example.belanote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public RecyclerView recViewZapisi;
    public Button btnDodajZapis;
    public ArrayList<Zapis> zapisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        zapisi = new ArrayList<Zapis>();
        KreirajZapise();

        recViewZapisi = this.findViewById(R.id.recViewRezultati);
        btnDodajZapis = this.findViewById(R.id.btnDodajZapis);

        Osvjezi();
    }

    public void Osvjezi(){
        RedZapisaAdapter adapter = new RedZapisaAdapter(zapisi);
        recViewZapisi.setAdapter(adapter);
        recViewZapisi.setLayoutManager(new LinearLayoutManager(this));
    }

    public void KreirajZapise(){ // U svrhu testiranja, obrisati nakon implementacije baze podataka
        Random random = new Random();
        for (int i = 0; i < 20; i++){
            Zapis zapis = new Zapis (random.nextInt(200), random.nextInt(200));
            zapisi.add(zapis);
        }
    }
}