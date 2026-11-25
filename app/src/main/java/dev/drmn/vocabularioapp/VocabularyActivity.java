package dev.drmn.vocabularioapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class VocabularyActivity extends AppCompatActivity {

    RecyclerView miRv;
    RecyclerView.LayoutManager miLayoutManager;
    RecyclerView.Adapter miAdapter;
    Button btnRegresar;
    String urlBase = "https://headpiece-grab-cornflake.ngrok-free.dev/api_server/";
    int idioma_filtro;
    int dificultad_filtro;
    List<Palabra> palabrasAPI = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Vocabulario", "onCreate abierto");
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

        miAdapter = new PalabraAdapter(this, palabrasAPI);
        miRv.setAdapter(miAdapter);

        Intent captura = getIntent();
        idioma_filtro = captura.getIntExtra("idiomaID", -1);
        dificultad_filtro = captura.getIntExtra("dificultadID",     -1);
        int idUsuario = captura.getIntExtra("idUsuario", -1);

        StringRequest eventoRequest = new StringRequest(
                Request.Method.POST,
                urlBase + "historial.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Evento", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Evento", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("usuario_id", String.valueOf(idUsuario));
                params.put("tipo", "Vocabulary");
                params.put("registro", "Practicó nuevas palabras.");
                return params;
            }
        };

        JsonArrayRequest palabras = new JsonArrayRequest(
                Request.Method.GET,
                urlBase + "palabra_filtrada.php?idioma_id="+idioma_filtro+"&dificultad_id="+dificultad_filtro,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        Log.d("Vocabulario", "idioma=" + idioma_filtro + " dificultad=" + dificultad_filtro);
                        try {
                            for (int i=0; i<jsonArray.length(); i++){
                                String palabra = jsonArray.getJSONObject(i).getString("palabra");
                                String significado = jsonArray.getJSONObject(i).getString("significado");
                                int idioma = jsonArray.getJSONObject(i).getInt("idioma_id");
                                int dificultad = jsonArray.getJSONObject(i).getInt("dificultad_id");

                                palabrasAPI.add(new Palabra(palabra, significado, idioma, dificultad));
                            }
                            miAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            Log.e("Vocabulario", "Error al parsear", e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("Volley", "Error: " + volleyError.toString());
                    }
                }
        );
        VolleySingleton.getInstance(this).agregar(palabras);
        VolleySingleton.getInstance(this).agregar(eventoRequest);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}