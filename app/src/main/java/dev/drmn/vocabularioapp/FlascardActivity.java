package dev.drmn.vocabularioapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import java.util.Random;

public class FlascardActivity extends AppCompatActivity {

    Button btnMostrar, btnMix, btnVolver;
    TextView tvPalabra;
    String palabraMostrada = "";
    List<Palabra> lista = new ArrayList<>();
    String urlBase = "https://headpiece-grab-cornflake.ngrok-free.dev/api_server/";
    Random rand = new Random();

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

        Intent captura = getIntent();
        int idioma = captura.getIntExtra("idiomaID", 0);
        int dificultad = captura.getIntExtra("dificultadID", 0);
        int idUsuario = captura.getIntExtra("idUsuario", 0);

        btnVolver = findViewById(R.id.btnVolver);
        btnMostrar = findViewById(R.id.btnMostrar);
        btnMix = findViewById(R.id.btnMix);
        tvPalabra = findViewById(R.id.tvPalabra);

        StringRequest eventoRequest = new StringRequest(
                Request.Method.POST,
                urlBase + "historial.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d("Evento", s);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("Evento", volleyError.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("usuario_id", String.valueOf(idUsuario));
                params.put("tipo", "Flashcards");
                params.put("registro", "Inició a practicar con flashcards.");
                return params;
            }
        };

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                urlBase + "palabra_filtrada.php?idioma_id=" + idioma + "&dificultad_id=" + dificultad,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try {
                            for (int i = 0; i < jsonArray.length(); i++){
                                String palabra = jsonArray.getJSONObject(i).getString("palabra");
                                String significado = jsonArray.getJSONObject(i).getString("significado");
                                int idioma = jsonArray.getJSONObject(i).getInt("idioma_id");
                                int dificultad = jsonArray.getJSONObject(i).getInt("dificultad_id");
                                lista.add(new Palabra(palabra, significado, idioma, dificultad));
                            }

                            if (!lista.isEmpty()) {
                                Palabra randItem = lista.get(rand.nextInt(lista.size()));
                                palabraMostrada = randItem.getPalabra();
                                tvPalabra.setText(palabraMostrada);
                            }
                        } catch (Exception e) {
                            Log.e("Flashcard", "Error", e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("Flashcard", "Error: " + volleyError.toString());
                    }
                }
        );
        VolleySingleton.getInstance(this).agregar(request);
        VolleySingleton.getInstance(this).agregar(eventoRequest);

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