package com.example.belanote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostavkeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostavkeFragment extends Fragment {

    private Button btnPostavke, btnObrisi, btnRecenzija;

    Postavke postavke;

    private AlertDialog.Builder granicaDialogBuilder;
    private AlertDialog granicaDialog;
    private EditText txtGranica;
    private Button btnSpremi, btnOdustani;

    PoslovniSloj poslovni;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PostavkeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostavkeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostavkeFragment newInstance(String param1, String param2) {
        PostavkeFragment fragment = new PostavkeFragment();
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
        postavke = new Postavke(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_postavke, container, false);
    }

    // Skocni DaNe prozor -> https://stackoverflow.com/questions/2478517/how-to-display-a-yes-no-dialog-box-on-android
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    poslovni.obrisiSveZapise();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnPostavke = view.findViewById(R.id.btnPostavke);
        btnObrisi = view.findViewById(R.id.btnObrisiPodatke);
        btnRecenzija = view.findViewById(R.id.btnRecenzija);

        btnPostavke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stvoriGranicaDialog();
            }
        });

        btnObrisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Jeste li sigurni?").setPositiveButton("Da", dialogClickListener)
                        .setNegativeButton("Ne", dialogClickListener).show();
            }
        });

        btnRecenzija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                return;  //TODO Otvara se trg play gdje korisnik moze ostaviti recenziju
            }
        });
    }

    private void stvoriGranicaDialog(){
        granicaDialogBuilder = new AlertDialog.Builder(getActivity());
        final View granicaPopupView = getLayoutInflater().inflate(R.layout.granica_popup, null);

        txtGranica = granicaPopupView.findViewById(R.id.txtGranicaPopup);
        btnSpremi = granicaPopupView.findViewById(R.id.btnSpremi);
        btnOdustani = granicaPopupView.findViewById(R.id.btnOdustani);

        granicaDialogBuilder.setView(granicaPopupView);
        granicaDialog = granicaDialogBuilder.create();
        granicaDialog.show();
        granicaDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        txtGranica.setText(Integer.toString(postavke.dohvatiGranicu()));

        btnOdustani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                granicaDialog.dismiss();
            }
        });

        btnSpremi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(provjeraGranice(txtGranica.getText().toString())){
                    postavke.dodajGranicu(Integer.parseInt(txtGranica.getText().toString()));
                    granicaDialog.dismiss();
                    Toast.makeText(getActivity(), "Granica je promjenjena na " + txtGranica.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean provjeraGranice(String granica){
        boolean uspjeh = false;
        int broj = Integer.parseInt(granica);
        if(broj < 161){
            Toast.makeText(getActivity(), "Granica ne može biti manja od 161", Toast.LENGTH_SHORT).show();
        }else if (broj > 9999){
            Toast.makeText(getActivity(), "Granice ne može biti veća od 9999", Toast.LENGTH_SHORT).show();
        }

        if(broj >= 161 && broj <= 9999){
            uspjeh = true;
        }
        return uspjeh;
    }
}