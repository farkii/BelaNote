package com.example.belanote;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";


    private static int granicaBodovi;
    private static int tekucaPartija;

    private static int partijaMi, partijaVi;

    public RecyclerView recViewZapisi;
    public FloatingActionButton btnDodajZapis;
    public TextView txtUkupnoMi, txtUkupnoVi, txtPartijaMi, txtPartijaVi;
    public ArrayList<Zapis> zapisi;
    public Bundle ukupnoPodaci;
    PoslovniSloj poslovni;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        poslovni = PoslovniSloj.getInstance(getActivity());

        zapisi = new ArrayList<Zapis>();

        Postavke postavke = new Postavke(getActivity());
        if(postavke.dohvatiGranicu() == -1){
            postavke.dodajGranicu(1001);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtUkupnoMi = view.findViewById(R.id.ukupno_mi_a1);
        txtUkupnoVi = view.findViewById(R.id.ukupno_vi_a1);
        txtPartijaMi = view.findViewById(R.id.partija_mi);
        txtPartijaVi = view.findViewById(R.id.partija_vi);

        recViewZapisi = view.findViewById(R.id.recViewRezultati);
        btnDodajZapis = view.findViewById(R.id.btnDodajZapis);

        btnDodajZapis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DodajActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        Postavke postavke = new Postavke(getActivity());
        granicaBodovi = postavke.dohvatiGranicu();
        azuriraj();
    }

    public void azuriraj(){
        tekucaPartija = poslovni.provjeraPobjede(granicaBodovi, tekucaPartija);

        Bundle rezultati = poslovni.dohvatiRezultatePartija();
        partijaMi = rezultati.getInt("mi", 0);
        partijaVi = rezultati.getInt("vi", 0);

        zapisi = poslovni.dohvatiSveZapise(tekucaPartija);
        ukupnoPodaci = poslovni.ukupniBodovi(tekucaPartija);

        int ukupnoMi = ukupnoPodaci.getInt("mi", 0);
        int ukupnoVi = ukupnoPodaci.getInt("vi", 0);


        RedZapisaAdapter adapter = new RedZapisaAdapter(getActivity(), zapisi);
        recViewZapisi.setAdapter(adapter);
        recViewZapisi.setLayoutManager(new LinearLayoutManager(getActivity()));  // saznati kako se azuriraju podaci u rec viewu bez da se svaki put stvara novi


        txtUkupnoMi.setText(Integer.toString(ukupnoMi));
        txtUkupnoVi.setText(Integer.toString(ukupnoVi));
        txtPartijaMi.setText(Integer.toString(partijaMi));
        txtPartijaVi.setText(Integer.toString(partijaVi));

    }
}