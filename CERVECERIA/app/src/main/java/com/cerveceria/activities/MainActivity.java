package com.cerveceria.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
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
import com.cerveceria.POJOs.ProductoPOJO;
import com.cerveceria.R;
import com.cerveceria.adapters.ProductoAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ProductoAdapter.OnItemClickListener {
    public static final String Extraid = "id";
    public static final String Extradescripcion = "descripcion";
    public static final String Extraconcepto = "concepto";
    public static final String Extrapreciolista = "lista";
    public static final String Extramarca = "marca";
    public static final String Extraml = "ml";
    public static final String Extraimagen = "imagen";
    public static final String Extrasabor = "sabor";
    public static final String Extratipo = "tipo";

    private RecyclerView rv;
    private ProductoAdapter pa;
    private ArrayList<ProductoPOJO> productos;
    private RequestQueue request;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView title = findViewById(R.id.toolbar_title);
        title.setText("Productos");
        BottomNavigationView navigation = findViewById(R.id.navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem =menu.getItem(0);
        menuItem.setChecked(true);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Inicio:
                        break;
                    case R.id.Carrito:
                        Intent a = new Intent(MainActivity.this, CarritoActivity.class);
                        startActivity(a);
                        finish();
                        break;
                    case R.id.Pedidos:
                        Intent b = new Intent(MainActivity.this, PedidoActivity.class);
                        startActivity(b);
                        finish();
                        break;
                    case R.id.Cuenta:
                        Intent c = new Intent(MainActivity.this, AjustesActivity.class);
                        startActivity(c);
                        finish();
                        break;
                }
                return false;
            }
        });

        rv= findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        productos = new ArrayList<>();

        request = Volley.newRequestQueue(this);
        parseJSON();
    }

    private void parseJSON(){
        final ProgressDialog progreso = new ProgressDialog(this);
        progreso.setMessage("Buscando...");
        progreso.show();
        //String url = "http://192.168.1.73/CERVECERIAPHPS/LOGUP.php?correo="+Correo.getText().toString()+"&contraseña="+Contrasena.getText().toString()+"&nombre="+Nombre.getText().toString()+ "&apellido="+Apellido.getText().toString();
        String url = "http://educere.com.mx/CERVECERIAPHPS/FRAGMENTINICIO.php";
        url = url.replace(" ", "%20");

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url,null , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            try{
                JSONArray jsonArray = response.getJSONArray("datos");

                for(int i=0; i<jsonArray.length(); i++){
                    JSONObject product = jsonArray.getJSONObject(i);

                    ProductoPOJO producto = new ProductoPOJO();

                    producto.setIid(product.getInt("id"));
                    producto.setDescripcion(product.getString("descripcion"));
                    producto.setConcepto(product.getString("concepto"));
                    producto.setPreciolista(product.getInt("preciolista"));
                    producto.setMarca(product.getString("marca"));
                    producto.setMl(product.getInt("ml"));
                    producto.setImagen("http://educere.com.mx/CERVECERIAIMAGENES/"+product.getString("imagen"));
                    producto.setSabor(product.getString("sabor"));
                    producto.setTipo(product.getString("tipo"));

                    productos.add(producto);
                }

                pa = new ProductoAdapter(MainActivity.this, productos);
                rv.setAdapter(pa);
                pa.setOnItemClickListener(MainActivity.this);

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
        Intent detailIntent = new Intent(this, DescripcionActivity.class);
        ProductoPOJO clickedItem = productos.get(i);

        detailIntent.putExtra(Extraid, clickedItem.getIid());
        detailIntent.putExtra(Extradescripcion, clickedItem.getDescripcion());
        detailIntent.putExtra(Extraconcepto, clickedItem.getConcepto());
        detailIntent.putExtra(Extrapreciolista, clickedItem.getPreciolista());
        detailIntent.putExtra(Extramarca, clickedItem.getMarca());
        detailIntent.putExtra(Extraml, clickedItem.getMl());
        detailIntent.putExtra(Extraimagen, clickedItem.getImagen());
        detailIntent.putExtra(Extrasabor, clickedItem.getSabor());
        detailIntent.putExtra(Extratipo, clickedItem.getTipo());

        startActivity(detailIntent);
    }
}