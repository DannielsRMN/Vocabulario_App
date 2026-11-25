package dev.drmn.vocabularioapp;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class ExercicesActivity extends AppCompatActivity {

    Button btnVolver, btnReproducir, btnOp1, btnOp2, btnOp3;
    TextView tvTexto;
    String urlBase = "https://headpiece-grab-cornflake.ngrok-free.dev/api_server/";

    List<Palabra> lista = new ArrayList<>();
    Random rand = new Random();
    TextToSpeech tts;
    String palabraCorrecta = "";
    int idiomaID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exercices);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnVolver = findViewById(R.id.btnEVolver);
        btnReproducir = findViewById(R.id.btnEReproducir);
        btnOp1 = findViewById(R.id.btnEOp1);
        btnOp2 = findViewById(R.id.btnEOp2);
        btnOp3 = findViewById(R.id.btnEOp3);
        tvTexto = findViewById(R.id.tvETexto);

        Intent captura = getIntent();
        int idUsuario = captura.getIntExtra("idUsuario", 0);
        idiomaID = captura.getIntExtra("idiomaID", 0);
        int dificultadID = captura.getIntExtra("dificultadID", 0);

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
                params.put("tipo", "Exercices");
                params.put("registro", "Se practicó el Listening");
                return params;
            }
        };
        VolleySingleton.getInstance(this).agregar(eventoRequest);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(obtenerLocale(idiomaID));
                }
            }
        });

        JsonArrayRequest palabrasRequest = new JsonArrayRequest(
                Request.Method.GET,
                urlBase + "palabra_filtrada.php?idioma_id=" + idiomaID + "&dificultad_id=" + dificultadID,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                String palabra = jsonArray.getJSONObject(i).getString("palabra");
                                String significado = jsonArray.getJSONObject(i).getString("significado");
                                int idioma = jsonArray.getJSONObject(i).getInt("idioma_id");
                                int dificultad = jsonArray.getJSONObject(i).getInt("dificultad_id");
                                lista.add(new Palabra(palabra, significado, idioma, dificultad));
                            }
                            nuevaRonda();
                        } catch (Exception e) {
                            Log.e("Exercices", "Error", e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("Exercices", "Error: " + volleyError.toString());
                    }
                }
        );
        VolleySingleton.getInstance(this).agregar(palabrasRequest);

        btnReproducir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!palabraCorrecta.isEmpty()) {
                    tts.speak(palabraCorrecta, TextToSpeech.QUEUE_FLUSH, null, null);
                }
            }
        });

        View.OnClickListener opcionListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String elegida = ((Button) v).getText().toString();
                if (elegida.equals(palabraCorrecta)) {
                    Toast.makeText(ExercicesActivity.this, "¡Correcto!", Toast.LENGTH_SHORT).show();
                    nuevaRonda();
                } else {
                    Toast.makeText(ExercicesActivity.this, "Incorrecto, intenta de nuevo", Toast.LENGTH_SHORT).show();
                }
            }
        };
        btnOp1.setOnClickListener(opcionListener);
        btnOp2.setOnClickListener(opcionListener);
        btnOp3.setOnClickListener(opcionListener);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void nuevaRonda() {
        int indiceCorrecto = rand.nextInt(lista.size());
        palabraCorrecta = lista.get(indiceCorrecto).getPalabra();

        List<String> opciones = new ArrayList<>();
        opciones.add(palabraCorrecta);

        while (opciones.size() < 3) {
            String candidata = lista.get(rand.nextInt(lista.size())).getPalabra();
            if (!opciones.contains(candidata)) {
                opciones.add(candidata);
            }
        }

        Collections.shuffle(opciones);
        btnOp1.setText(opciones.get(0));
        btnOp2.setText(opciones.get(1));
        btnOp3.setText(opciones.get(2));
        tvTexto.setText("Presiona Reproducir y elige la palabra que escuchaste");
    }

    private Locale obtenerLocale(int idioma) {
        if (idioma == 2) {
            return Locale.FRANCE;
        }
        if (idioma == 3) {
            return Locale.forLanguageTag("pt-BR");
        }
        return Locale.US;
    }
}