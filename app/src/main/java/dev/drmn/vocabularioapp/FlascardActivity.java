package dev.drmn.vocabularioapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FlascardActivity extends AppCompatActivity {

    Button btnMostrar, btnMix, btnVolver;
    TextView tvPalabra;
    String palabraMostrada = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_flascard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnVolver = findViewById(R.id.btnVolver);
        btnMostrar = findViewById(R.id.btnMostrar);
        btnMix = findViewById(R.id.btnMix);
        tvPalabra = findViewById(R.id.tvPalabra);

        List<Palabra> lista = new ArrayList<>();
        lista.add(new Palabra("Reporter","Reportero","Media","Inglés"));
        lista.add(new Palabra("Waiter ","Mesero","Media","Inglés"));
        lista.add(new Palabra("Taxi driver","Taxista","Media","Inglés"));
        lista.add(new Palabra("Salesperson","Vendedor","Media","Inglés"));
        lista.add(new Palabra("Breed","Criar","Difícil","Inglés"));
        lista.add(new Palabra("Breeze","Brisa","Difícil","Inglés"));

        Random rand = new Random();
        Palabra randItem = lista.get(rand.nextInt(lista.size()));
        palabraMostrada = randItem.getPalabra().toString();
        tvPalabra.setText(palabraMostrada);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < lista.size(); i++){
                    if(lista.get(i).getPalabra().toString().equals(palabraMostrada)){
                        tvPalabra.setText(lista.get(i).getSignificado().toString());
                    }
                }
            }
        });

        btnMix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Palabra randItem = lista.get(rand.nextInt(lista.size()));
                palabraMostrada = randItem.getPalabra().toString();
                tvPalabra.setText(randItem.getPalabra().toString());
            }
        });
    }
}