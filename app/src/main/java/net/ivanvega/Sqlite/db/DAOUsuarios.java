package net.ivanvega.Sqlite.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DAOUsuarios {

    private SQLiteDatabase baseDatos ;

    public DAOUsuarios(Context ctx) {
          baseDatos = new Conexion(ctx).getWritableDatabase();
    }

    public long add(Usuario u)
    {

        ContentValues cv = new ContentValues();

        cv.put(Conexion.COLUMNS_USUARIOS[1], u.getNombre() );
        cv.put(Conexion.COLUMNS_USUARIOS[2], u.getTelefono() );
        cv.put(Conexion.COLUMNS_USUARIOS[3], u.getEmail());
        cv.put(Conexion.COLUMNS_USUARIOS[4], u.getRed_social() );
        cv.put(Conexion.COLUMNS_USUARIOS[5], u.getFechaNacimiento() );
        long result =baseDatos.insert(Conexion.TABLES_DB[0],null,cv);
        //_ad.close();
        return result;

    }

    public long   update (Usuario u){
        ContentValues cv = new ContentValues();
        cv.put(Conexion.COLUMNS_USUARIOS[1], u.getNombre() );
        cv.put(Conexion.COLUMNS_USUARIOS[2], u.getTelefono() );
        cv.put(Conexion.COLUMNS_USUARIOS[3], u.getEmail());
        cv.put(Conexion.COLUMNS_USUARIOS[4], u.getRed_social() );
        cv.put(Conexion.COLUMNS_USUARIOS[5], u.getFechaNacimiento() );

        baseDatos.update(Conexion.TABLES_DB[0],
                cv,
                "_id=?",
                new String[]{String.valueOf( u.getId())}
                );

        return 0;
    }

    public List<Usuario > getAll(){
        List <Usuario> lst = new ArrayList<Usuario>();
         Cursor c = baseDatos.query(
                Conexion.TABLES_DB[0],
                Conexion.COLUMNS_USUARIOS,
                null, null,null,null,null
        );

         while(c.moveToNext()){

             lst.add(
               new Usuario(c.getInt(0),
                       c.getString(1),
                       c.getString(2),
                       c.getString(3),
                       c.getString(4),
                       c.getString(5) )
             );

         }


        return lst;
    }

    public Cursor getAllC(){
        Cursor c = baseDatos.query(
                Conexion.TABLES_DB[0],
                Conexion.COLUMNS_USUARIOS,
                null, null,null,null,null
        );
        return c;
    }
  public Cursor getBusqueda(Usuario u){
        String where="nombre='"+ u.getNombre()+"'";
        Cursor c = baseDatos.query(Conexion.TABLES_DB[0],
                Conexion.COLUMNS_USUARIOS,
                where,
                null,
                null,
                null,
                null);
        //_ad.close();
        return c;
  }
   public int getBorar(Usuario u){

        return baseDatos.delete(Conexion.TABLES_DB[0],"nombre='"+u.getNombre()+"'",null);
   }
}
