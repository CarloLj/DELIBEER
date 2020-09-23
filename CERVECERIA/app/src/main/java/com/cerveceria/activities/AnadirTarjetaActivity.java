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

public class AnadirTarjetaActivity extends AppCompatActivity {

    private RequestQueue request;
    TextView nombre, numero, fecha, codigo;
    Button confirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_tarjeta);

        nombre = findViewById(R.id.nombretarjeta);
        numero = findViewById(R.id.numerotarjeta);
        fecha = findViewById(R.id.fechavencimiento);
        codigo = findViewById(R.id.codigoseguridad);

        confirmar = findViewById(R.id.confirmarcard);

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( nombre.getText().toString().trim().length()>0 &&
                        numero.getText().toString().trim().length()>0 &&
                        fecha.getText().toString().trim().length()>0 &&
                        codigo.getText().toString().trim().length()>0){

                    request = Volley.newRequestQueue(getApplicationContext());
                    parseJSON();
                }
            }
        });
    }

    private void parseJSON(){
        //String url = "http://192.168.1.73/CERVECERIAPHPS/LOGUP.php?correo="+Correo.getText().toString()+"&contraseña="+Contrasena.getText().toString()+"&nombre="+Nombre.getText().toString()+ "&apellido="+Apellido.getText().toString();
        String url = "http://educere.com.mx/CERVECERIAPHPS/CREATARJETA.php?Nombretarjeta="+nombre.getText().toString()+"&Numerotarjeta="
                +numero.getText().toString()+"&Fechavencimiento="+fecha.getText().toString()+"&CCV="+codigo.getText().toString()+"&idcliente="+ Global.usuario.getIid();

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
