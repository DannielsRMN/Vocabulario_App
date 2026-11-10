package dev.drmn.vocabularioapp;

import static android.widget.Toast.LENGTH_SHORT;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PalabraAdapter extends RecyclerView.Adapter<PalabraAdapter.PalabraViewHolder> {
    private List<Palabra> listadoPalabras;
    public PalabraAdapter(List<Palabra> lista) {
        this.listadoPalabras = lista;
    }

    @NonNull
    @Override
    public PalabraAdapter.PalabraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista1, parent, false);
        PalabraViewHolder palabraViewHolder = new PalabraViewHolder(v);
        return palabraViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PalabraAdapter.PalabraViewHolder holder, int position) {
        Palabra palabra =listadoPalabras.get(position);
        holder.itemPalabra.setText(palabra.getPalabra());
        holder.itemDificultad.setText(palabra.getDificultad());
        holder.btnEscuchar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Palabra: "+palabra.getPalabra(), LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.listadoPalabras.size();
    }

    public class PalabraViewHolder extends RecyclerView.ViewHolder {
        TextView itemPalabra, itemDificultad;
        Button btnEscuchar;

        public PalabraViewHolder(@NonNull View itemView) {
            super(itemView);
            itemPalabra = itemView.findViewById(R.id.iPalabra);
            itemDificultad = itemPalabra.findViewById(R.id.iDificultad);
            btnEscuchar = itemView.findViewById(R.id.iEscuchar);
        }
    }
}
