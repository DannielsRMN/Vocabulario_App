package dev.drmn.vocabularioapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class VocabularyActivity extends AppCompatActivity {

    RecyclerView miRv;
    RecyclerView.LayoutManager miLayoutManager;
    RecyclerView.Adapter miAdapter;
    Button btnRegresar;
    public static List<Palabra> lista = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vocabulary);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        miRv = findViewById(R.id.miRV);
        btnRegresar = findViewById(R.id.btnVoVolver);
        miLayoutManager = new LinearLayoutManager(this);
        miRv.setLayoutManager(miLayoutManager);

        lista.add(new Palabra("Hello", "Hola","Fácil", "Inglés"));
        lista.add(new Palabra("Hi","Hola","Fácil","Inglés"));
        lista.add(new Palabra("Carpenter","Carpintero","Media","Inglés"));
        lista.add(new Palabra("Reporter","Reportero","Media","Inglés"));
        lista.add(new Palabra("Waiter ","Mesero","Media","Inglés"));
        lista.add(new Palabra("Taxi driver","Taxista","Media","Inglés"));
        lista.add(new Palabra("Salesperson","Vendedor","Media","Inglés"));
        lista.add(new Palabra("Breed","Criar","Difícil","Inglés"));
        lista.add(new Palabra("Breeze","Brisa","Difícil","Inglés"));
        lista.add(new Palabra("Three","Tres","Fácil","Inglés"));
        lista.add(new Palabra("Tree","Árbol","Fácil","Inglés"));

        miAdapter = new PalabraAdapter(this, lista);
        miRv.setAdapter(miAdapter);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}