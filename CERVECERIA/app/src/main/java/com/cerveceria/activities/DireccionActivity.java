package com.cerveceria.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.cerveceria.R;

import static com.cerveceria.activities.DireccionesActivity.Extraapellidodest;
import static com.cerveceria.activities.DireccionesActivity.Extracalle;
import static com.cerveceria.activities.DireccionesActivity.Extracodigopostal;
import static com.cerveceria.activities.DireccionesActivity.Extracolonia;
import static com.cerveceria.activities.DireccionesActivity.Extraid;
import static com.cerveceria.activities.DireccionesActivity.Extraidcliente;
import static com.cerveceria.activities.DireccionesActivity.Extranombredest;
import static com.cerveceria.activities.DireccionesActivity.Extranumeroext;
import static com.cerveceria.activities.DireccionesActivity.Extranumeroint;
import static com.cerveceria.activities.DireccionesActivity.Extrareferencias;
import static com.cerveceria.activities.DireccionesActivity.Extratelefono;

public class DireccionActivity extends AppCompatActivity {

    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direccion);

        Intent intent = getIntent();

        int id = intent.getIntExtra(Extraid, 0);
        String nombredest = intent.getStringExtra(Extranombredest);
        String apellidodest = intent.getStringExtra(Extraapellidodest);
        String telefono = intent.getStringExtra(Extratelefono);
        String calle = intent.getStringExtra(Extracalle);
        int numeroext = intent.getIntExtra(Extranumeroext, 0);
        int numeroint = intent.getIntExtra(Extranumeroint, 0);
        String colonia = intent.getStringExtra(Extracolonia);
        String codigopostal = intent.getStringExtra(Extracodigopostal);
        String referencias = intent.getStringExtra(Extrareferencias);
        int idcliente = intent.getIntExtra(Extraidcliente, 0);

        TextView tvnombredest = findViewById(R.id.nombredestdir);
        TextView tvapellidodest = findViewById(R.id.apellidodestdir);
        TextView tvtelefono = findViewById(R.id.telefonodir);
        TextView tvcalle = findViewById(R.id.calledir);
        TextView tvnumeroext = findViewById(R.id.numeroextdir);
        TextView tvnumeroint = findViewById(R.id.numerointdir);
        TextView tvcolonia = findViewById(R.id.coloniadir);
        TextView tvcodigopostal = findViewById(R.id.codigopostaldir);
        TextView tvreferencias = findViewById(R.id.referenciasdir);

        tvnombredest.setText(nombredest);
        tvapellidodest.setText(apellidodest);
        tvtelefono.setText(telefono);
        tvcalle.setText(calle);
        tvnumeroext.setText(""+numeroext);
        tvnumeroint.setText(""+numeroint);
        tvcolonia.setText(colonia);
        tvcodigopostal.setText(codigopostal);
        tvreferencias.setText(referencias);
    }
}
