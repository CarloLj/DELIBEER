package com.cerveceria.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cerveceria.POJOs.DireccionPOJO;
import com.cerveceria.R;
import com.cerveceria.adapters.DireccionAdapter;
import com.cerveceria.globals.Global;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DireccionesActivity extends AppCompatActivity implements DireccionAdapter.OnItemClickListener {
    public static final String Extraid = "id";
    public static final String Extranombredest = "nombredest";
    public static final String Extraapellidodest = "apellidodest";
    public static final String Extratelefono = "telefono";
    public static final String Extracalle = "calle";
    public static final String Extranumeroext = "numeroext";
    public static final String Extranumeroint = "numeroint";
    public static final String Extracolonia = "colonia";
    public static final String Extracodigopostal = "codigopostal";
    public static final String Extrareferencias = "referencias";
    public static final String Extraidcliente = "idcliente";

    FloatingActionButton fab;
    TextView titulo;
    String s;
    private RecyclerView rv;
    private DireccionAdapter da;
    private ArrayList<DireccionPOJO> direcciones;
    private RequestQueue request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direcciones);

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
                Intent intent = new Intent(getApplicationContext(), AnadirDireccionActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        rv= findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        direcciones = new ArrayList<>();


        da = new DireccionAdapter(DireccionesActivity.this, direcciones);
        rv.setAdapter(da);
        da.setOnItemClickListener(DireccionesActivity.this);

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
        direcciones.clear();
        final ProgressDialog progreso = new ProgressDialog(this);
        progreso.setMessage("Buscando...");
        progreso.show();

        //String url = "http://192.168.1.73/CERVECERIAPHPS/LOGUP.php?correo="+Correo.getText().toString()+"&contraseña="+Contrasena.getText().toString()+"&nombre="+Nombre.getText().toString()+ "&apellido="+Apellido.getText().toString();
        String url = "http://educere.com.mx/CERVECERIAPHPS/BUSCADIRECCIONES.php?id="+Global.usuario.getIid();
        url = url.replace(" ", "%20");

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            try{
                JSONArray jsonArray = response.getJSONArray("datos");

                for(int i=0; i<jsonArray.length(); i++){
                    JSONObject address = jsonArray.getJSONObject(i);

                    DireccionPOJO direccion = new DireccionPOJO();

                    direccion.setIid(address.getInt("id"));
                    direccion.setNombredest(address.getString("Nombredest"));
                    direccion.setApellidodest(address.getString("Apellidodest"));
                    direccion.setTelefono(address.getString("Telefono"));
                    direccion.setCodigopostal(address.getString("Codigopostal"));
                    direccion.setColonia(address.getString("Colonia"));
                    direccion.setCalle(address.getString("Calle"));
                    direccion.setNumeroext(address.getInt("Numeroext"));
                    direccion.setNumeroint(address.getInt("Numeroint"));
                    direccion.setReferencias(address.getString("Referencias"));
                    direccion.setIdcliente(address.getInt("idcliente"));

                    direcciones.add(direccion);
                }

                da.notifyDataSetChanged();
                progreso.hide();

            } catch (JSONException e) {
                e.printStackTrace();
                progreso.hide();
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
        if(s.equals("Elegir una dirección de envío")){

            Global.orden.setCosto(100);
            Global.orden.setIddomicilio(direcciones.get(i).getIid());
            Global.orden.setPaqueteria("DHL");
            Intent intent = new Intent(this, TarjetasActivity.class);
            intent.putExtra("Titulo", "Elegir un método de pago");
            startActivity(intent);
        }
        else{
            Intent detailIntent = new Intent(this, DireccionActivity.class);
            DireccionPOJO clickedItem = direcciones.get(i);

            detailIntent.putExtra(Extraid, clickedItem.getIid());
            detailIntent.putExtra(Extranombredest, clickedItem.getNombredest());
            detailIntent.putExtra(Extraapellidodest, clickedItem.getApellidodest());
            detailIntent.putExtra(Extratelefono, clickedItem.getTelefono());
            detailIntent.putExtra(Extracalle, clickedItem.getCalle());
            detailIntent.putExtra(Extranumeroext, clickedItem.getNumeroext());
            detailIntent.putExtra(Extranumeroint, clickedItem.getNumeroint());
            detailIntent.putExtra(Extracolonia, clickedItem.getColonia());
            detailIntent.putExtra(Extracodigopostal, clickedItem.getCodigopostal());
            detailIntent.putExtra(Extrareferencias, clickedItem.getReferencias());
            detailIntent.putExtra(Extraidcliente, clickedItem.getIdcliente());

            startActivity(detailIntent);
        }
    }

    @Override
    public void onDelete(int i){
        request = Volley.newRequestQueue(this);
        eraseJSON(i);
        direcciones.remove(i);
        da.notifyDataSetChanged();
    }

    @Override
    public void onEdit(int i) {
        request = Volley.newRequestQueue(this);
        eraseJSON(i);
        direcciones.remove(i);
        Intent add = new Intent(this, AnadirDireccionActivity.class);
        startActivity(add);
        da.notifyDataSetChanged();
    }

    private void eraseJSON(int i){
        //String url = "http://192.168.1.73/CERVECERIAPHPS/LOGUP.php?correo="+Correo.getText().toString()+"&contraseña="+Contrasena.getText().toString()+"&nombre="+Nombre.getText().toString()+ "&apellido="+Apellido.getText().toString();
        String url = "http://educere.com.mx/CERVECERIAPHPS/ELIMINADIRECCION.php?id="+direcciones.get(i).getIid();
        url = url.replace(" ", "%20");

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                da.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        request.add(jsonObjectRequest);
    }

    private void editJSON(int i){
        //String url = "http://192.168.1.73/CERVECERIAPHPS/LOGUP.php?correo="+Correo.getText().toString()+"&contraseña="+Contrasena.getText().toString()+"&nombre="+Nombre.getText().toString()+ "&apellido="+Apellido.getText().toString();
        String url = "http://educere.com.mx/CERVECERIAPHPS/ELIMINADIRECCION.php?id="+direcciones.get(i).getIid();
        url = url.replace(" ", "%20");

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

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
