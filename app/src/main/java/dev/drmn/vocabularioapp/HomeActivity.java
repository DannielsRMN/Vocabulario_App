package dev.drmn.vocabularioapp;

import static android.widget.Toast.LENGTH_LONG;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

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
            Toast.makeText(this, "Flashcards", LENGTH_LONG).show();
        }

        if (item.getTitle().equals("Exercise")){
            Toast.makeText(this, "Exercise", LENGTH_LONG).show();
        }

        if (item.getTitle().equals("Pronunciacion")){
            Toast.makeText(this, "Pronunciacion", LENGTH_LONG).show();
        }

        if (item.getTitle().equals("Cuenta")){
            Toast.makeText(this, "Cuenta", LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }
}