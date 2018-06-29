package com.arg_r.pryfinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.arg_r.pryfinal.entidades.Usuario;
import com.arg_r.pryfinal.mantenimientos.UsuarioMant;

public class AdminActivity extends AppCompatActivity {

    private Button btnGuardar, btnMostrar, btnEliminar;
    private TextView txtUsuario, txtDni, txtNombre, txtApellidos, txtCorreo, txtPassword, txtEstado;
    UsuarioMant mMant = new UsuarioMant();
    int ranking = 0;
    String usuarioNombre;
    int codigo;
    int opcion;
    int est = 1;
    int state;
    //GameExBd mGameExBd = new GameExBd();
   // SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mMant.setContext(this);
        txtUsuario = findViewById(R.id.txtUsuario);
        Intent intent = getIntent();
        Bundle obj = intent.getExtras();
        usuarioNombre = obj.getString("Nombre");
        state = obj.getInt("Estado");
        opcion = obj.getInt("op");
        codigo = obj.getInt("codigo");

        txtUsuario.setText("Hola: " + usuarioNombre);

        txtDni = findViewById(R.id.dni);
        txtNombre = findViewById(R.id.nombre);
        txtApellidos = findViewById(R.id.apellidos);
        txtCorreo = findViewById(R.id.Temail);
        txtPassword = findViewById(R.id.Tpassword);
        txtEstado = findViewById(R.id.estado);

        btnGuardar = findViewById(R.id.BTNGUARDAR);
        btnMostrar = findViewById(R.id.BTNMOSTRAR);
        btnEliminar = findViewById(R.id.BTNELIMINAR);

        if (opcion == 3) {
            Usuario usuario =  cargarCampos(codigo);
            txtDni.setText(usuario.getDni());
            txtNombre.setText(usuario.getNombre());
            txtApellidos.setText(usuario.getApellidos());
            txtCorreo.setText(usuario.getCorreo());
            txtPassword.setText(usuario.getPassword());
            txtEstado.setText(String.valueOf(usuario.getEstado()));
            if (state == 3) {
                txtEstado.setHint(R.string.disponibilidad);
            }
        } else if (opcion == 2) {
            txtEstado.setText("1");
            txtEstado.setEnabled(false);
            btnEliminar.setVisibility(View.GONE);
        }

        //mDatabase = mGameExBd.setDatabase();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (state) {
                    case 3:
                        if (Integer.parseInt(txtEstado.getText().toString()) > 3
                                && Integer.parseInt(txtEstado.getText().toString()) >= 0) {
                            txtEstado.setError("Debe ingresar 0:No Disponible, 1:Disponible, " +
                                    "2:Desarrolador, ó 3:Administrador");
                        } else {
                            guardar();
                        }
                        break;
                    default:
                        if (Integer.parseInt(txtEstado.getText().toString()) != 1
                                && Integer.parseInt(txtEstado.getText().toString()) != 0) {
                            txtEstado.setError("Debe ingresar 0:No Disponible, 1:Disponible,");
                        } else {
                            guardar();
                        }
                }
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
                showErrorDialog("¿Seguro que desea eliminar a " + txtNombre.getText() + " "
                        + txtApellidos.getText() + "?");
            }
        });
    }

    public void mostrar() {
        Intent intent = new Intent(AdminActivity.this, ListaUsuariosActivity.class);
        Bundle obj = new Bundle();
        obj.putString("Nombre", usuarioNombre);
        obj.putInt("Estado", state);
        intent.putExtras(obj);
        finish();
        startActivity(intent);
    }

    public void actualizar() {
        long estado;
        estado = mMant.actualizar(codigo, txtDni.getText().toString(), txtNombre.getText().toString(),
                txtApellidos.getText().toString(), txtCorreo.getText().toString(),
                txtPassword.getText().toString(), ranking, Integer.parseInt(txtEstado.getText().toString()));
        if (estado == 0) {
            Toast.makeText(getApplicationContext(), "No se pudo actualizar", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Actualizacion exitosa", Toast.LENGTH_SHORT).show();
            mostrar();
        }
    }

    public void eliminar() {

        long estado;
        estado = mMant.eliminar(codigo);
        if (estado == 0) {
            Toast.makeText(getApplicationContext(), "No se pudo eliminar", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Registro Eliminado", Toast.LENGTH_SHORT).show();
            mostrar();
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
            txtDni.requestFocus();
            Toast.makeText(getApplicationContext(), "Registro insertado", Toast.LENGTH_SHORT).show();

        }

    }

    public void guardar() {
        if (opcion == 3) {
            actualizar();
        } else {
            insertar();
        }
    }

    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Usuario")
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        eliminar();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}