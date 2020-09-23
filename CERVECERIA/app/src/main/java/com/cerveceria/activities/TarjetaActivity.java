package com.cerveceria.activities;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.cerveceria.R;
import com.squareup.picasso.Picasso;

import static com.cerveceria.activities.TarjetasActivity.Extraid;
import static com.cerveceria.activities.TarjetasActivity.Extraidcliente;
import static com.cerveceria.activities.TarjetasActivity.Extrafechavencimiento;
import static com.cerveceria.activities.TarjetasActivity.Extranombretarjeta;
import static com.cerveceria.activities.TarjetasActivity.Extranumerotarjeta;

public class TarjetaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjeta);

        Intent intent = getIntent();
        int id = intent.getIntExtra(Extraid, 0);
        String nombretarjeta = intent.getStringExtra(Extranombretarjeta);
        String numerotarjeta = intent.getStringExtra(Extranumerotarjeta);
        String fechatarjeta = intent.getStringExtra(Extrafechavencimiento);
        int idcliente = intent.getIntExtra(Extraidcliente, 0);


        String a=numerotarjeta.substring(0,1);
        String url;
        switch (a){
            case "4":
                url = "http://educere.com.mx/CERVECERIATARJETAS/VISA.png";
                break;
            case "5":
                url = "http://educere.com.mx/CERVECERIATARJETAS/MC.png";
                break;
            case "6":
                url = "http://educere.com.mx/CERVECERIATARJETAS/DISC.png";
                break;
            default:
                url = "http://educere.com.mx/CERVECERIATARJETAS/AE.png";
        }

        TextView tvnombretarjeta = findViewById(R.id.nombretar);
        TextView tvnumerotarjeta = findViewById(R.id.numerotar);
        TextView tvfechatarjeta = findViewById(R.id.fechatar);
        ImageView ivtarjeta = findViewById(R.id.tar);

        tvnombretarjeta.setText(nombretarjeta);
        tvnumerotarjeta.setText(numerotarjeta);
        tvfechatarjeta.setText(fechatarjeta);
        Picasso.get().load(url).into(ivtarjeta);

    }
}
