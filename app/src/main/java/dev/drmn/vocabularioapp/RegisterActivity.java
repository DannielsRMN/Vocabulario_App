package dev.drmn.vocabularioapp;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    Spinner edSpinner;
    Button btn;
    EditText etNombres, etApellidos, etCorreo, etContrasena, etFecha;
    String urlBase = "https://headpiece-grab-cornflake.ngrok-free.dev/api_server/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ingreso de opciones al spinner de roles
        edSpinner = findViewById(R.id.rgRol);
        ArrayAdapter<CharSequence> adaptador =ArrayAdapter.createFromResource(this, R.array.roles, android.R.layout.simple_spinner_item);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edSpinner.setAdapter(adaptador);

        btn = findViewById(R.id.btnRegistrar);

        etNombres = findViewById(R.id.rgNombre);
        etApellidos = findViewById(R.id.rgApellidos);
        etFecha = findViewById(R.id.rgFechaNacimiento);
        etCorreo = findViewById(R.id.rgCorreoElectronico);
        etContrasena = findViewById(R.id.rgContrasena);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombres = etNombres.getText().toString();
                String apellidos = etApellidos.getText().toString();
                String fechaNacimiento = etFecha.getText().toString();
                String correo = etCorreo.getText().toString();
                String contrasena = etContrasena.getText().toString();
                String rol = edSpinner.getSelectedItem().toString();

                try {
                    if (nombres.isEmpty() || apellidos.isEmpty() || fechaNacimiento.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || rol.isEmpty()){
                        Toast.makeText(RegisterActivity.this, "Rellene los campos, por favor.", LENGTH_SHORT).show();
                    } else {
                        // Requests
                        StringRequest registroRequest = new StringRequest(
                                Request.Method.POST,
                                urlBase + "usuarios.php",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String s) {
                                        Log.d("Volley", "Response: " + s);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError volleyError) {
                                        Log.e("Volley", "Error"+volleyError.getMessage());
                                    }
                                }
                        ){
                            @Override
                            protected Map<String, String> getParams(){
                                Map<String, String> params = new HashMap<>();
                                params.put("nombre", nombres);
                                params.put("apellidos", apellidos);
                                params.put("fecha_nacimiento", fechaNacimiento);
                                params.put("correo_electronico", correo);
                                params.put("contrasena", contrasena);
                                params.put("rol", rol);
                                return params;
                            }
                        };

                        VolleySingleton.getInstance(RegisterActivity.this).agregar(registroRequest);
                        Toast.makeText(RegisterActivity.this, "Usuario registrado.", LENGTH_SHORT).show();
                        Intent ivolver = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(ivolver);
                    }
                } catch (Exception e){
                    Log.e("Register", "Error durante el registro de un nuevo usuario");
                    e.printStackTrace();
                }
            }
        });
    }
}