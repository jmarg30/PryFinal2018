package com.arg_r.pryfinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdminConsoleActivity extends AppCompatActivity {
    private AppCompatImageButton mButtonUsuarios;
    private AppCompatImageButton mButtonJuegos;
    private TextView txtNombre;
    private String nombre;
    private int estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_console);

        Intent intent = getIntent();
        Bundle obj = intent.getExtras();
        nombre = obj.getString("Nombre");
        estado = obj.getInt("Estado");
        mButtonUsuarios = findViewById(R.id.btnUsuarios);
        mButtonJuegos = findViewById(R.id.btnJuegos);

        txtNombre = findViewById(R.id.txtHola);
        txtNombre.setText("Hola: " + nombre);

        mButtonUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminConsoleActivity.this, ListaUsuariosActivity.class);
                Bundle obj = new Bundle();
                obj.putString("Nombre", nombre);
                obj.putInt("Estado", estado);
                obj.putInt("op", 1);
                intent.putExtras(obj);
                startActivity(intent);
            }
        });
    }
}
