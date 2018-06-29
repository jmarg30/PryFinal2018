package com.arg_r.pryfinal.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class GameExBd {

    private String nombreTabla = "usuarios";
    private String nombreBD = "GameExBD";
    private SQLiteDatabase mDatabase;

    private String sqlbd = "create table  if not  exists " +
            nombreTabla + " ( codigo integer  primary key  autoincrement," +
            "dni text not null , nombre text not  null , apellidos text not  null , " +
            "correo text not null , password text not null , ranking integer not null , estado integer not null );";

    public GameExBd() {

    }

    public String getNombreTabla() {
        return nombreTabla;
    }

    public SQLiteDatabase setDatabase(Context context){
        mDatabase = context.openOrCreateDatabase(nombreBD, Context.MODE_PRIVATE, null);
        mDatabase.execSQL(sqlbd);
        return mDatabase;
    }

}
