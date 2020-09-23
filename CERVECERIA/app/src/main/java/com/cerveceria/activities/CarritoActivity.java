package com.cerveceria.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cerveceria.R;
import com.cerveceria.adapters.CarritoAdapter;
import com.cerveceria.globals.Global;

public class CarritoActivity extends AppCompatActivity implements CarritoAdapter.OnItemClickListener {
    RecyclerView rv;
    CarritoAdapter ca;
    TextView total;
    ImageButton pagar;
    int cuenta=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        rv= findViewById(R.id.rvcar);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        TextView title = findViewById(R.id.toolbar_title);
        title.setText("Productos");
        BottomNavigationView navigation = findViewById(R.id.navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem=menu.getItem(1);
        menuItem.setChecked(true);

        total = findViewById(R.id.cuentapedidos);
        cuenta = 0;
        for(int i  = 0; i< Global.carrito.size(); i++){
            cuenta = cuenta + Global.carrito.get(i).getSubtotal();
        }
        total.setText("Total: $"+cuenta+".00");

        pagar = findViewById(R.id.pay);
        pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cuenta!=0){
                    Global.orden.setPreciototal(cuenta);
                    Global.orden.setIdcliente(Global.usuario.getIid());
                    Intent intent = new Intent(getApplicationContext(), DireccionesActivity.class);
                    intent.putExtra("Titulo", "Elegir una dirección de envío");
                    startActivity(intent);
                }
            }
        });

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.Inicio:
                    Intent a = new Intent(CarritoActivity.this, MainActivity.class);
                    startActivity(a);
                    break;
                case R.id.Carrito:
                    break;
                case R.id.Pedidos:
                    Intent b = new Intent(CarritoActivity.this, PedidoActivity.class);
                    startActivity(b);
                    break;
                case R.id.Cuenta:
                    Intent c = new Intent(CarritoActivity.this, AjustesActivity.class);
                    startActivity(c);
                    break;
            }
            return false;
            }
        });

        ca = new CarritoAdapter(CarritoActivity.this);
        rv.setAdapter(ca);
        ca.setOnItemClickListener(CarritoActivity.this);
    }

    @Override
    public void onibmas(int i) {
        Global.carrito.get(i).setCantidad(Global.carrito.get(i).getCantidad()+1);
        Global.carrito.get(i).setSubtotal(Global.carrito.get(i).getPreciolista()*Global.carrito.get(i).getCantidad());
        ca.notifyItemChanged(i);

        cuenta=0;
        for(int j=0; j<Global.carrito.size(); j++){
            cuenta = cuenta + Global.carrito.get(j).getSubtotal();
        }
        total.setText("Total: $"+cuenta+".00");
    }

    @Override
    public void onibmenos(int i) {
        if(Global.carrito.get(i).getCantidad()>1){
            Global.carrito.get(i).setCantidad(Global.carrito.get(i).getCantidad()-1);
            Global.carrito.get(i).setSubtotal(Global.carrito.get(i).getPreciolista()*Global.carrito.get(i).getCantidad());
            ca.notifyItemChanged(i);

            cuenta=0;
            for(int j=0; j<Global.carrito.size(); j++){
                cuenta = cuenta + Global.carrito.get(j).getSubtotal();
            }
            total.setText("Total: $"+cuenta+".00");
        }
    }

    @Override
    public void ondelete(int i) {
        Global.carrito.remove(i);
        ca.notifyItemRemoved(i);
        ca.notifyItemRangeChanged(i, ca.getItemCount());

        cuenta=0;
        for(int j=0; j<Global.carrito.size(); j++){
            cuenta = cuenta + Global.carrito.get(j).getSubtotal();
        }
        total.setText("Total: $"+cuenta+".00");
    }
}