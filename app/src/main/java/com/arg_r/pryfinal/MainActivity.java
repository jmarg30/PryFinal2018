package com.arg_r.pryfinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.arg_r.pryfinal.bd.GameExBd;
import com.arg_r.pryfinal.entidades.Usuario;
import com.arg_r.pryfinal.mantenimientos.UsuarioMant;

public class MainActivity extends AppCompatActivity {

    private Button btnGuardar, btnMostrar, btnEliminar;
    private TextView txtUsuario, txtDni, txtNombre, txtApellidos, txtCorreo, txtPassword, txtEstado;
    UsuarioMant mMant = new UsuarioMant();
    String ranking = "0";
    String usuarioNombre;
    int codigo;
    int opcion;
    int est = 1;
    //GameExBd mGameExBd = new GameExBd();
   // SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMant.setContext(this);
        txtUsuario = findViewById(R.id.txtUsuario);
        Intent intent = getIntent();
        Bundle obj = intent.getExtras();
        usuarioNombre = obj.getString("Nombre");
        opcion = obj.getInt("op");
        codigo = obj.getInt("codigo");

        txtUsuario.setText("Hola: " + usuarioNombre);

        txtDni = findViewById(R.id.dni);
        txtNombre = findViewById(R.id.nombre);
        txtApellidos = findViewById(R.id.apellidos);
        txtCorreo = findViewById(R.id.Temail);
        txtPassword = findViewById(R.id.Tpassword);
        txtEstado = findViewById(R.id.estado);

        if (opcion == 3) {
            Usuario usuario =  cargarCampos(codigo);
            txtDni.setText(usuario.getDni());
            txtNombre.setText(usuario.getNombre());
            txtApellidos.setText(usuario.getApellidos());
            txtCorreo.setText(usuario.getCorreo());
            txtPassword.setText(usuario.getPassword());
            txtEstado.setText(String.valueOf(usuario.getEstado()));
        }

        btnGuardar = findViewById(R.id.BTNGUARDAR);
        btnMostrar = findViewById(R.id.BTNMOSTRAR);
        btnEliminar = findViewById(R.id.BTNELIMINAR);

        //mDatabase = mGameExBd.setDatabase();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizar();
            }
        });

        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrar();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminar();
            }
        });
    }

    public void mostrar() {
        Intent intent = new Intent(MainActivity.this, ListaUsuariosActivity.class);
        intent.putExtra("Nombre", usuarioNombre);
        startActivity(intent);
    }

    public void actualizar() {
        long estado;
        estado = mMant.actualizar(codigo, txtDni.getText().toString(), txtNombre.getText().toString(),
                txtApellidos.getText().toString(), txtCorreo.getText().toString(),
                txtPassword.getText().toString(), ranking, Integer.parseInt(txtEstado.getText().toString()));
        if (estado == 0) {
            Toast.makeText(getApplicationContext(), "No se pudo actualizar", Toast.LENGTH_LONG).show();
            showErrorDialog("El usuario no existe. \n ¿Desea registrarlo?");
        } else {
            txtDni.setText("");
            txtNombre.setText("");
            txtApellidos.setText("");
            txtCorreo.setText("");
            txtPassword.setText("");
            txtEstado.setText("");
            Toast.makeText(getApplicationContext(), "Actualizacion exitosa", Toast.LENGTH_SHORT).show();
        }
    }

    public void eliminar() {

        long estado;
        estado = mMant.eliminar(codigo);
        if (estado == 0) {
            Toast.makeText(getApplicationContext(), "No se pudo eliminar", Toast.LENGTH_LONG).show();
        } else {
            txtDni.setText("");
            txtNombre.setText("");
            txtApellidos.setText("");
            txtCorreo.setText("");
            txtPassword.setText("");
            txtEstado.setText("");
            Toast.makeText(getApplicationContext(), "Registro Eliminado", Toast.LENGTH_SHORT).show();
        }
    }

    public Usuario cargarCampos(int codigo) {

        Usuario usuario =  mMant.cargarUsuario(codigo);
        return usuario;
    }

    public void insertar() {

        UsuarioMant mUsuarioMant = new UsuarioMant();
        mUsuarioMant.setContext(this);

        long estado = 0;

        estado = mUsuarioMant.insertar(txtDni.getText().toString(), txtNombre.getText().toString(),
                txtApellidos.getText().toString(), txtCorreo.getText().toString(),
                txtPassword.getText().toString(), ranking, est);

        if (estado == 0) {
            Toast.makeText(getApplicationContext(), "Registro no insertado", Toast.LENGTH_LONG).show();
        } else {
            txtDni.setText("");
            txtNombre.setText("");
            txtApellidos.setText("");
            txtCorreo.setText("");
            txtPassword.setText("");
            txtEstado.setText("");
            Toast.makeText(getApplicationContext(), "Registro insertado", Toast.LENGTH_SHORT).show();

        }

    }

    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        insertar();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}