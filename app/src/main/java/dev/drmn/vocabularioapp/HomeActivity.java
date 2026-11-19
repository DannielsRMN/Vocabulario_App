package dev.drmn.vocabularioapp;

import static android.widget.Toast.LENGTH_LONG;

import android.content.Intent;
import android.os.Bundle;
import android.ranging.RangingManager;
import android.util.Log;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    TextView tvRacha;
    RecyclerView rvHome;
    RecyclerView.LayoutManager layoutManagerHome;
    RecyclerView.Adapter homeAdapter;
    String urlBase = "https://headpiece-grab-cornflake.ngrok-free.dev/api_server/";
    String rachaStr = "";
    List<HistorialSemanal> historials = new ArrayList<>();
    int idCapturada;
    int idiomaID;
    int dificultadID;

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

        rvHome = findViewById(R.id.rvHistorialSemanal);
        layoutManagerHome = new LinearLayoutManager(this);
        rvHome.setLayoutManager(layoutManagerHome);

        homeAdapter = new HistorialAdapter(this, historials);
        rvHome.setAdapter(homeAdapter);

        Intent usuarioDetalle = getIntent();
        idCapturada = usuarioDetalle.getIntExtra("idUsuario", 0);

        JsonArrayRequest progresoRequest = new JsonArrayRequest(
                Request.Method.GET,
                urlBase + "progreso.php?usuario_id=" + idCapturada,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        Log.d("Racha", "Recibido: " + jsonArray);
                        try {
                            int racha = 0;
                            if (jsonArray.length() > 0) {
                                racha = jsonArray.getJSONObject(0).getInt("racha");
                            }
                            rachaStr = String.valueOf(racha);
                            tvRacha.setText(rachaStr);
                            Log.i("Valor Racha", rachaStr);   // aquí sí vale 5
                        } catch (Exception e) {
                            Log.e("Racha", "Error", e);
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
        JsonArrayRequest historialRequest = new JsonArrayRequest(
                Request.Method.GET,
                urlBase + "historial.php?usuario_id=" + idCapturada,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        Log.d("Historial", "Recibido: " + jsonArray);
                        try {
                            for (int i=0; i < jsonArray.length(); i++){
                                JSONObject o = jsonArray.getJSONObject(i);
                                String tipo = o.getString("tipo");
                                String registro = o.getString("registro");
                                LocalDate fecha = LocalDate.parse(o.getString("tiempo").substring(0, 10));
                                historials.add(new HistorialSemanal(tipo, fecha, registro));
                            }
                            homeAdapter.notifyDataSetChanged();
                        } catch (Exception e){
                            e.printStackTrace();
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
        JsonObjectRequest cuenta = new JsonObjectRequest(
                Request.Method.GET,
                urlBase + "usuarios.php?id=" + idCapturada,
                null,
                response -> {
                    idiomaID = response.optInt("id_idioma", 0);
                    dificultadID = response.optInt("id_dificultad", 0);
                    Log.d("Filtro", "idioma=" + idiomaID + " dificultad=" + dificultadID);
                },
                error -> Log.e("Volley", "Error: " + error.toString())
        );
        VolleySingleton.getInstance(this).agregar(progresoRequest);
        VolleySingleton.getInstance(this).agregar(historialRequest);
        VolleySingleton.getInstance(this).agregar(cuenta);
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
            ivoca.putExtra("idiomaID", idiomaID);
            ivoca.putExtra("dificultadID", dificultadID);
            startActivity(ivoca);
        }

        if (item.getTitle().equals("Cuenta")){
            Intent iaccount = new Intent(HomeActivity.this, AccountActivity.class);
            iaccount.putExtra("idUsuario", idCapturada);
            startActivity(iaccount);
        }

        if (item.getTitle().equals("Cerrar Sesión")){
            Toast.makeText(this, "Cerrando Sesión", LENGTH_LONG).show();
            Intent iclose = new Intent(HomeActivity.this, MainActivity.class);
            idCapturada = 0;
            startActivity(iclose);
        }

        return super.onOptionsItemSelected(item);
    }
}