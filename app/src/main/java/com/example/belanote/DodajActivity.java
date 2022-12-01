package com.example.belanote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class DodajActivity extends AppCompatActivity{

    private final static String TAG = "DodajActivity";

    private int idZapis; //alocira se s postojecim podacima kod azuriranja postojeceg zapisa u metodi onStart()

    public TextView txtBodMi, txtBodVi, txtZvanjaMi, txtZvanjaVi,txtUkupnoMi, txtUkupnoVi;
    public RadioGroup rgTim, rgAdut;
    public int partija;
    public Button btnDodaj;
    public MaterialButton btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnC, btnDel;
    private boolean pao = false;
    public static int tekucaPartija;

    Polje oznacenoPolje;

    PoslovniSloj poslovni;

    View.OnClickListener oznaceniBodovi = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TextView polje = (TextView) view;
            switch(polje.getId()){
                case R.id.mi:{
                    oznacenoPolje = Polje.bodMi;
                    promjenaBoja(oznacenoPolje.ordinal()+1);
                    break;
                }
                case R.id.vi:{
                    oznacenoPolje = Polje.bodVi;
                    promjenaBoja(oznacenoPolje.ordinal()+1);
                    break;
                }
                case R.id.zvanja_mi:{
                    oznacenoPolje = Polje.zvanjaMi;
                    promjenaBoja(oznacenoPolje.ordinal()+1);
                    break;
                }
                case R.id.zvanja_vi:{
                    oznacenoPolje = Polje.zvanjaVi;
                    promjenaBoja(oznacenoPolje.ordinal()+1);
                    break;
                }
            }
        }
    };

    View.OnClickListener unosBodova = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MaterialButton btn = (MaterialButton) view;

            if(btn.getId() == R.id.btnC){
                txtBodMi.setText("0");
                txtBodVi.setText("0");
                txtZvanjaMi.setText("0");
                txtZvanjaVi.setText("0");
            }else if(btn.getId() == R.id.btnDel){
                oduzimanjeOznacenomPolju();
            }else {
                String zaDodati = btn.getText().toString();
                dodavanjeOznacenomPolju(zaDodati);
            }
            racunanjeUkupnihBodova();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj);

        idZapis = -1;

        poslovni = PoslovniSloj.getInstance(this);

        tekucaPartija = poslovni.dohvatiZadnjuPartiju();

        txtBodMi = this.findViewById(R.id.mi);
        txtBodMi.setOnClickListener(oznaceniBodovi);

        txtBodVi = this.findViewById(R.id.vi);
        txtBodVi.setOnClickListener(oznaceniBodovi);

        txtZvanjaMi = this.findViewById(R.id.zvanja_mi);
        txtZvanjaMi.setOnClickListener(oznaceniBodovi);

        txtZvanjaVi = this.findViewById(R.id.zvanja_vi);
        txtZvanjaVi.setOnClickListener(oznaceniBodovi);

        txtUkupnoMi = this.findViewById(R.id.ukupno_mi);
        txtUkupnoVi = this.findViewById(R.id.ukupno_vi);

        rgTim = this.findViewById(R.id.rg_zvao);
        rgAdut = this.findViewById(R.id.rg_adut);

        btnDodaj = this.findViewById(R.id.btn_dodaj_2act);

        inicijalizirajGumb(btn0, R.id.btn0);
        inicijalizirajGumb(btn1, R.id.btn1);
        inicijalizirajGumb(btn2, R.id.btn2);
        inicijalizirajGumb(btn3, R.id.btn3);
        inicijalizirajGumb(btn4, R.id.btn4);
        inicijalizirajGumb(btn5, R.id.btn5);
        inicijalizirajGumb(btn6, R.id.btn6);
        inicijalizirajGumb(btn7, R.id.btn7);
        inicijalizirajGumb(btn8, R.id.btn8);
        inicijalizirajGumb(btn9, R.id.btn9);
        inicijalizirajGumb(btnC, R.id.btnC);
        inicijalizirajGumb(btnDel, R.id.btnDel);

        txtBodMi.setText("0");
        txtBodVi.setText("0");
        txtZvanjaMi.setText("0");
        txtZvanjaVi.setText("0");


        oznacenoPolje = Polje.bodMi;
        promjenaBoja(oznacenoPolje.ordinal()+1);

        btnDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(provjeraPodataka()) {
                    if(idZapis != -1){
                        if(!poslovni.azurirajZapis(stvoriZapisZaAzuriranje())){
                            Log.w(TAG, "Pogreska kod azuriranja podataka");
                        }
                    }else {
                        if (!poslovni.unesiZapis(Integer.parseInt(txtBodMi.getText().toString()), Integer.parseInt(txtBodVi.getText().toString()), Integer.parseInt(txtZvanjaMi.getText().toString()), Integer.parseInt(txtZvanjaVi.getText().toString()), rgAdut.indexOfChild(findViewById(rgAdut.getCheckedRadioButtonId())) + 1, rgTim.indexOfChild(findViewById(rgTim.getCheckedRadioButtonId())) + 1, pao, tekucaPartija)) {
                            Log.w(TAG, "Pogreska kod unosa podataka");
                        }
                    }
                    finish();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        Bundle updatePodaci = intent.getExtras();

        if(updatePodaci != null){
            idZapis = updatePodaci.getInt("id");

            txtBodMi.setText(Integer.toString(updatePodaci.getInt("bodMi")));
            txtBodVi.setText(Integer.toString(updatePodaci.getInt("bodVi")));
            txtZvanjaMi.setText(Integer.toString(updatePodaci.getInt("zvanjaMi")));
            txtZvanjaVi.setText(Integer.toString(updatePodaci.getInt("zvanjaVi")));

            String tekst = Integer.toString(updatePodaci.getInt("zvanjaVi"));

            rgTim.check(rgTim.getChildAt(updatePodaci.getInt("zvao")-1).getId());
            rgAdut.check(rgAdut.getChildAt(updatePodaci.getInt("adut")-1).getId());

            racunanjeUkupnihBodova();


        }
    }

    private boolean provjeraPodataka(){
        boolean uspjeh = true;
        int bodMi = Integer.parseInt(txtBodMi.getText().toString()), bodVi = Integer.parseInt(txtBodVi.getText().toString());

        if(bodMi+bodVi != 162){
            Toast.makeText(this, "Suma bodova bez zvanja mora iznositi tocno 162", Toast.LENGTH_SHORT).show();
            uspjeh = false;
        }

        if(rgTim.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, "Potrebno je izabrati koji tim koji je zvao", Toast.LENGTH_SHORT).show();
            uspjeh = false;
        }
        if(rgAdut.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, "Potrebno je izabrati adut", Toast.LENGTH_SHORT).show();
            uspjeh = false;
        }

        return uspjeh;
    }

    private void inicijalizirajGumb(MaterialButton button, int id){
        button = this.findViewById(id);
        button.setOnClickListener(unosBodova);
    }

    private void promjenaBoja(int i){
        switch(i){
            case 1:{
                txtBodMi.setBackgroundColor(Color.parseColor("#288039"));
                txtBodVi.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtZvanjaMi.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtZvanjaVi.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            }
            case 2:{
                txtBodMi.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtBodVi.setBackgroundColor(Color.parseColor("#288039"));
                txtZvanjaMi.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtZvanjaVi.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            }
            case 3:{
                txtBodMi.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtBodVi.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtZvanjaMi.setBackgroundColor(Color.parseColor("#288039"));
                txtZvanjaVi.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            }
            case 4:{
                txtBodMi.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtBodVi.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtZvanjaMi.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtZvanjaVi.setBackgroundColor(Color.parseColor("#288039"));
                break;
            }
        }
    }

    private void dodavanjeOznacenomPolju(String zaDodati){
        switch (oznacenoPolje){
            case bodMi:{
                String broj = txtBodMi.getText().toString();
                if(broj.equals("0")){
                    broj = zaDodati;
                }else{
                    broj += zaDodati;
                }
                txtBodMi.setText(broj);
                txtBodVi.setText(racunanjeBodova(broj));
                break;
            }
            case bodVi:{
                String broj = txtBodVi.getText().toString();
                if(broj.equals("0")){
                    broj = zaDodati;
                }else{
                    broj += zaDodati;
                }
                txtBodVi.setText(broj);
                txtBodMi.setText(racunanjeBodova(broj));
                break;
            }
            case zvanjaMi:{
                String broj = txtZvanjaMi.getText().toString();
                if(broj.equals("0")){
                    broj = zaDodati;
                }else{
                    broj += zaDodati;
                }
                txtZvanjaMi.setText(broj);
                break;
            }
            case zvanjaVi:{
                String broj = txtZvanjaVi.getText().toString();
                if(broj.equals("0")){
                    broj = zaDodati;
                }else{
                    broj += zaDodati;
                }
                txtZvanjaVi.setText(broj);
                break;
            }
        }
    }
    private void oduzimanjeOznacenomPolju(){
        switch (oznacenoPolje){
            case bodMi:{
                String broj = txtBodMi.getText().toString();
                if(broj.length() == 1){
                    broj = "0";
                }else{
                    broj = broj.substring(0, broj.length()-1);
                }
                txtBodMi.setText(broj);
                txtBodVi.setText(racunanjeBodova(broj));
                break;
            }
            case bodVi:{
                String broj = txtBodVi.getText().toString();
                if(broj.length() == 1){
                    broj = "0";
                }else{
                    broj = broj.substring(0, broj.length()-1);
                }
                txtBodVi.setText(broj);
                txtBodMi.setText(racunanjeBodova(broj));
                break;
            }
            case zvanjaMi:{
                String broj = txtZvanjaMi.getText().toString();
                if(broj.length() == 1){
                    broj = "0";
                }else{
                    broj = broj.substring(0, broj.length()-1);
                }
                txtZvanjaMi.setText(broj);
                break;
            }
            case zvanjaVi:{
                String broj = txtZvanjaVi.getText().toString();
                if(broj.length() == 1){
                    broj = "0";
                }else{
                    broj = broj.substring(0, broj.length()-1);
                }
                txtZvanjaVi.setText(broj);
                break;
            }
        }
    }

    private String racunanjeBodova(String bodovi){
        int bod = Integer.parseInt(bodovi);
        int drugiTim = 0;

        if(bod <= 162 && bod >= 0){
            drugiTim = 162 - bod;
            return Integer.toString(drugiTim);
        }

        return Integer.toString(drugiTim);
    }

    private void racunanjeUkupnihBodova(){
        int bodMi = Integer.parseInt(txtBodMi.getText().toString());
        int bodVi = Integer.parseInt(txtBodVi.getText().toString());

        int zvanjaMi = Integer.parseInt(txtZvanjaMi.getText().toString());
        int zvanjaVi = Integer.parseInt(txtZvanjaVi.getText().toString());

        txtUkupnoMi.setText(Integer.toString(bodMi + zvanjaMi));
        txtUkupnoVi.setText(Integer.toString(bodVi + zvanjaVi));
    }

    private Zapis stvoriZapisZaAzuriranje(){
        return new Zapis(idZapis, Integer.parseInt(txtBodMi.getText().toString()), Integer.parseInt(txtBodVi.getText().toString()), Integer.parseInt(txtZvanjaMi.getText().toString()), Integer.parseInt(txtZvanjaVi.getText().toString()), tekucaPartija, rgAdut.indexOfChild(findViewById(rgAdut.getCheckedRadioButtonId())) + 1, rgTim.indexOfChild(findViewById(rgTim.getCheckedRadioButtonId())) + 1);
    }
}