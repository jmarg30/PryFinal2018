package com.arg_r.pryfinal.mantenimientos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.arg_r.pryfinal.bd.GameExBd;
import com.arg_r.pryfinal.entidades.Usuario;

import java.util.ArrayList;

public class UsuarioMant {

    private String sqlbd = "";
    private String nombreTabla = "usuarios";
    ArrayList<Usuario> listado=null;
    GameExBd mGameExBd = new GameExBd();
    Context mContext;
    public UsuarioMant() {
    }

    public void setContext(Context context) {
         this.mContext = context;
    }


    public long insertar(String dni, String nom, String ape, String corr, String pass, String rank, int est) {

        SQLiteDatabase mDatabase = mGameExBd.setDatabase(mContext);
        long estado = 0;

        try {
            ContentValues values = new ContentValues();
            values.put("dni", dni);
            values.put("nombre", nom);
            values.put("apellidos", ape);
            values.put("correo", corr);
            values.put("password", pass);
            values.put("ranking", rank);
            values.put("estado", est);

           estado = mDatabase.insert(nombreTabla, null, values);
        }catch (Exception e){
            estado = 0;
            e.printStackTrace();
        }
        return estado;
    }

    public Usuario Seleccionar(String email, String password) {

        Usuario usuario = new Usuario();
        long estado = 0;

        SQLiteDatabase mDatabase = mGameExBd.setDatabase(mContext);
        Cursor cursor;

        try {
            cursor = mDatabase.rawQuery("SELECT * FROM usuarios where correo=? " +
                    "AND password=?", new String[]{email, password});

            if(cursor.moveToNext()) {
                usuario.setNombre(cursor.getString(2));
                usuario.setCorreo(cursor.getString(4));
                usuario.setPassword(cursor.getString(5));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return usuario;
    }

    public Usuario cargarUsuario(int codigo) {
        Usuario usuario = new Usuario();
        SQLiteDatabase mDatabase = mGameExBd.setDatabase(mContext);
        Cursor cursor;
        try {
            cursor = mDatabase.rawQuery("SELECT * FROM usuarios where codigo=" + codigo,null);
            if (cursor.moveToNext()) {
                usuario.setDni(cursor.getString(1));
                usuario.setNombre(cursor.getString(2));
                usuario.setApellidos(cursor.getString(3));
                usuario.setCorreo(cursor.getString(4));
                usuario.setPassword(cursor.getString(5));
                usuario.setRank(cursor.getString(6));
                usuario.setEstado(cursor.getInt(7));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }

    public ArrayList<Usuario> listar() {
        SQLiteDatabase mDatabase = mGameExBd.setDatabase(mContext);
        Cursor cursor;
        String codigo;

        try {
            cursor = mDatabase.rawQuery("SELECT * FROM usuarios", null);
            listado = new ArrayList<>();
            while (cursor.moveToNext()) {
                Usuario usuario = new Usuario();
                usuario.setId(cursor.getInt(0));
                usuario.setDni(cursor.getString(1));
                usuario.setNombre(cursor.getString(2));
                usuario.setApellidos(cursor.getString(3));
                usuario.setCorreo(cursor.getString(4));
                usuario.setPassword(cursor.getString(5));
                usuario.setRank(cursor.getString(6));
                usuario.setEstado(cursor.getInt(7));

                listado.add(usuario);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return listado;
    }

    public long actualizar(int codigo, String dni, String nom, String ape, String corr, String pass, String rank, int est) {

        SQLiteDatabase mDatabase = mGameExBd.setDatabase(mContext);
        long estado = 0;

        try {
            ContentValues values = new ContentValues();
            values.put("dni", dni);
            values.put("nombre", nom);
            values.put("apellidos", ape);
            values.put("correo", corr);
            values.put("password", pass);
            values.put("ranking", rank);
            values.put("estado", est);

           estado = mDatabase.update(nombreTabla, values, "codigo=?", new String[] {String.valueOf(codigo)});
        }catch (Exception e) {
            estado = 0;
            e.printStackTrace();
        }
        return estado;
    }

    public long eliminar(int codigo) {

        SQLiteDatabase mDatabase = mGameExBd.setDatabase(mContext);
        long estado = 0;

        try {
           estado = mDatabase.delete(nombreTabla, "codigo=?", new String[] {String.valueOf(codigo)});
        } catch (Exception e) {
            estado = 0;
            e.printStackTrace();
        }
        return estado;
    }

}
