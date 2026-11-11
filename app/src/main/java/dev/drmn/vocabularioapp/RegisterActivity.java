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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    Spinner edSpinner;
    Button btn;
    EditText etNombres, etApellidos, etCorreo, etContrasena, etFecha;

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
                try {
                    String nombres = etNombres.getText().toString();
                    String apellidos = etApellidos.getText().toString();
                    String fechaNacimiento = etFecha.getText().toString();
                    String correo = etCorreo.getText().toString();
                    String contrasena = etContrasena.getText().toString();
                    String rol = edSpinner.getSelectedItem().toString();

                    if (nombres.isEmpty() && apellidos.isEmpty() && fechaNacimiento.isEmpty() && correo.isEmpty() && contrasena.isEmpty() && rol.isEmpty()){
                        Toast.makeText(RegisterActivity.this, "Rellene los campos, por favor.", LENGTH_SHORT).show();
                    } else {
                        List<Usuario> usuarios = MainActivity.usuarios;
                        usuarios.add(new Usuario(nombres, apellidos, fechaNacimiento, correo, contrasena, rol));

                        Log.i("Registro", "Nombres: "+nombres+" Apellidos: "+apellidos+" Fecha: "+fechaNacimiento+" Correo: "+correo+" Contraseña: "+contrasena+ " Rol: "+rol);
                        Integer n = usuarios.size();
                        Log.i("Registro Completo", usuarios.get(n-1).getCorreoElectronico() + " - "+ usuarios.get(n-1).getContrasena());

                        Toast.makeText(RegisterActivity.this, "Usuario registrado.", LENGTH_SHORT).show();
                        finish();
                    }
                } catch (Exception e){
                    Log.e("Register", "Error durante el registro de un nuevo usuario");
                    e.printStackTrace();
                }
            }
        });
    }
}