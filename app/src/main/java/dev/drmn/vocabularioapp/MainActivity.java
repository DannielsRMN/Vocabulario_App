package dev.drmn.vocabularioapp;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn1, btn2;
    EditText etCorreo, etPassword;
    String urlBase = "https://headpiece-grab-cornflake.ngrok-free.dev/api_server/";

    public static List<Usuario> usuarios = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn1 = findViewById(R.id.registrarse);
        btn2 = findViewById(R.id.iniciarSesion);
        etCorreo = findViewById(R.id.correoElectronico);
        etPassword = findViewById(R.id.contrasena);

        // Request
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                urlBase + "usuarios.php",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try {
                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String email = jsonObject.getString("correo_electronico");
                                String contrasena = jsonObject.getString("contrasena");
                                usuarios.add(new Usuario(email, contrasena));
                            }
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
        VolleySingleton.getInstance(this).agregar(request);;

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i1);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correoCapturado = etCorreo.getText().toString().toLowerCase();
                String contrasenaCapturada = etPassword.getText().toString().toLowerCase();
                int bandera = 0;

                try {
                    for (int i = 0; i < usuarios.size(); i++){
                        if(correoCapturado.equals(usuarios.get(i).getCorreoElectronico().toLowerCase())){
                            if (contrasenaCapturada.equals(usuarios.get(i).getContrasena())){
                                Intent i2 = new Intent(MainActivity.this, ChooseActivity.class);
                                bandera = 1;
                                startActivity(i2);
                            }
                        }
                    }

                    if(bandera == 0){
                        Toast.makeText(MainActivity.this, "Credenciales inválidas", LENGTH_SHORT).show();
                    }

                } catch (Exception e){
                    Log.e("Login", "Error encontrado en el Login");
                    e.printStackTrace();
                }
            }
        });
    }
}