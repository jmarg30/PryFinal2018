package com.arg_r.pryfinal;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.arg_r.pryfinal.bd.GameExBd;
import com.arg_r.pryfinal.mantenimientos.UsuarioMant;

public class RegisterActivity extends AppCompatActivity {

    private TextView textDni;
    private TextView textNombre;
    private TextView textApellidos;
    private TextView textCorreo;
    private TextView textPassword;
    private Button btnRegistrar;
    String ranking = "0";
    int est = 1;
    //SQLiteDatabase mDatabase;
    //GameExBd mGameExBd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textDni = findViewById(R.id.txtDni);
        textNombre = findViewById(R.id.txtNombre);
        textApellidos = findViewById(R.id.txtApellidos);
        textCorreo = findViewById(R.id.txtEmail);
        textPassword = findViewById(R.id.txtPassword);
        btnRegistrar = findViewById(R.id.BTNINSERTAR);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertar();
            }
        });

    }

    public void insertar() {

        UsuarioMant mUsuarioMant = new UsuarioMant();
        mUsuarioMant.setContext(this);

        long estado = 0;

        estado = mUsuarioMant.insertar(textDni.getText().toString(), textNombre.getText().toString(),
                textApellidos.getText().toString(), textCorreo.getText().toString(),
                textPassword.getText().toString(), ranking, est);

        if (estado == 0) {
            Toast.makeText(getApplicationContext(), "Registro no insertado", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Registro insertado", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            Bundle obj = new Bundle();
            obj.putString("Nombre", textNombre.getText().toString());
            obj.putInt("op", 2);
            intent.putExtras(obj);
            startActivity(intent);
        }

    }
}
