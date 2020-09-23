package com.cerveceria.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cerveceria.POJOs.DireccionPOJO;
import com.cerveceria.R;

import java.util.ArrayList;

public class DireccionAdapter extends RecyclerView.Adapter<DireccionAdapter.MiniActivity> {
    private Context context;
    private ArrayList<DireccionPOJO> direcciones;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int i);
        void onDelete(int i);
        void onEdit(int i);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public DireccionAdapter(Context context, ArrayList<DireccionPOJO> direcciones){
        this.context = context;
        this.direcciones = direcciones;
    }

    @Override
    public MiniActivity onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.cardview_direccion, viewGroup, false);
        return new MiniActivity(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MiniActivity miniActivity, int i) {
        DireccionPOJO direccionitem = direcciones.get(i);

        int iid = direccionitem.getIid();
        String snombredest = direccionitem.getNombredest();
        String sapellidodest = direccionitem.getApellidodest();
        String stelefono = direccionitem.getTelefono();
        String scodigopostal = direccionitem.getCodigopostal();
        String scolonia = direccionitem.getColonia();
        String scalle = direccionitem.getCalle();
        int inumeroext = direccionitem.getNumeroext();
        int inumeroint = direccionitem.getNumeroint();
        String sreferencias = direccionitem.getReferencias();
        int iidcliente = direccionitem.getIdcliente();

        miniActivity.nombredest.setText(snombredest);
        miniActivity.apellidodest.setText(sapellidodest);
        miniActivity.telefono.setText(stelefono);
        miniActivity.calle.setText(scalle);
        miniActivity.numeroext.setText(""+inumeroext);
        miniActivity.numeroint.setText(""+inumeroint);
        miniActivity.colonia.setText(scolonia);
        miniActivity.codigopostal.setText(scodigopostal);
    }

    @Override
    public int getItemCount() {
        return direcciones.size();
    }

    public class MiniActivity extends RecyclerView.ViewHolder{
        public TextView nombredest, apellidodest, telefono, calle, numeroext, numeroint, colonia, codigopostal;
        public ImageButton editar, borrar;

        public MiniActivity(View itemView) {
            super(itemView);

            nombredest = itemView.findViewById(R.id.nombredestdir);
            apellidodest = itemView.findViewById(R.id.apellidodestdir);
            telefono = itemView.findViewById(R.id.telefonodir);
            calle = itemView.findViewById(R.id.calledir);
            numeroext = itemView.findViewById(R.id.numeroextdir);
            numeroint = itemView.findViewById(R.id.numerointdir);
            colonia =itemView.findViewById(R.id.coloniadir);
            codigopostal = itemView.findViewById(R.id.codigopostaldir);

            editar = itemView.findViewById(R.id.editardir);
            borrar = itemView.findViewById(R.id.borrardir);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        int i = getAdapterPosition();
                        if (i != RecyclerView.NO_POSITION){
                            mListener.onItemClick(i);
                        }
                    }
                }
            });
            borrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        int i = getAdapterPosition();
                        if (i != RecyclerView.NO_POSITION){
                            mListener.onDelete(i);
                        }
                    }
                }
            });
            editar.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        int i = getAdapterPosition();
                        if (i != RecyclerView.NO_POSITION){
                            mListener.onEdit(i);
                        }
                    }
                }
            });
        }
    }
}
