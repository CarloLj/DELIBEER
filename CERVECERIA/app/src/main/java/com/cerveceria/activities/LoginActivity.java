package com.cerveceria.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cerveceria.POJOs.ClientePOJO;
import com.cerveceria.R;
import com.cerveceria.globals.Global;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    EditText nombre, contrasena;
    Button ingresar;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    ProgressDialog progreso;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nombre = findViewById(R.id.nombredeusuario);
        contrasena = findViewById(R.id.password);

        ingresar = findViewById(R.id.ingresar);

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request = Volley.newRequestQueue(getApplicationContext());
                parseJSON();
            }
        });
    }

    public void parseJSON() {
        progreso = new ProgressDialog(LoginActivity.this);
        progreso.setMessage("Iniciando sesion...");
        progreso.show();

        if ((nombre.getText().toString().trim().length() == 0 )||(contrasena.getText().toString().trim().length() == 0 )){
            Toast.makeText(getApplicationContext(), "Por favor, introduzca todos los campos requeridos", Toast.LENGTH_SHORT).show();
            progreso.hide();
        }else {
            //String server_url = "http://192.168.1.73/CERVECERIAPHPS/LOGIN.php?nombre="+nombre.getText().toString()+"&contraseña="+contrasena.getText().toString();
            String url = "http://educere.com.mx/CERVECERIAPHPS/LOGIN.php?nombre="+nombre.getText().toString()+"&contraseña="+contrasena.getText().toString();
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progreso.hide();
                    try {
                        JSONArray jsonArray = response.getJSONArray("datos");

                        JSONObject jsonObject = jsonArray.getJSONObject(0);

                        Global.usuario.setIid(jsonObject.getInt("id"));
                        Global.usuario.setNombre(jsonObject.getString("nombre"));
                        Global.usuario.setApellido(jsonObject.getString("apellido"));
                        Global.usuario.setContrasena(jsonObject.getString("contraseña"));
                        Global.usuario.setCorreo(jsonObject.getString("correo"));

                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progreso.hide();
                    Toast.makeText(LoginActivity.this, "Algo fallo", Toast.LENGTH_SHORT).show();
                }
            });
            request.add(jsonObjectRequest);
        }
    }

    public void LOGUP(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
}
