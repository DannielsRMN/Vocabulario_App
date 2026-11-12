package dev.drmn.vocabularioapp;

import static android.widget.Toast.LENGTH_LONG;

import android.content.Intent;
import android.os.Bundle;
import android.ranging.RangingManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    TextView tvRacha;
    RecyclerView rvHome;
    RecyclerView.LayoutManager layoutManagerHome;
    RecyclerView.Adapter homeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.mitoolbar);
        setSupportActionBar(toolbar);

        tvRacha = findViewById(R.id.hmDays);
        List<Racha> rachaList = new ArrayList<>();
        rachaList.add(new Racha(1, 4));
        tvRacha.setText(String.valueOf(rachaList.get(0).getCantDias()));

        rvHome = findViewById(R.id.rvHistorialSemanal);
        layoutManagerHome = new LinearLayoutManager(this);
        rvHome.setLayoutManager(layoutManagerHome);

        List<HistorialSemanal> historials = new ArrayList<>();
        historials.add(new HistorialSemanal("Vocabulary", LocalDate.of(2003,12,13), "Se practico vocabulario mediante flashcards"));
        historials.add(new HistorialSemanal("Exercices", LocalDate.of(2013,03,28), "Se practicaron ejercicios de listening"));

        homeAdapter = new HistorialAdapter(this, historials);
        rvHome.setAdapter(homeAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals("Flashcards")){
            Intent iflash = new Intent(HomeActivity.this, FlascardActivity.class);
            startActivity(iflash);
        }

        if (item.getTitle().equals("Exercices")){
            Intent iexercices = new Intent(HomeActivity.this, ExercicesActivity.class);
            startActivity(iexercices);
        }

        if (item.getTitle().equals("Pronunciacion")){
            Intent ivoca = new Intent(HomeActivity.this, VocabularyActivity.class);
            startActivity(ivoca);
        }

        if (item.getTitle().equals("Cuenta")){
            Intent iaccount = new Intent(HomeActivity.this, AccountActivity.class);
            startActivity(iaccount);
        }

        if (item.getTitle().equals("Cerrar Sesión")){
            Toast.makeText(this, "Cerrando Sesión", LENGTH_LONG).show();
            Intent iclose = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(iclose);
        }

        return super.onOptionsItemSelected(item);
    }
}