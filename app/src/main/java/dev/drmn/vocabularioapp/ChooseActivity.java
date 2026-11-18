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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseActivity extends AppCompatActivity {
    Button btnChoosen;
    Spinner chSpinner;
    RadioGroup radioGroup;
    String urlBase = "https://headpiece-grab-cornflake.ngrok-free.dev/api_server/";

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

        Intent usuarioDetalle = getIntent();
        int idCapturada = usuarioDetalle.getIntExtra("idUsuario", 0);

        btnChoosen = findViewById(R.id.chButton);
        chSpinner = findViewById(R.id.chSpinner);
        radioGroup = findViewById(R.id.radioGroup);

        ArrayAdapter<CharSequence> adaptador =ArrayAdapter.createFromResource(this, R.array.dificultades, android.R.layout.simple_spinner_item);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chSpinner.setAdapter(adaptador);

        btnChoosen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int seleccion = radioGroup.getCheckedRadioButtonId();
                int idIdioma = Integer.parseInt(findViewById(seleccion).getTag().toString());
                int idDificultad = chSpinner.getSelectedItemPosition() + 1;
                StringRequest request = new StringRequest(
                        Request.Method.POST,
                        urlBase + "usuarios.php",
                        response -> {
                            Intent intent = new Intent(ChooseActivity.this, HomeActivity.class);
                            intent.putExtra("idUsuario", idCapturada);
                            startActivity(intent);
                        },
                        error -> Log.e("Volley", "Error: " + error.toString())
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("accion", "elegir");
                        params.put("id", String.valueOf(idCapturada));
                        params.put("idioma_elegido", String.valueOf(idIdioma));
                        params.put("dificultad_elegida", String.valueOf(idDificultad));
                        return params;
                    }
                };
                VolleySingleton.getInstance(ChooseActivity.this).agregar(request);
            }
        });
    }
}