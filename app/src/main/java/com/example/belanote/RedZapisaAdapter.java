package com.example.belanote;

import static com.example.belanote.Boja.*;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RedZapisaAdapter extends RecyclerView.Adapter<RedZapisaAdapter.ViewHolder>{

    private static ArrayList<Zapis> zapisi;
    private static Context context;

    public RedZapisaAdapter(Context c, ArrayList<Zapis> z) {
        this.zapisi = z;
        this.context = c;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.red_zapisa, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMi.setText(Integer.toString(zapisi.get(position).getBodoviMi() + zapisi.get(position).getZvanjaMi()));
        holder.txtVi.setText(Integer.toString(zapisi.get(position).getBodoviVi() + zapisi.get(position).getZvanjaVi()));

        switch (zapisi.get(position).getBoja()){
            case HERC:{
                holder.imgIkona.setImageResource(R.drawable.hearts_icon);
                break;
            }
            case TREF:{
                holder.imgIkona.setImageResource(R.drawable.clubs);
                break;
            }
            case KARO:{
                holder.imgIkona.setImageResource(R.drawable.diamond_icon);
                break;
            }
            case PIK:{
                holder.imgIkona.setImageResource(R.drawable.spades_icon);
                break;
            }
        }

        if(zapisi.get(position).getZvao() == Tim.MI){
            holder.imgLijevo.setImageResource(R.drawable.ic_baseline_arrow_left_24);
        }else if (zapisi.get(position).getZvao() == Tim.VI){
            holder.imgDesno.setImageResource(R.drawable.ic_baseline_arrow_right_24);
        }
    }

    @Override
    public int getItemCount() {
        return zapisi.toArray().length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtMi, txtVi;
        public ImageView imgIkona, imgLijevo, imgDesno;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMi = (TextView) itemView.findViewById(R.id.bodoviMi);
            txtVi = (TextView) itemView.findViewById(R.id.bodoviVi);
            imgIkona = (ImageView) itemView.findViewById(R.id.ikona);
            imgLijevo = (ImageView) itemView.findViewById(R.id.lijevo);
            imgDesno = (ImageView) itemView.findViewById(R.id.desno);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Zapis zapis = zapisi.get(position);

                    Bundle bundle = new Bundle();
                    bundle.putInt("id", zapis.getIdZapis());
                    bundle.putInt("bodMi", zapis.getBodoviMi());
                    bundle.putInt("bodVi", zapis.getBodoviVi());
                    bundle.putInt("zvanjaMi", zapis.getZvanjaMi());
                    bundle.putInt("zvanjaVi", zapis.getZvanjaVi());
                    bundle.putInt("zvao", zapis.getId_zvao());
                    bundle.putInt("adut", zapis.getId_boja());

                    Intent intent = new Intent(context, DodajActivity.class);
                    intent.putExtras(bundle);

                    context.startActivity(intent);
                }
            });
        }
    }
}
