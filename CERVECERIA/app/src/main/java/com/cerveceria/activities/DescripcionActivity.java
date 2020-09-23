package com.cerveceria.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cerveceria.POJOs.CarritoPOJO;
import com.cerveceria.R;
import com.cerveceria.globals.Global;
import com.squareup.picasso.Picasso;

import static com.cerveceria.activities.MainActivity.Extraconcepto;
import static com.cerveceria.activities.MainActivity.Extradescripcion;
import static com.cerveceria.activities.MainActivity.Extraid;
import static com.cerveceria.activities.MainActivity.Extraimagen;
import static com.cerveceria.activities.MainActivity.Extramarca;
import static com.cerveceria.activities.MainActivity.Extraml;
import static com.cerveceria.activities.MainActivity.Extrapreciolista;
import static com.cerveceria.activities.MainActivity.Extrasabor;
import static com.cerveceria.activities.MainActivity.Extratipo;

public class DescripcionActivity extends AppCompatActivity {

    Intent i;
    int cant=1;
    int preciototal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion);

        Intent intent = getIntent();
        final int id = intent.getIntExtra(Extraid, 0);
        final String descripcion = intent.getStringExtra(Extradescripcion);
        final String concepto = intent.getStringExtra(Extraconcepto);
        final String marca = intent.getStringExtra(Extramarca);
        final String imagen = intent.getStringExtra(Extraimagen);
        final String sabor = intent.getStringExtra(Extrasabor);
        final String tipo = intent.getStringExtra(Extratipo);
        final int preciolista = intent.getIntExtra(Extrapreciolista, 0);
        final int ml = intent.getIntExtra(Extraml, 0);
        preciototal=preciolista;

        ImageButton ibmas = findViewById(R.id.mas);
        ImageButton ibmenos = findViewById(R.id.menos);
        Button bagregarcarrito = findViewById(R.id.agregarcarrito);
        ImageView ivimagen = findViewById(R.id.producto);
        final TextView tvcant = findViewById(R.id.cant);
        final TextView tvpreciototal = findViewById(R.id.preciototal);
        TextView tvdescripcion = findViewById(R.id.descripcion);
        TextView tvconcepto = findViewById(R.id.concepto);
        TextView tvmarca = findViewById(R.id.marca);
        TextView tvsabor = findViewById(R.id.sabor);
        TextView tvtipo = findViewById(R.id.tipo);
        TextView tvpreciolista = findViewById(R.id.preciolista);
        TextView tvml = findViewById(R.id.ml);

        ibmas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cant+=1;
                tvcant.setText(""+cant);
                preciototal=preciolista*cant;
                tvpreciototal.setText("$"+preciototal+".00");
            }
        });

        ibmenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cant>1){
                    cant-=1;
                    tvcant.setText(""+cant);
                    preciototal=preciolista*cant;
                    tvpreciototal.setText("$"+preciototal+".00");
                }
            }
        });

        bagregarcarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean skip=false;
                for(int i=0; i<Global.carrito.size(); i++){
                    if(id==Global.carrito.get(i).getIdproducto()){
                        Global.carrito.get(i).setCantidad(Global.carrito.get(i).getCantidad()+cant);
                        Global.carrito.get(i).setSubtotal(Global.carrito.get(i).getSubtotal()+cant*preciolista);
                        skip=true;
                        break;
                    }
                }
                if(!skip){
                    CarritoPOJO carritoPOJO=new CarritoPOJO();
                    carritoPOJO.setConcepto(concepto);
                    carritoPOJO.setPreciolista(preciolista);
                    carritoPOJO.setMarca(marca);
                    carritoPOJO.setMl(ml);
                    carritoPOJO.setDescripcion(descripcion);
                    carritoPOJO.setImagen(imagen);
                    carritoPOJO.setSabor(sabor);
                    carritoPOJO.setTipo(tipo);
                    carritoPOJO.setCantidad(cant);
                    carritoPOJO.setDescuento(0);
                    carritoPOJO.setSubtotal(preciototal);
                    carritoPOJO.setIdproducto(id);

                    Global.carrito.add(carritoPOJO);
                }

                i=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        Picasso.get().load(imagen).into(ivimagen);
        tvcant.setText(""+1);
        tvpreciolista.setText("$"+preciolista+".00");
        tvpreciototal.setText("$"+preciolista+".00");
        tvconcepto.setText(concepto);
        tvdescripcion.setText(descripcion);
        tvmarca.setText(marca);
        tvsabor.setText(sabor);
        tvtipo.setText(tipo);
        tvml.setText(""+ml+" ml");
    }
}
