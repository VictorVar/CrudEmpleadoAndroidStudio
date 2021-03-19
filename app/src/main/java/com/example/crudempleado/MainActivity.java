package com.example.crudempleado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText nombre, apellido, cedula, cod_emp, departamento, direccion, sueldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nombre = findViewById(R.id.nombre);
        apellido = findViewById(R.id.apellido);
        cedula = findViewById(R.id.cedula);
        cod_emp = findViewById(R.id.cod_emp);
        departamento = findViewById(R.id.departamento);
        direccion = findViewById(R.id.direccion);
        sueldo = findViewById(R.id.sueldo);

    }

    public void entrar(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String anombre = nombre.getText().toString();
        String aapellido = apellido.getText().toString();
        String acedula = cedula.getText().toString();
        String acod_emp = cod_emp.getText().toString();
        String adepartamento = departamento.getText().toString();
        String adireccion = direccion.getText().toString();
        String asueldo = sueldo.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("nombre", anombre);
        registro.put("apellido", aapellido);
        registro.put("cedula", acedula);
        registro.put("codigo_empleado", acod_emp);
        registro.put("direccion", adireccion);
        registro.put("departamento", adepartamento);
        registro.put("sueldo", asueldo);
        bd.insert("Empleados", null, registro);
        bd.close();
        nombre.setText("");
        apellido.setText("");
        cedula.setText("");
        cod_emp.setText("");
        departamento.setText("");
        direccion.setText("");
        sueldo.setText("");
        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void cancel(View v) {
        nombre.setText("");
        apellido.setText("");
        cedula.setText("");
        cod_emp.setText("");
        departamento.setText("");
        direccion.setText("");
        sueldo.setText("");
        Toast.makeText(this, "Cleaned field", Toast.LENGTH_SHORT).show();
    }

    public void consultarporcod(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String acod_emp = cod_emp.getText().toString();
        Cursor fila = bd.rawQuery("select nombre,apellido,cedula,direccion,departamento,sueldo from Empleados where codigo_empleado=" + acod_emp, null);
        if (fila.moveToFirst()) {
            nombre.setText(fila.getString(0));
            apellido.setText(fila.getString(1));
            cedula.setText(fila.getString(2));
            departamento.setText(fila.getString(3));
            direccion.setText(fila.getString(4));
            sueldo.setText(fila.getString(5));
        } else
            Toast.makeText(this, "there is no employee with that code", Toast.LENGTH_SHORT).show();
        bd.close();
    }

    public void borrar(View V) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String acod_emp = cod_emp.getText().toString();
        int cant = bd.delete("Empleados", "codigo_empleado=" + acod_emp, null);
        bd.close();
        nombre.setText("");
        apellido.setText("");
        cedula.setText("");
        cod_emp.setText("");
        departamento.setText("");
        direccion.setText("");
        sueldo.setText("");
        if (cant == 1) {
            Toast.makeText(this, "Dates deleted with that employe code", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Not exist a employe with that employe code", Toast.LENGTH_SHORT).show();
        }

    }
    public void editar(View V){
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            String anombre= nombre.getText().toString();
            String aapellido=apellido.getText().toString();
            String acedula=cedula.getText().toString();
            String acod_emp=cod_emp.getText().toString();
            String adepartamento=departamento.getText().toString();
            String adireccion=direccion.getText().toString();
            String asueldo= sueldo.getText().toString();
            ContentValues registro = new ContentValues();
            registro.put("nombre", anombre);
            registro.put("apellido",aapellido);
            registro.put("cedula", acedula);
            registro.put("codigo_empleado", acod_emp);
            registro.put("departamento", adepartamento);
            registro.put("direccion", adireccion);
            registro.put("sueldo", asueldo);
            int cant = bd.update("Empleados", registro, "codigo_empleado=" + acod_emp, null);
            bd.close();
            if (cant == 1)
                Toast.makeText(this, "Dates was modified", Toast.LENGTH_SHORT)
                        .show();
            else
                Toast.makeText(this, "Not exist a employe with that code",
                        Toast.LENGTH_SHORT).show();
        }
    }