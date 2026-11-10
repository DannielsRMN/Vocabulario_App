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

        List<Palabra> lista = new ArrayList<>();
        lista.add(new Palabra("Hello", "Fácil"));
        lista.add(new Palabra("Hi", "Fácil"));
        lista.add(new Palabra("Carpenter", "Media"));
        lista.add(new Palabra("Reporter", "Media"));
        lista.add(new Palabra("Dentist", "Media"));
        lista.add(new Palabra("Please", "Fácil"));
        lista.add(new Palabra("Sorry", "Fácil"));
        lista.add(new Palabra("He", "Fácil"));
        lista.add(new Palabra("Cat", "Fácil"));
        lista.add(new Palabra("Waiter ", "Media"));
        lista.add(new Palabra("Taxi driver", "Media"));
        lista.add(new Palabra("Salesperson", "Media"));
        lista.add(new Palabra("Two", "Fácil"));
        lista.add(new Palabra("Dog", "Fácil"));
        lista.add(new Palabra("Bride", "Difícil"));
        lista.add(new Palabra("Breast", "Difícil"));
        lista.add(new Palabra("Breed", "Difícil"));
        lista.add(new Palabra("Breeze", "Difícil"));
        lista.add(new Palabra("Three", "Fácil"));
        lista.add(new Palabra("Tree", "Fácil"));

        miAdapter = new PalabraAdapter(lista);
        miRv.setAdapter(miAdapter);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}