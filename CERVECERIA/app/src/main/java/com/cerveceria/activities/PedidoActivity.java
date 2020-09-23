package com.cerveceria.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cerveceria.POJOs.OrdenPOJO;
import com.cerveceria.R;
import com.cerveceria.adapters.OrdenAdapter;
import com.cerveceria.globals.Global;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PedidoActivity extends AppCompatActivity implements OrdenAdapter.OnItemClickListener{
    public static final String Extraid = "id";
    public static final String Extrapreciototal = "preciototal";
    public static final String Extrafecha = "fecha";
    public static final String Extraestado = "estado";

    public static final String Extracomision = "comision";
    public static final String Extraidtarjeta = "idtarjeta";
    public static final String Extrapagado = "pagado";
    public static final String Extrafechapago = "fechapago";

    public static final String Extracosto = "costo";
    public static final String Extraiddomicilio = "iddomicilio";
    public static final String Extraenviado = "enviado";
    public static final String Extrapaqueteria = "paqueteria";
    public static final String Extrafechaenvio = "fechaenvio";
    public static final String Extrafechaestimada = "fechaestimada";

    private RecyclerView rv;
    private ArrayList<OrdenPOJO> ordenes;
    private RequestQueue request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        TextView title = findViewById(R.id.toolbar_title);
        title.setText("Mis pedidos");
        BottomNavigationView navigation = findViewById(R.id.navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem =menu.getItem(2);
        menuItem.setChecked(true);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Inicio:
                        Intent a = new Intent(PedidoActivity.this, MainActivity.class);
                        startActivity(a);
                        finish();
                        break;
                    case R.id.Carrito:
                        Intent b = new Intent(PedidoActivity.this, CarritoActivity.class);
                        startActivity(b);
                        finish();
                        break;
                    case R.id.Pedidos:
                        break;
                    case R.id.Cuenta:
                        Intent c = new Intent(PedidoActivity.this, AjustesActivity.class);
                        startActivity(c);
                        finish();
                        break;
                }
                return false;
            }
        });


        rv= findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        ordenes = new ArrayList<>();

        request = Volley.newRequestQueue(this);
        parseJSON();
    }

    private void parseJSON(){
        final ProgressDialog progreso = new ProgressDialog(this);
        progreso.setMessage("Buscando...");
        progreso.show();

        //String url = "http://192.168.1.73/CERVECERIAPHPS/LOGUP.php?correo="+Correo.getText().toString()+"&contraseña="+Contrasena.getText().toString()+"&nombre="+Nombre.getText().toString()+ "&apellido="+Apellido.getText().toString();
        String url = "http://educere.com.mx/CERVECERIAPHPS/BUSCAORDENES.php?id="+Global.usuario.getIid();
        url = url.replace(" ", "%20");

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url,null , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray jsonArray = response.getJSONArray("datos");

                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject order = jsonArray.getJSONObject(i);

                        OrdenPOJO orden = new OrdenPOJO();

                        orden.setIid(order.getInt("id"));
                        orden.setEstado(order.getString("estado"));
                        orden.setFecha(order.getString("fecha"));
                        orden.setPreciototal(order.getInt("preciototal"));

                        orden.setPaqueteria(order.getString("paqueteria"));
                        orden.setCosto(order.getInt("costo"));
                        orden.setEnviado(order.getInt("enviado"));
                        orden.setFechaenvio(order.getString("fechaenvio"));
                        orden.setFechaestimada(order.getString("fechaestimada"));

                        orden.setComision(order.getInt("comision"));
                        orden.setPagado(order.getInt("pagado"));
                        orden.setFechapago(order.getString("fechapago"));

                        ordenes.add(orden);
                    }

                    OrdenAdapter oa = new OrdenAdapter(PedidoActivity.this, ordenes);
                    rv.setAdapter(oa);
                    oa.setOnItemClickListener(PedidoActivity.this);
                    progreso.hide();

                } catch (JSONException e) {
                    progreso.hide();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                error.printStackTrace();
            }
        });
        request.add(jsonObjectRequest);
    }

    @Override
    public void onItemClick(int i) {
        Intent detailIntent = new Intent(this, DetallesActivity.class);
        OrdenPOJO clickedItem = ordenes.get(i);

        detailIntent.putExtra(Extraid, clickedItem.getIid());
        detailIntent.putExtra(Extrapreciototal, clickedItem.getPreciototal());
        detailIntent.putExtra(Extrafecha, clickedItem.getFecha());
        detailIntent.putExtra(Extraestado, clickedItem.getEstado());

        detailIntent.putExtra(Extracomision, clickedItem.getComision());
        detailIntent.putExtra(Extrapagado, clickedItem.getPagado());
        detailIntent.putExtra(Extrafechapago, clickedItem.getFechapago());

        detailIntent.putExtra(Extracosto, clickedItem.getCosto());
        detailIntent.putExtra(Extraenviado, clickedItem.getEnviado());
        detailIntent.putExtra(Extrapaqueteria, clickedItem.getPaqueteria());
        detailIntent.putExtra(Extrafechaenvio, clickedItem.getFechaenvio());
        detailIntent.putExtra(Extrafechaestimada, clickedItem.getFechaestimada());

        startActivity(detailIntent);
    }
}
