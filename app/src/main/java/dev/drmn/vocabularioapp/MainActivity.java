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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn1, btn2;
    EditText etCorreo, etPassword;
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

        usuarios.add(new Usuario("DannielsR.MN@gmail.com", "admin"));
        usuarios.add(new Usuario("Admin@gmail.com", "1234"));
        usuarios.add(new Usuario("admin", "1234"));

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
                try {
                    for (int i = 0; i < usuarios.size(); i++){
                        String correoCapturado = etCorreo.getText().toString().toLowerCase();
                        String contrasenaCapturada = etPassword.getText().toString().toLowerCase();
                        if(correoCapturado.equals(usuarios.get(i).getCorreoElectronico().toLowerCase())){
                            if (contrasenaCapturada.equals(usuarios.get(i).getContrasena())){
                                Intent i2 = new Intent(MainActivity.this, ChooseActivity.class);
                                startActivity(i2);
                            } else {
                                Toast.makeText(MainActivity.this, "Credenciales inválidas", LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Credenciales inválidas", LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e){
                    Log.e("Login", "Error en el login.");
                    e.printStackTrace();
                }
            }
        });
    }
}