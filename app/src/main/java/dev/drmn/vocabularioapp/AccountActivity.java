package dev.drmn.vocabularioapp;

import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;

public class AccountActivity extends AppCompatActivity {

    Button btnVolver;
    int id_Capturada = 0;
    TextView tvNombre, tvApellidos, tvFecha, tvCorreo;
    String urlBase = "https://headpiece-grab-cornflake.ngrok-free.dev/api_server/";
    Usuario usuarioActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnVolver = findViewById(R.id.btnAccVolver);

        tvNombre = findViewById(R.id.tvNombre);
        tvApellidos = findViewById(R.id.tvApellidos);
        tvCorreo = findViewById(R.id.tvCorreo);
        tvFecha = findViewById(R.id.tvFechaNacimiento);

        Intent captura = getIntent();
        id_Capturada = captura.getIntExtra("idUsuario", 0);

        JsonArrayRequest cuenta = new JsonArrayRequest(
                Request.Method.GET,
                urlBase + "usuarios.php",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try {
                            String nombre = jsonArray.getJSONObject(id_Capturada - 1).getString("nombre");
                            String apellidos = jsonArray.getJSONObject(id_Capturada - 1).getString("apellidos");
                            String fechaNacimiento = jsonArray.getJSONObject(id_Capturada - 1).getString("fecha_nacimiento");
                            String correoElectronico = jsonArray.getJSONObject(id_Capturada - 1).getString("correo_electronico");

                            tvNombre.setText(nombre);
                            tvApellidos.setText(apellidos);
                            tvCorreo.setText(correoElectronico);
                            tvFecha.setText(fechaNacimiento);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }
        );
        VolleySingleton.getInstance(this).agregar(cuenta);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}