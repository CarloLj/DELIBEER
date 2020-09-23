package com.cerveceria.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cerveceria.R;
import com.cerveceria.globals.Global;

import org.json.JSONObject;

public class AnadirDireccionActivity extends AppCompatActivity {

    private RequestQueue request;
    TextView nombre, apellido, telefono, codigopostal, colonia, calle, numeroext, numeroint, referencias;
    Button confirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_direccion);

        nombre = findViewById(R.id.nombredest);
        apellido = findViewById(R.id.apellidodest);
        telefono = findViewById(R.id.telefono);
        codigopostal = findViewById(R.id.codigopostal);
        colonia = findViewById(R.id.colonia);
        calle = findViewById(R.id.calle);
        numeroext = findViewById(R.id.numeroext);
        numeroint = findViewById(R.id.numeroint);
        referencias = findViewById(R.id.referencias);

        confirmar = findViewById(R.id.confirmardir);

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nombre.getText().toString().trim().length()>0 &&
                        apellido.getText().toString().trim().length()>0 &&
                        telefono.getText().toString().trim().length()>0 &&
                        codigopostal.getText().toString().trim().length()>0 &&
                        colonia.getText().toString().trim().length()>0 &&
                        calle.getText().toString().trim().length()>0 &&
                        numeroext.getText().toString().trim().length()>0 ) {

                    request = Volley.newRequestQueue(getApplicationContext());
                    parseJSON();
                }
            }
        });
    }

    private void parseJSON(){
        //String url = "http://192.168.1.73/CERVECERIAPHPS/LOGUP.php?correo="+Correo.getText().toString()+"&contraseña="+Contrasena.getText().toString()+"&nombre="+Nombre.getText().toString()+ "&apellido="+Apellido.getText().toString();
        String url = "http://educere.com.mx/CERVECERIAPHPS/CREADIRECCION.php?Nombredest="+nombre.getText().toString()+"&Apellidodest="
                +apellido.getText().toString()+"&Telefono="+telefono.getText().toString()+"&Codigopostal="+codigopostal.getText().toString()
                +"&Colonia="+colonia.getText().toString()+"&Calle="+calle.getText().toString()+"&Numeroext="+numeroext.getText().toString()
                +"&Numeroint="+numeroint.getText().toString()+"&Referencias="+referencias.getText().toString()+"&idcliente="+ Global.usuario.getIid();

        url = url.replace(" ", "%20");

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url,null , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                    setResult(RESULT_OK,null);
                    finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        request.add(jsonObjectRequest);
    }
}
