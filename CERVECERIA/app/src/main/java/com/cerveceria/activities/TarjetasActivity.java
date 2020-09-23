package com.cerveceria.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cerveceria.POJOs.DireccionPOJO;
import com.cerveceria.POJOs.TarjetaPOJO;
import com.cerveceria.R;
import com.cerveceria.adapters.DireccionAdapter;
import com.cerveceria.adapters.TarjetaAdapter;
import com.cerveceria.globals.Global;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TarjetasActivity extends AppCompatActivity implements TarjetaAdapter.OnItemClickListener {
    public static final String Extraid = "id";
    public static final String Extraidcliente = "idcliente";
    public static final String Extranombretarjeta = "nombretarjeta";
    public static final String Extranumerotarjeta = "numerotarjeta";
    public static final String Extrafechavencimiento = "fechavencimiento";
    public static final String Extracodigoseguridad = "codigoseguridad";

    FloatingActionButton fab;
    TextView titulo;
    String s;
    private RecyclerView rv;
    private TarjetaAdapter ta;
    private ArrayList<TarjetaPOJO> tarjetas;
    private RequestQueue request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjetas);

        Intent intent = getIntent();
        s = intent.getStringExtra("Titulo");

        titulo = findViewById(R.id.toolbar_title);
        titulo.setText(s);

        rv=findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        fab= findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x = new Intent(getApplicationContext(), AnadirTarjetaActivity.class);
                startActivityForResult(x, 1);
            }
        });

        rv= findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        tarjetas = new ArrayList<>();

        ta = new TarjetaAdapter(TarjetasActivity.this, tarjetas);
        rv.setAdapter(ta);
        ta.setOnItemClickListener(TarjetasActivity.this);

        request = Volley.newRequestQueue(this);
        printJSON();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==RESULT_OK){
            printJSON();
        }
    }

    private void printJSON(){
        tarjetas.clear();
        final ProgressDialog progreso = new ProgressDialog(this);
        progreso.setMessage("Buscando...");
        progreso.show();

        //String url = "http://192.168.1.73/CERVECERIAPHPS/LOGUP.php?correo="+Correo.getText().toString()+"&contraseña="+Contrasena.getText().toString()+"&nombre="+Nombre.getText().toString()+ "&apellido="+Apellido.getText().toString();
        String url = "http://educere.com.mx/CERVECERIAPHPS/BUSCATARJETAS.php?id="+ Global.usuario.getIid();
        url = url.replace(" ", "%20");

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray jsonArray = response.getJSONArray("datos");

                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject cards = jsonArray.getJSONObject(i);

                        TarjetaPOJO tarjeta = new TarjetaPOJO();

                        tarjeta.setIid(cards.getInt("id"));
                        tarjeta.setNombretarjeta(cards.getString("Nombretarjeta"));
                        tarjeta.setNumerotarjeta(cards.getString("Numerotarjeta"));
                        tarjeta.setFechavencimiento(cards.getString("Fechavencimiento"));
                        tarjeta.setCodigoseguridad(cards.getString("CCV"));
                        tarjeta.setIdcliente(cards.getInt("idcliente"));

                        tarjetas.add(tarjeta);
                    }

                    ta.notifyDataSetChanged();
                    progreso.hide();

                } catch (JSONException e) {
                    e.printStackTrace();
                    progreso.hide();
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

    @Override
    public void onItemClick(int i) {
        if(s.equals("Elegir un método de pago")){
            final ProgressDialog all = new ProgressDialog(this);
            all.setMessage("Procesando pago...");
            all.show();
            Global.orden.setEnviado(1);
            Global.orden.setPagado(1);
            Global.orden.setComision(10);
            Global.orden.setPreciototal(Global.orden.getPreciototal()+Global.orden.getCosto()+Global.orden.getComision());
            Global.orden.setIdtarjeta(tarjetas.get(i).getIid());
            Global.orden.setEstado("En transporte");

            request = Volley.newRequestQueue(this);
            envioJSON(all);
        }
        else{
            Intent detailIntent = new Intent(this, TarjetaActivity.class);
            TarjetaPOJO clickedItem = tarjetas.get(i);

            detailIntent.putExtra(Extraid, clickedItem.getIid());
            detailIntent.putExtra(Extranombretarjeta, clickedItem.getNombretarjeta());
            detailIntent.putExtra(Extranumerotarjeta, clickedItem.getNumerotarjeta());
            detailIntent.putExtra(Extrafechavencimiento, clickedItem.getFechavencimiento());
            detailIntent.putExtra(Extracodigoseguridad, clickedItem.getCodigoseguridad());
            detailIntent.putExtra(Extraidcliente, clickedItem.getIdcliente());

            startActivity(detailIntent);
        }
    }

    @Override
    public void onDelete(int i){
        request = Volley.newRequestQueue(this);
        eraseJSON(i);
        tarjetas.remove(i);
        ta.notifyDataSetChanged();
    }

    @Override
    public void onEdit(int i) {
        request = Volley.newRequestQueue(this);
        eraseJSON(i);
        tarjetas.remove(i);
        Intent add = new Intent(this, AnadirTarjetaActivity.class);
        startActivity(add);
        ta.notifyDataSetChanged();
    }

    private void eraseJSON(int i){
        //String url = "http://192.168.1.73/CERVECERIAPHPS/LOGUP.php?correo="+Correo.getText().toString()+"&contraseña="+Contrasena.getText().toString()+"&nombre="+Nombre.getText().toString()+ "&apellido="+Apellido.getText().toString();
        String url = "http://educere.com.mx/CERVECERIAPHPS/ELIMINATARJETA.php?id="+tarjetas.get(i).getIid();
        url = url.replace(" ", "%20");

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ta.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        request.add(jsonObjectRequest);
    }

    private void envioJSON(final ProgressDialog all){
        String url = "http://educere.com.mx/CERVECERIAPHPS/CREAENVIO.php?paqueteria="+Global.orden.getPaqueteria()+"&costo="+Global.orden
                .getCosto()+"&enviado="+Global.orden.getEnviado()+"&iddomicilio="+Global.orden.getIddomicilio();
        url = url.replace(" ", "%20");

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("datos");

                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    Global.orden.setIdenvio(jsonObject.getInt("id"));

                    pagoJSON(all);

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

    private void pagoJSON(final ProgressDialog all){
        String url = "http://educere.com.mx/CERVECERIAPHPS/CREAPAGO.php?comision="+Global.orden.getComision()+"&pagado="+Global.orden.getPagado()+"&idtarjeta="+Global.orden.getIdtarjeta();
        url = url.replace(" ", "%20");

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("datos");

                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    Global.orden.setIdpago(jsonObject.getInt("id"));

                    ordenJSON(all);

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
    private void ordenJSON(final ProgressDialog all){
        String url = "http://educere.com.mx/CERVECERIAPHPS/CREAORDEN.php?estado="+Global.orden.getEstado()+"&preciototal="+Global.orden.getPreciototal()+"&idpago="+Global.orden.getIdpago()+"&idcliente="+Global.orden.getIdcliente()+"&idenvio="+Global.orden.getIdenvio();
        url = url.replace(" ", "%20");

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("datos");

                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    Global.orden.setIid(jsonObject.getInt("id"));

                    Global.carrito.clear();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                    all.hide();

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
