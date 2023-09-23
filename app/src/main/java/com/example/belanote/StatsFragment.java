package com.example.belanote;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatsFragment extends Fragment {

    private Spinner spinner;
    private TextView txtBrojPartija, txtUkupnoMi, txtUkupnoVi, txtNajveceMi, txtNajveceVi, txtBrojZvanjaMi, txtBrojZvanjaVi, txtBrojPadovaMi, txtBrojPadovaVi;
    private ImageView imgNajcescaBoja, imgNajcescaBojaMi, imgNajcescaBojaVi;
    private PoslovniSloj poslovniSloj;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StatsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatsFragment newInstance(String param1, String param2) {
        StatsFragment fragment = new StatsFragment();
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

        poslovniSloj = PoslovniSloj.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtBrojPartija = view.findViewById(R.id.broj_partija);
        txtUkupnoMi = view.findViewById(R.id.statistika_ukupno_zvanja_mi);
        txtUkupnoVi = view.findViewById(R.id.statistika_ukupno_zvanja_vi);
        txtNajveceMi = view.findViewById(R.id.statistika_najvece_zvanje_mi);
        txtNajveceVi = view.findViewById(R.id.statistika_najvece_zvanje_vi);
        txtBrojZvanjaMi = view.findViewById(R.id.statistika_broj_zvanja_mi);
        txtBrojZvanjaVi = view.findViewById(R.id.statistika_broj_zvanja_vi);
        txtBrojPadovaMi = view.findViewById(R.id.statistika_broj_padova_mi);
        txtBrojPadovaVi = view.findViewById(R.id.statistika_broj_padova_vi);
        imgNajcescaBoja = view.findViewById(R.id.najcesca_boja_ikona);
        imgNajcescaBojaMi = view.findViewById(R.id.najcesca_boja_mi_ikona);
        imgNajcescaBojaVi = view.findViewById(R.id.najcesca_boja_vi_ikona);

        spinner = view.findViewById(R.id.odabir_igre_spinner);
        List<String> igre = poslovniSloj.dohvatiPartije();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), R.layout.spinner_list, igre);
        adapter.setDropDownViewResource(R.layout.spinner_list);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                azuriraj(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
    }

    @SuppressLint("SetTextI18n")
    public void azuriraj(int i){
        List<Zapis> zapisi = poslovniSloj.dohvatiSveZapise(i);
        Bundle podaci = poslovniSloj.izracunajStatistiku(zapisi);

        txtBrojPartija.setText(Integer.toString(podaci.getInt("brojPartija", 0)));
        txtUkupnoMi.setText(Integer.toString(podaci.getInt("ukupnoZvanjaMi", 0)));
        txtUkupnoVi.setText(Integer.toString(podaci.getInt("ukupnoZvanjaVi", 0)));
        txtNajveceMi.setText(Integer.toString(podaci.getInt("najveceZvanjeMi", 0)));
        txtNajveceVi.setText(Integer.toString(podaci.getInt("najveceZvanjeVi", 0)));
        txtBrojZvanjaMi.setText(Integer.toString(podaci.getInt("brojZvanjaMi", 0)));
        txtBrojZvanjaVi.setText(Integer.toString(podaci.getInt("brojZvanjaVi", 0)));
        txtBrojPadovaMi.setText(Integer.toString(podaci.getInt("brojPadovaMi", 0)));
        txtBrojPadovaVi.setText(Integer.toString(podaci.getInt("brojPadovaVi", 0)));

        switch((Boja)podaci.get("najcescaBoja")){
            case PIK:{imgNajcescaBoja.setImageResource(R.drawable.spades_icon); break;}
            case HERC:{imgNajcescaBoja.setImageResource(R.drawable.hearts_icon); break;}
            case KARO:{imgNajcescaBoja.setImageResource(R.drawable.diamond_icon); break;}
            case TREF:{imgNajcescaBoja.setImageResource(R.drawable.clubs); break;}
        }

        switch((Boja)podaci.get("najcescaBojaMi")){
            case PIK:{imgNajcescaBojaMi.setImageResource(R.drawable.spades_icon); break;}
            case HERC:{imgNajcescaBojaMi.setImageResource(R.drawable.hearts_icon); break;}
            case KARO:{imgNajcescaBojaMi.setImageResource(R.drawable.diamond_icon); break;}
            case TREF:{imgNajcescaBojaMi.setImageResource(R.drawable.clubs); break;}
        }

        switch((Boja)podaci.get("najcescaBojaVi")){
            case PIK:{imgNajcescaBojaVi.setImageResource(R.drawable.spades_icon); break;}
            case HERC:{imgNajcescaBojaVi.setImageResource(R.drawable.hearts_icon); break;}
            case KARO:{imgNajcescaBojaVi.setImageResource(R.drawable.diamond_icon); break;}
            case TREF:{imgNajcescaBojaVi.setImageResource(R.drawable.clubs); break;}
        }


    }
}