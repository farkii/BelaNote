package com.example.belanote;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RedZapisaAdapter extends RecyclerView.Adapter<RedZapisaAdapter.ViewHolder>{

    private static ArrayList<Zapis> zapisi;

    public RedZapisaAdapter(ArrayList<Zapis> z) {
        this.zapisi = z;
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
    }

    @Override
    public int getItemCount() {
        return zapisi.toArray().length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtMi, txtVi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMi = (TextView) itemView.findViewById(R.id.bodoviMi);
            txtVi = (TextView) itemView.findViewById(R.id.bodoviVi);

            // TODO Dodati onClick listenere koji otvaraju aktivnost u kojoj se moze azurirati zapis
        }
    }
}
