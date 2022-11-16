package com.example.belanote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class DodajActivity extends AppCompatActivity {

    private final static String TAG = "DodajActivity";

    public EditText txtBodMi, txtBodVi, txtZvanjaMi, txtZvanjaVi;
    public RadioGroup rgTim, rgAdut;
    public int partija;
    public Button btnDodaj;
    private boolean pao = false;
    PoslovniSloj poslovni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj);

        poslovni = PoslovniSloj.getInstance(this);

        txtBodMi = this.findViewById(R.id.mi);
        txtBodVi = this.findViewById(R.id.vi);
        txtZvanjaMi = this.findViewById(R.id.zvanja_mi);
        txtZvanjaVi = this.findViewById(R.id.zvanja_vi);
        rgTim = this.findViewById(R.id.rg_pao);
        rgAdut = this.findViewById(R.id.rg_adut);
        partija = 1;
        btnDodaj = this.findViewById(R.id.btn_dodaj_2act);

        btnDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!poslovni.unesiZapis(stvoriZapis())) {
                    Log.w(TAG, "Pogreska kod unosa podataka");
                }
                finish();
            }
        });

    }

    private Zapis stvoriZapis(){
        return new Zapis(Integer.parseInt(txtBodMi.getText().toString()),
                Integer.parseInt(txtBodVi.getText().toString()),
                Integer.parseInt(txtZvanjaMi.getText().toString()),
                Integer.parseInt(txtZvanjaVi.getText().toString()),
                partija,
                rgAdut.indexOfChild(findViewById(rgAdut.getCheckedRadioButtonId()))+1,
                rgTim.indexOfChild(findViewById(rgTim.getCheckedRadioButtonId()))+1,
                pao);
    }
}