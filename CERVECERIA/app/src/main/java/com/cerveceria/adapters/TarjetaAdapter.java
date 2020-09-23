package com.cerveceria.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cerveceria.POJOs.DireccionPOJO;
import com.cerveceria.POJOs.TarjetaPOJO;
import com.cerveceria.R;
import com.cerveceria.activities.TarjetasActivity;
import com.cerveceria.globals.Global;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TarjetaAdapter extends RecyclerView.Adapter<TarjetaAdapter.MiniActivity>{
    private Context context;
    private ArrayList<TarjetaPOJO> tarjetas;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int i);
        void onDelete(int i);
        void onEdit(int i);
    }

    public void setOnItemClickListener(TarjetaAdapter.OnItemClickListener listener){
        mListener = listener;
    }

    public TarjetaAdapter(Context context, ArrayList<TarjetaPOJO> tarjetas){
        this.context = context;
        this.tarjetas = tarjetas;
    }

    @Override
    public MiniActivity onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.cardview_tarjeta, viewGroup, false);
        return new MiniActivity(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MiniActivity miniActivity, int i) {
        TarjetaPOJO tarjetaitem = tarjetas.get(i);

        int iid = tarjetaitem.getIid();
        String snombretarjeta = tarjetaitem.getNombretarjeta();
        String snumerotarjeta = tarjetaitem.getNumerotarjeta();
        String sfechavencimiento = tarjetaitem.getFechavencimiento();
        String scodigoseguridad = tarjetaitem.getCodigoseguridad();
        int iidcliente = tarjetaitem.getIdcliente();

        miniActivity.nombretarjeta.setText(snombretarjeta);
        miniActivity.numerotarjeta.setText(snumerotarjeta);
        miniActivity.fechavencimiento.setText(sfechavencimiento);
        String a=tarjetaitem.getNumerotarjeta().substring(0,1);
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
        Picasso.get().load(url).into(miniActivity.tarjeta);
    }

    @Override
    public int getItemCount() {
        return tarjetas.size();
    }

    public class MiniActivity extends RecyclerView.ViewHolder{
        public TextView nombretarjeta, numerotarjeta, fechavencimiento;
        public ImageButton editar, borrar;
        public ImageView tarjeta;

        public MiniActivity(View itemView) {
            super(itemView);

            nombretarjeta = itemView.findViewById(R.id.nombretar);
            numerotarjeta = itemView.findViewById(R.id.numerotar);
            fechavencimiento = itemView.findViewById(R.id.fechatar);

            tarjeta = itemView.findViewById(R.id.tar);
            editar = itemView.findViewById(R.id.editartar);
            borrar = itemView.findViewById(R.id.borrartar);

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
