package net.ivanvega.Sqlite;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import net.ivanvega.sqliteenandroid.R;
import net.ivanvega.Sqlite.db.DAOUsuarios;
import net.ivanvega.Sqlite.db.Conexion;
import net.ivanvega.Sqlite.db.Usuario;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText nombre,email,telefono,fecha,redSocial;
    ListView lsv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nombre = findViewById(R.id.txtNombre);
        telefono = findViewById(R.id.txtTelefono);
        email = findViewById(R.id.txtEmail);
        redSocial = findViewById(R.id.txtRedSocial);
        fecha = findViewById(R.id.txtFechaNacimiento);
        lsv = findViewById(R.id.lsv);
        btnCargar();
    }

    public void btnI_click(View v){
        DAOUsuarios ado = new DAOUsuarios(getApplicationContext());
        if( !nombre.getText().toString().isEmpty() && !telefono.getText().toString().isEmpty() &&
                !email.getText().toString().isEmpty() && !fecha.getText().toString().isEmpty()){

            long result = ado.add(
                    new Usuario(0, nombre.getText().toString(), telefono.getText().toString(), email.getText().toString(),redSocial.getText().toString() , fecha.getText().toString())
            );

            if (result>0){
                Toast.makeText(this, "Agregado con exito",
                        Toast.LENGTH_LONG).show();
                btnCargar();
                nombre.setText("");
                telefono.setText("");
                email.setText("");
                redSocial.setText("");
                fecha.setText("");
            }
        }else{
            Toast.makeText(this, "Debes ingresar todos los datos ",Toast.LENGTH_LONG).show();
        }


    }

    public void btnCargar(){

            DAOUsuarios dao = new DAOUsuarios(this);
            List<Usuario> lst =  dao.getAll();
            for (Usuario item: lst
                    ) {
                Log.d("USUARIO: " ,  String.valueOf( item.getId()));
                Log.d("USUARIO: " , item.getNombre());
            }
            Cursor c =  dao.getAllC();
      if(c.moveToFirst()){
          SimpleCursorAdapter adp = new SimpleCursorAdapter(
                  this, android.R.layout.simple_list_item_2 ,
                  c , Conexion.COLUMNS_USUARIOS,
                  new int[]{android.R.id.text1, android.R.id.text2},
                  SimpleCursorAdapter.NO_SELECTION

          );
          lsv.setAdapter(adp);
      }else{
          lsv.setAdapter(null);
       Toast.makeText(this,"Tabla vacia",Toast.LENGTH_LONG).show();
      }


    }

    public void btnBuscar_click(View view) {
        Cursor C;
        DAOUsuarios ado = new DAOUsuarios(getApplicationContext());
        String Nombre =nombre.getText().toString();
        if(!Nombre.isEmpty()){
            C=ado.getBusqueda(new Usuario(Nombre));
            if(C.moveToFirst()){
             nombre.setText(C.getString(1));
                telefono.setText(C.getString(2));
               email.setText(C.getString(3));
               redSocial.setText(C.getString(4));
               fecha.setText(C.getString(5));
            }else{
                Toast.makeText(this, "Error al buscar",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "Se requiere nombre de usuario",Toast.LENGTH_LONG).show();
        }
    }

    public void btnEliminar_click(View view) {
        DAOUsuarios ado = new DAOUsuarios(getApplicationContext());
        String Nombre =nombre.getText().toString();
        if(!Nombre.isEmpty()){
              if (ado.getBorar(new Usuario(Nombre))==1){
                  nombre.setText("");
                  Toast.makeText(this, "Borrado",Toast.LENGTH_LONG).show();
                  btnCargar();
              }else{
                  Toast.makeText(this, "Error al borar",Toast.LENGTH_LONG).show();
              }
        }else{
            Toast.makeText(this, "Se requiere nombre de usuario",Toast.LENGTH_LONG).show();
        }
    }
}
