package com.cerveceria.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cerveceria.POJOs.OrdenPOJO;
import com.cerveceria.POJOs.ProductoPOJO;
import com.cerveceria.R;
import com.cerveceria.globals.Global;

import java.util.ArrayList;


public class OrdenAdapter extends RecyclerView.Adapter<OrdenAdapter.MiniActivity>{
    private Context context;
    private ArrayList<OrdenPOJO> ordenes;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int i);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public OrdenAdapter(Context context, ArrayList<OrdenPOJO> ordenes){
        this.context = context;
        this.ordenes = ordenes;
    }

    @Override
    public MiniActivity onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.cardview_orden, parent, false);
        return new MiniActivity(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MiniActivity miniActivity, int i) {
        OrdenPOJO ordenitem = ordenes.get(i);
        int iiid = ordenitem.getIid();
        String sfecha = ordenitem.getFecha();
        int ipreciototal = ordenitem.getPreciototal();

        miniActivity.iid.setText("Pedido #"+iiid);
        miniActivity.fecha.setText("Realizado el: "+sfecha);
        miniActivity.preciototal.setText("Precio total: $"+ipreciototal+".00");
    }

    @Override
    public int getItemCount() {return ordenes.size(); }

    public class MiniActivity extends RecyclerView.ViewHolder{
        public TextView iid, fecha, preciototal;

        public MiniActivity(View itemView){
            super(itemView);

            iid=itemView.findViewById(R.id.iidorden);
            fecha=itemView.findViewById(R.id.fechaorden);
            preciototal=itemView.findViewById(R.id.preciototalorden);

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
        }
    }
}
