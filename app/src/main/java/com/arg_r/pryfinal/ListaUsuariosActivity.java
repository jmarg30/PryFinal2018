package com.arg_r.pryfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.arg_r.pryfinal.bd.GameExBd;
import com.arg_r.pryfinal.entidades.Usuario;
import com.arg_r.pryfinal.mantenimientos.UsuarioMant;

import java.util.ArrayList;

public class ListaUsuariosActivity extends AppCompatActivity {

    String nombre;
    int estado;
    ListView mListView;
    Button mButton;
    ArrayList<Usuario> listado;
    UsuarioMant mMant;
    GameExBd mGameExBd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios);


        Intent intent = getIntent();
        Bundle obj = intent.getExtras();
        nombre = obj.getString("Nombre");
        estado = obj.getInt("Estado");

        mListView = findViewById(R.id.LISTA);
        mButton = findViewById(R.id.btnNuevo);
        listar();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ListaUsuariosActivity.this, AdminActivity.class);
                Bundle obj = new Bundle();
                Usuario usuario = listado.get(i);
                obj.putInt("codigo", usuario.getId());
                obj.putString("Nombre", nombre);
                obj.putInt("Estado", estado);
                obj.putInt("op", 3);
                intent.putExtras(obj);
                finish();
                startActivity(intent);
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevo();
            }
        });


    }

    public void listar(){
        mMant = new UsuarioMant();
        mMant.setContext(this);
        listado = mMant.listar();
        UsuarioAdapter adapter = new UsuarioAdapter(this, listado);
        mListView.setAdapter(adapter);
    }

    public void nuevo() {
        Intent intent = new Intent(ListaUsuariosActivity.this, AdminActivity.class);
        Bundle obj = new Bundle();
        obj.putString("Nombre", nombre);
        obj.putInt("op", 2);
        intent.putExtras(obj);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ListaUsuariosActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}
