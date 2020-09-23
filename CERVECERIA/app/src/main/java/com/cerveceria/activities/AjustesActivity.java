package com.cerveceria.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cerveceria.R;
import com.cerveceria.globals.Global;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AjustesActivity extends AppCompatActivity {

    TextView correo, nombreapellido, cerrarSesion, verTarjetas, verDirecciones, darseBaja;
    private RequestQueue request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        nombreapellido=findViewById(R.id.nombreapellido);
        correo=findViewById(R.id.correo);

        nombreapellido.setText(Global.usuario.getNombre()+" "+Global.usuario.getApellido());
        correo.setText(Global.usuario.getCorreo());

        cerrarSesion = findViewById(R.id.cerrar_sesion);
        verTarjetas = findViewById(R.id.tarjetas);
        verDirecciones = findViewById(R.id.direcciones);
        darseBaja = findViewById(R.id.dar_baja);

        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CerrarSesion();
            }
        });

        verTarjetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerTarjetas();
            }
        });
        verDirecciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerDirecciones();
            }
        });
        darseBaja.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DarBaja();
            }
        });

        TextView title = findViewById(R.id.toolbar_title);
        title.setText("Mi cuenta");
        BottomNavigationView navigation = findViewById(R.id.navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem =menu.getItem(3);
        menuItem.setChecked(true);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Inicio:
                        Intent a = new Intent(AjustesActivity.this, MainActivity.class);
                        startActivity(a);
                        finish();
                        break;
                    case R.id.Carrito:
                        Intent b = new Intent(AjustesActivity.this, CarritoActivity.class);
                        startActivity(b);
                        finish();
                        break;
                    case R.id.Pedidos:
                        Intent c = new Intent(AjustesActivity.this, PedidoActivity.class);
                        startActivity(c);
                        finish();
                        break;
                    case R.id.Cuenta:
                        break;
                }
                return false;
            }
        });
    }
    private void CerrarSesion() {
        Global.carrito.clear();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void VerTarjetas() {
        Intent intent = new Intent(this, TarjetasActivity.class);
        intent.putExtra("Titulo", "Ver métodos de pago");
        startActivity(intent);
    }

    private void VerDirecciones() {
        Intent intent = new Intent(this, DireccionesActivity.class);
        intent.putExtra("Titulo", "Ver direcciones de envío");
        startActivity(intent);
    }
    private void DarBaja(){
        Global.carrito.clear();
        request = Volley.newRequestQueue(this);
        parseJSON();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    private void parseJSON(){
        //String url = "http://192.168.1.73/CERVECERIAPHPS/LOGUP.php?correo="+Correo.getText().toString()+"&contraseña="+Contrasena.getText().toString()+"&nombre="+Nombre.getText().toString()+ "&apellido="+Apellido.getText().toString();
        String url = "http://educere.com.mx/CERVECERIAPHPS/ELIMINACLIENTE.php?nombre="+
        Global.usuario.getNombre()+"&contraseña="+Global.usuario.getContrasena();
        url = url.replace(" ", "%20");

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url,null , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray jsonArray = response.getJSONArray("datos");

                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject product = jsonArray.getJSONObject(i);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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