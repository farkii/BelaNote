package com.example.belanote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostavkeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostavkeFragment extends Fragment {

    private Button btnPostavke, btnObrisi, btnRecenzija;

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
                return;
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
}