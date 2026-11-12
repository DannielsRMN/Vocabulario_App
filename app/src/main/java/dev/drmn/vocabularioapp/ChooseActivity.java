package dev.drmn.vocabularioapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ChooseActivity extends AppCompatActivity {
    Button btnChoosen;
    Spinner chSpinner;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnChoosen = findViewById(R.id.chButton);
        chSpinner = findViewById(R.id.chSpinner);
        radioGroup = findViewById(R.id.radioGroup);

        ArrayAdapter<CharSequence> adaptador =ArrayAdapter.createFromResource(this, R.array.dificultades, android.R.layout.simple_spinner_item);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chSpinner.setAdapter(adaptador);

        btnChoosen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selección = radioGroup.getCheckedRadioButtonId();
                RadioButton btnSeleccionado = findViewById(selección);
                String idioma = btnSeleccionado.getText().toString();

                String dificultad = chSpinner.getSelectedItem().toString();

                Log.i("IDIOMA: ", idioma);
                Log.i("DIFICULTAD: ", dificultad);

                Intent ichoosen = new Intent(ChooseActivity.this, HomeActivity.class);
                startActivity(ichoosen);
            }
        });
    }
}