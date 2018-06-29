package com.arg_r.pryfinal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.arg_r.pryfinal.entidades.Usuario;

import java.util.List;

public class UsuarioAdapter extends ArrayAdapter<Usuario> {
    public UsuarioAdapter(@NonNull Context context, @NonNull List<Usuario> usuarios) {
        super(context, 0, usuarios);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.user_list_design, parent, false);
        }

        Usuario usuario = getItem(position);

        TextView textDni = listItemView.findViewById(R.id.textDni);
        textDni.setText("DNI: " + usuario.getDni());

        TextView textNombre = listItemView.findViewById(R.id.textNombre);
        textNombre.setText("Nombre: " +  usuario.getNombre());

        TextView textApellidos = listItemView.findViewById(R.id.textApellidos);
        textApellidos.setText("Apellidos " + usuario.getApellidos());

        TextView textEmail = listItemView.findViewById(R.id.textEmail);
        textEmail.setText("Email: " + usuario.getCorreo());

        TextView textPassword = listItemView.findViewById(R.id.textPassword);
        textPassword.setText("Password: " + usuario.getPassword());

        TextView textRank = listItemView.findViewById(R.id.textRank);
        textRank.setText("Ranking: " + usuario.getRank());

        TextView textEstado = listItemView.findViewById(R.id.textEstado);
        switch (usuario.getEstado()) {
            case 0:
                textEstado.setText("No disponible");
                break;
            case 1:
                textEstado.setText("Disponible");
                break;
            case 2:
                textEstado.setText("Desarrollador");
                break;
            case 3:
                textEstado.setText("Administrador");
                break;
            default: textEstado.setText("");
                break;
        }

        return listItemView;

    }
}
