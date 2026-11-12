package dev.drmn.vocabularioapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder> {
    private List<HistorialSemanal> listadoHistorial;
    public HistorialAdapter(HomeActivity homeActivity, List<HistorialSemanal> historials) {
        this.listadoHistorial = historials;
    }

    @NonNull
    @Override
    public HistorialAdapter.HistorialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.historial_semana, parent, false);
        HistorialAdapter.HistorialViewHolder historialViewHolder = new HistorialAdapter.HistorialViewHolder(v);
        return historialViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistorialAdapter.HistorialViewHolder holder, int position) {
        HistorialSemanal historialSemanal = listadoHistorial.get(position);
        holder.hsContexto.setText(historialSemanal.getContexto());
        holder.hsDate.setText(historialSemanal.getFecha().toString());
        holder.hsAction.setText(historialSemanal.getEvento());
    }

    @Override
    public int getItemCount() {
        return this.listadoHistorial.size();
    }

    public class HistorialViewHolder extends RecyclerView.ViewHolder {
        TextView hsContexto, hsDate, hsAction;
        public HistorialViewHolder(@NonNull View itemView) {
            super(itemView);
            hsContexto = itemView.findViewById(R.id.hsContexto);
            hsDate = itemView.findViewById(R.id.hsDate);
            hsAction = itemView.findViewById(R.id.hsAction);
        }
    }
}
