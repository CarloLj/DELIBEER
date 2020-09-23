package com.cerveceria.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.cerveceria.R;

import static com.cerveceria.activities.PedidoActivity.Extracomision;
import static com.cerveceria.activities.PedidoActivity.Extracosto;
import static com.cerveceria.activities.PedidoActivity.Extraenviado;
import static com.cerveceria.activities.PedidoActivity.Extrafechaenvio;
import static com.cerveceria.activities.PedidoActivity.Extrafechaestimada;
import static com.cerveceria.activities.PedidoActivity.Extrafechapago;
import static com.cerveceria.activities.PedidoActivity.Extraid;
import static com.cerveceria.activities.PedidoActivity.Extrapagado;
import static com.cerveceria.activities.PedidoActivity.Extrapaqueteria;
import static com.cerveceria.activities.PedidoActivity.Extrapreciototal;
import static com.cerveceria.activities.PedidoActivity.Extraestado;
import static com.cerveceria.activities.PedidoActivity.Extrafecha;

public class DetallesActivity extends AppCompatActivity {
    TextView tvid, tvpreciototal, tvfecha, tvestado, tvcomision, tvfechapago, tvcosto, tvpaqueteria, tvfechaenvio, tvfechaestimada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        Intent intent = getIntent();
        final int id = intent.getIntExtra(Extraid, 0);
        final int preciototal = intent.getIntExtra(Extrapreciototal, 0);
        final String fecha = intent.getStringExtra(Extrafecha);
        final String estado = intent.getStringExtra(Extraestado);

        final int comision = intent.getIntExtra(Extracomision, 0);
        final int pagado = intent.getIntExtra(Extrapagado, 0);
        final String fechapago = intent.getStringExtra(Extrafechapago);

        final int costo = intent.getIntExtra(Extracosto, 0);
        final int enviado = intent.getIntExtra(Extraenviado, 0);
        final String paqueteria = intent.getStringExtra(Extrapaqueteria);
        final String fechaenvio = intent.getStringExtra(Extrafechaenvio);
        final String fechaestimada = intent.getStringExtra(Extrafechaestimada);

        tvid = findViewById(R.id.tvid);
        tvpreciototal = findViewById(R.id.tvpreciototal);
        tvfecha = findViewById(R.id.tvfecha);
        tvestado = findViewById(R.id.tvestado);
        tvcomision = findViewById(R.id.tvcomision);
        tvfechapago = findViewById(R.id.tvfechapago);
        tvcosto= findViewById(R.id.tvcosto);
        tvpaqueteria = findViewById(R.id.tvpaqueteria);
        tvfechaenvio = findViewById(R.id.tvfechaenvio);
        tvfechaestimada = findViewById(R.id.tvfechaestimada);

        tvid.setText("Pedido #"+id);
        tvpreciototal.setText("Precio total: $"+preciototal+".00");
        tvfecha.setText("Realizado el: "+fecha);
        tvestado.setText("Estado: "+estado);
        tvcomision.setText("Costo de comision: $"+comision+".00");
        tvfechapago.setText("Pagado el: "+fechapago);
        tvcosto.setText("Costo de envio: $"+costo+".00");
        tvpaqueteria.setText("Paquetería: "+paqueteria);
        tvfechaenvio.setText("Enviado el: "+fechaenvio);
        tvfechaestimada.setText("Llega aproximadamente el: "+fechaestimada);
    }
}
