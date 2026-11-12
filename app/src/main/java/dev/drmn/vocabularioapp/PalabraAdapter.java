package dev.drmn.vocabularioapp;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class PalabraAdapter extends RecyclerView.Adapter<PalabraAdapter.PalabraViewHolder> {
    private List<Palabra> listadoPalabras;
    private TextToSpeech tts;
    public PalabraAdapter(Context context,List<Palabra> lista) {
        this.listadoPalabras = lista;

        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    tts.setLanguage(Locale.US);
                }
            }
        });
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
                if(tts != null){
                    tts.speak(palabra.getPalabra(), TextToSpeech.QUEUE_FLUSH, null);
                    Log.i("TTS: ", "Escuando "+palabra.getPalabra());
                }

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
            itemDificultad = itemView.findViewById(R.id.iDificultad);
            btnEscuchar = itemView.findViewById(R.id.iEscuchar);
        }
    }
}
