package com.cerveceria.activities;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cerveceria.R;

import org.json.JSONObject;


public class SignupActivity extends AppCompatActivity {

    EditText id, Nombre, Apellido, Contrasena, Correo;
    Button guardar;
    ImageButton back;
    ProgressDialog progreso;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Nombre=findViewById(R.id.NOMBRE);
        Apellido=findViewById(R.id.Apellido);
        Correo=findViewById(R.id.Correo);
        Contrasena=findViewById(R.id.Contraseña);

        guardar= findViewById(R.id.Registrar);
        back = findViewById(R.id.back_conf);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Nombre.getText().toString().trim().length()>0 &&
                        Apellido.getText().toString().trim().length()>0 &&
                        Correo.getText().toString().trim().length()>0 &&
                        Contrasena.getText().toString().trim().length()>0) {

                    request = Volley.newRequestQueue(getApplicationContext());
                    cargarWebService();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Por favor, introduzca todos los campos requeridos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(com.cerveceria.activities.SignupActivity.this, LoginActivity.class );
                startActivity(i);
                finish();
            }
        });

    }

    private void cargarWebService() {

        progreso = new ProgressDialog(com.cerveceria.activities.SignupActivity.this);
        progreso.setMessage("Guardando...");
        progreso.show();

        //String url = "http://192.168.1.73/CERVECERIAPHPS/LOGUP.php?correo="+Correo.getText().toString()+"&contraseña="+Contrasena.getText().toString()+"&nombre="+Nombre.getText().toString()+ "&apellido="+Apellido.getText().toString();
        String url = "http://educere.com.mx/CERVECERIAPHPS/LOGUP.php?correo="+Correo.getText().toString()+"&contraseña="+Contrasena.getText().toString()+"&nombre="+Nombre.getText().toString()+ "&apellido="+Apellido.getText().toString();
        url = url.replace(" ", "%20");

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progreso.hide();
                Correo.setText("");
                Contrasena.setText("");
                Nombre.setText("");
                Apellido.setText("");
                Toast.makeText(getApplicationContext(),"Se ha registrado exitosamente",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(com.cerveceria.activities.SignupActivity.this, LoginActivity.class );
                startActivity(i);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                Correo.setText("");
                Contrasena.setText("");
                Nombre.setText("");
                Apellido.setText("");
                Toast.makeText(com.cerveceria.activities.SignupActivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);
    }
}
